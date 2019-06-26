/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import sistemas.distribuidos.distchat.JsonConvert;

/**
 *
 * @author laairoy
 */
public class SocketList {

    private static Map<Socket, String> list;
    private static Map<Socket, String> listBingo;
    private static SocketList socketList;
    UIServidor tela;
    private BingoThread bingoThread = null;

    private SocketList(UIServidor tela) {
        this.list = new HashMap<>();
        this.listBingo = new HashMap<>();
        this.tela = tela;

    }

    private SocketList() {
        this.list = new HashMap<>();
        this.listBingo = new HashMap<>();
    }

    public static SocketList init(UIServidor tela) {
        if (socketList == null) {
            socketList = new SocketList(tela);
        }
        return socketList;
    }

    public static SocketList init() {
        if (socketList == null) {
            socketList = new SocketList();
        }
        return socketList;
    }

    public boolean add(Socket cli, JsonConvert json) throws IOException {
        if (list.containsKey(cli) == false) {

            list.put(cli, json.getNome());

            JsonConvert confirmar = new JsonConvert();
            confirmar.setCod("rlogin");
            confirmar.setStatus("sucesso");

            enviarMsg(cli, confirmar.toString());

            enviarLista();

            return true;
        }
        return false;
    }

    public boolean remove(Socket cli) throws IOException {
        JsonConvert logout = new JsonConvert();
        if (list.containsKey(cli) == true) {
            removeBingo(cli);
            list.remove(cli);

            logout.setCod("rlogout");
            logout.setStatus("sucesso");

            enviarMsg(cli, logout.toString());

            enviarLista();

            return true;
        }
        return false;
    }

    private void enviarLista() throws IOException {
        JsonConvert json = new JsonConvert();
        json.setCod("lista");
        tela.resetClientes();

        for (Map.Entry<Socket, String> entry : list.entrySet()) {
            json.addToList(entry.getValue(), entry.getKey().getInetAddress().toString(), entry.getKey().getPort());
            tela.atualizaClientes(entry.getValue(), entry.getKey().getInetAddress().toString(), entry.getKey().getPort());
        }

        enviarBroadcast(json.toString(), list);
    }

    private void enviarMsg(Socket cli, String msg) throws IOException {
        new PrintStream(cli.getOutputStream()).println(msg);
        atualizarStatus("[ENVIANDO] -> " + "[" + cli.getInetAddress() + ":" + cli.getPort() + "] " + msg);

    }

    public void enviarBroadcast(String msg, Map<Socket, String> lista) throws IOException {
        atualizarStatus("[BROADCAST]");

        for (Socket cli : lista.keySet()) {
            System.out.print("  ");
            enviarMsg(cli, msg);
        }

    }

    public Socket buscarCliente(String ip, int porta) {
        for (Socket entry : list.keySet()) {
            String eIP = entry.getInetAddress().toString().replace("/", "");
            int ePorta = entry.getPort();
            if (eIP.equals(ip) && ePorta == porta) {
                return entry;
            }
        }
        return null;
    }

    public void msgUnicast(Socket cli, JsonConvert recebido) throws IOException {
        JsonConvert json = new JsonConvert();
        json.setCod("chat");
        json.setStatus("uni");
        json.setMsg(recebido.getMsg());
        json.addToList(recebido.getNome(), cli.getInetAddress().toString(), cli.getPort());

        JSONObject temp = new JSONObject(recebido.getList().get(0).toString());

        System.out.println(recebido.toString());
        System.out.println(recebido.getList().get(0).toString());

        Socket cliEnviar = buscarCliente(temp.getString("IP"), Integer.parseInt(temp.getString("PORTA")));

        //envia MSG para o remetente
        enviarMsg(cli, json.toString());

        //envia MSG para destinatario
        enviarMsg(cliEnviar, json.toString());
    }

    public void msgBroadCast(Socket cli, JsonConvert recebido) throws IOException {
        JsonConvert json = new JsonConvert();
        json.setCod("chat");
        json.setStatus("broad");
        json.setMsg(recebido.getMsg());
        json.addToList(recebido.getNome(), cli.getInetAddress().toString(), cli.getPort());

        enviarBroadcast(json.toString(), list);
    }

    private void enviarListaBingo() throws IOException {
        JsonConvert json = new JsonConvert();
        json.setCod("listapronto");
        json.addToList();

        tela.resetJogadores();

        for (Map.Entry<Socket, String> entry : listBingo.entrySet()) {
            json.addToList(entry.getValue(), entry.getKey().getInetAddress().toString(), entry.getKey().getPort());
            tela.atualizaJogadores(entry.getValue(), entry.getKey().getInetAddress().toString(), entry.getKey().getPort());
        }

        enviarBroadcast(json.toString(), list);
    }

    public boolean addBingo(Socket cli, JsonConvert json) throws IOException {
        if (listBingo.containsKey(cli) == false) {
            JsonConvert confirmar = new JsonConvert();
            confirmar.setCod("rpronto");

            if (bingoThread == null) {
                bingoThread = new BingoThread();
            }
            //jogo ja comecou
            if (bingoThread.adicionarJogador(cli) == false) {
                confirmar.setStatus("falha");
                confirmar.setMsg("O jogo ja começou!");
                enviarMsg(cli, confirmar.toString());
                return false;
            }
            if (listBingo.size() == 0) {
                bingoThread.start();
            }

            listBingo.put(cli, json.getNome());

            confirmar.setStatus("sucesso");

            enviarMsg(cli, confirmar.toString());
            enviarListaBingo();
            enviarTempo();

            return true;
        }
        return false;
    }

    public boolean removeBingo(Socket cli) throws IOException {
        JsonConvert logout = new JsonConvert();
        if (listBingo.containsKey(cli) == true) {

            listBingo.remove(cli);
            int size = bingoThread.removerJogador(cli);
            if (size == 0) {
                bingoThread = null;

            }

            logout.setCod("rpronto");
            logout.setStatus("falha");

            enviarMsg(cli, logout.toString());

            enviarListaBingo();

            enviarTempo();

            return true;
        }
        return false;
    }

    public void enviarCartela(Socket cli, ArrayList<Integer> cartela) throws IOException {
        JsonConvert json = new JsonConvert();
        json.setCod("cartela");
        json.addCartela(cartela);

        enviarMsg(cli, json.toString());
    }

    private void enviarTempo() throws IOException {

        if (bingoThread == null) {
            return;
        }

        if (bingoThread.isStarted() == false) {
            JsonConvert json = new JsonConvert();
            json.setCod("tempo");

            enviarBroadcast(json.toString(), listBingo);
        }

    }

    public void enviarNumero(int num) throws IOException {
        JsonConvert json = new JsonConvert();
        json.setCod("sorteado");
        json.addCartela(num);

        enviarBroadcast(json.toString(), listBingo);
    }

    public void atualizarTempo(int time) {
        if (tela != null) {
            tela.atualizaTempo(time);
        }
    }

    public void atualizarStatus(String str) {
        tela.atualizaLog(str);
        System.out.println(str);
    }

    public void marcarNumero(Socket cli, JsonConvert json) {
        try {
            //int num = json.getCartelaNum();
            if (json.getStatus().equals("sucesso")) {
                boolean res = bingoThread.marcarNumero(cli);
                //System.out.println("testes: " + res);
                if (res == true) {
                    atualizarStatus("[CLIENTE " + json.getNome() + "]: Tem o número.");
                } else {
                    atualizarStatus("[CLIENTE " + json.getNome() + "]: Nao tem o numero. Mentiu!");
                }
            }
        } catch (Exception e) {
            System.out.print("[ERRO]: " + e);
        }
    }

    public void pausarBingo() {
        if (bingoThread != null) {
            bingoThread.pausarBingo();
        }
    }
}

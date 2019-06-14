/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
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

    private SocketList(UIServidor tela) {
        this.list = new HashMap<>();
        this.listBingo = new HashMap<>();
        this.tela = tela;
    }

    public static SocketList init(UIServidor tela) {
        if (socketList == null) {
            socketList = new SocketList(tela);
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
        tela.atualizaLog("[ENVIANDO] -> " + "[" + cli.getInetAddress() + ":" + cli.getPort() + "] " + msg);
        System.out.println("[ENVIANDO] -> " + "[" + cli.getInetAddress() + ":" + cli.getPort() + "] " + msg);
    }

    public void enviarBroadcast(String msg, Map<Socket, String> lista) throws IOException {
        tela.atualizaLog("[BROADCAST]");
        System.out.println("[BROADCAST]");
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
        tela.resetJogadores();

        for (Map.Entry<Socket, String> entry : listBingo.entrySet()) {
            json.addToList(entry.getValue(), entry.getKey().getInetAddress().toString(), entry.getKey().getPort());
            tela.atualizaJogadores(entry.getValue(), entry.getKey().getInetAddress().toString(), entry.getKey().getPort());
        }

        enviarBroadcast(json.toString(), listBingo);
    }

    public boolean addBingo(Socket cli, JsonConvert json) throws IOException {
        if (listBingo.containsKey(cli) == false) {

            listBingo.put(cli, json.getNome());

            JsonConvert confirmar = new JsonConvert();
            confirmar.setCod("rpronto");
            confirmar.setStatus("sucesso");

            enviarMsg(cli, confirmar.toString());

            enviarListaBingo();

            return true;
        }
        return false;
    }
    public boolean removeBingo(Socket cli) throws IOException {
        JsonConvert logout = new JsonConvert();
        if (listBingo.containsKey(cli) == true) {
            listBingo.remove(cli);

            logout.setCod("rpronto");
            logout.setStatus("falha");

            enviarMsg(cli, logout.toString());

            enviarListaBingo();

            return true;
        }
        return false;
    }
}

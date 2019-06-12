/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import sistemas.distribuidos.distchat.DadosCliente;
import sistemas.distribuidos.distchat.JsonConvert;

/**
 *
 * @author laairoy
 */
public class Cliente {

    private final String ip;
    private final int porta;
    private final Socket conexao;
    private static Cliente cliente;

    private Cliente(String ip, int porta) throws IOException {
        this.ip = ip;
        this.porta = porta;
        this.conexao = new Socket(this.ip, this.porta);
    }

    public static Cliente init(String ip, int porta) throws IOException {
        if (Cliente.cliente == null) {
            Cliente.cliente = new Cliente(ip, porta);
        }

        return Cliente.cliente;
    }

    public static Cliente init() throws IOException {
        return Cliente.cliente;
    }

    //exemplo
    public void login(String nome) throws IOException {

        aguardarResposta();

        JsonConvert json = new JsonConvert();
        json.setNome(nome);
        json.setCod("login");

        enviarMSG(json.toString());

    }

    public void logout(String nome) throws IOException {

        JsonConvert json = new JsonConvert();
        json.setNome(nome);
        json.setCod("logout");

        enviarMSG(json.toString());
    }

    public void sendMsg(String clienteNome, String msg,DadosCliente dadosCliente) throws IOException {
        JsonConvert json = new JsonConvert();
        json.setCod("chat");
        json.setStatus("uni");
        json.setNome(clienteNome);
        json.setMsg(msg);
        json.addToList(dadosCliente.getNome(), dadosCliente.getIp(), dadosCliente.getPorta());

        enviarMSG(json.toString());
    }

    public void sendMsg(String clienteNome, String msg) throws IOException {
        JsonConvert json = new JsonConvert();
        json.setCod("chat");
        json.setStatus("broad");
        json.setNome(clienteNome);
        json.setMsg(msg);

        enviarMSG(json.toString());
    }

    private void aguardarResposta() throws IOException {
        ClienteThread receber = new ClienteThread(conexao.getInputStream());
        receber.start();
    }

    private void enviarMSG(String msg) throws IOException {
        PrintStream saida = new PrintStream(conexao.getOutputStream());
        saida.println(msg);

        System.out.println("[ENVIANDO] -> " + msg);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import sistemas.distribuidos.distchat.JsonCorvert;

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

        JsonCorvert json = new JsonCorvert();
        json.setNome(nome);
        json.setCod("login");

        enviarMSG(json.toString());

    }

    public void logout(String nome) throws IOException {

        JsonCorvert json = new JsonCorvert();
        json.setNome(nome);
        json.setCod("logout");

        enviarMSG(json.toString());

        //System.out.println("-> " + json.toString());
    }

    private void aguardarResposta() throws IOException {
        ClienteReceberMsg receber = new ClienteReceberMsg(conexao.getInputStream());
        receber.start();
    }
    
    private void enviarMSG(String msg) throws IOException{
        PrintStream saida = new PrintStream(conexao.getOutputStream());
         saida.println(msg);
         //saida.close();
         
         System.out.println("[ENVIANDO] -> " + msg);
        
    }
}

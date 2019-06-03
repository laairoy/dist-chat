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

    public Cliente(String ip, int porta) throws IOException {
        this.ip = ip;
        this.porta = porta;
        this.conexao = new Socket(this.ip, this.porta);

    }

    //exemplo
    public void login(String nome) throws IOException {
        
        aguardarResposta();

        //PrintStream out = new PrintStream(conexao.getOutputStream());

        //Scanner teclado = new Scanner(System.in);
        PrintStream saida = new PrintStream(conexao.getOutputStream());

        /*while (teclado.hasNextLine()) {
            saida.println(teclado.nextLine());
        }*/

        //conexao.close();
        
        JsonCorvert json = new JsonCorvert();
        json.setNome(nome);
        json.setCod("login");
        
        
        saida.println(json.toString());
        System.out.println("-> " + json.toString());
        
    }
    
    public void logout(String nome) throws IOException {

        PrintStream saida = new PrintStream(conexao.getOutputStream());

        JsonCorvert json = new JsonCorvert();
        json.setNome(nome);
        json.setCod("logout");
        
        saida.println(json.toString());
        
        System.out.println("-> " + json.toString());       
    }

    private void aguardarResposta() throws IOException {
        ClienteReceberMsg receber = new ClienteReceberMsg(conexao.getInputStream());
    }

}

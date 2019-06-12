/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author laairoy
 */
public class Servidor {

    private final int porta;
    private final ServerSocket servidor;
    UIServidor tela;

    public Servidor(int porta, UIServidor tela) throws IOException {
        this.porta = porta;
        this.servidor = new ServerSocket(porta);
        this.tela = tela;
    }

    public void start() throws IOException {
        tela.atualizaLog("[SERVIDOR INICIADO] " + "[PORTA: " + this.porta + "]");
        System.out.println("[SERVIDOR INICIADO] " + "[PORTA: " + this.porta + "]");
        while (true) {
            Socket cliente = servidor.accept();

            ServidorThread novaConexao = new ServidorThread(cliente, tela);

            novaConexao.start();

        }
    }
}

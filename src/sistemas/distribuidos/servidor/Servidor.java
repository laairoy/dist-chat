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

    public Servidor(int porta) throws IOException {
        this.porta = porta;
        this.servidor = new ServerSocket(porta);
    }

    public void start() throws IOException {
        System.out.println("[SERVIDOR INICIADO] " + "[PONTA: " + this.porta + "]");
        while (true) {
            Socket cliente = servidor.accept();

            ServidorCliente novaConexao = new ServidorCliente(cliente);

            novaConexao.start();

        }
    }
}

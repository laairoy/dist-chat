/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import sistemas.distribuidos.distchat.JsonCorvert;

/**
 *
 * @author laairoy
 */
public class ServidorCliente extends Thread {

    private final Socket cliente;

    public ServidorCliente(Socket cliente) {
        this.cliente = cliente;
        start();
    }

    @Override
    public void run() {
        System.out.println("New Communication Thread Started");

        try {
            //PrintStream out = new PrintStream(cliente.getOutputStream());
            Scanner in = new Scanner(cliente.getInputStream());
            //DataInputStream in = new DataInputStream(cliente.getInputStream());

            while (in.hasNextLine()) {
                String msg = in.nextLine();
                System.out.println("[RECEBENDO] -> " + "["+ cliente.getInetAddress() + ":" + cliente.getPort() + "] " + msg);

                verificarOperacao(msg);
            }

            //out.close();
            in.close();
            //cliente.close();

        } catch (IOException e) {
            System.err.println("deu pau");
        }
    }

    private void verificarOperacao(String msg) throws IOException {
        SocketList sList = SocketList.init();
        JsonCorvert json = new JsonCorvert(msg);

        if (json.getCod().equals("login")) {
            if (sList.add(cliente, json)) {

                System.out.println("<- usuario " + json.getNome() + "conectou");
            }

        } else if (json.getCod().equals("logout")) {
            if (sList.remove(cliente)) {
                System.out.println("<- usuario" + json.getNome() + "desconectou");
            }
        }
    }

}

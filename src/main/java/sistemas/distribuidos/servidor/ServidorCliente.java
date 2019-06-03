/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import sistemas.distribuidos.distchat.JsonCorvert;

/**
 *
 * @author laairoy
 */
public class ServidorCliente extends Thread {

    private final Socket cliente;

    public ServidorCliente(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        System.out.println("[CONEXAO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String msg;

            while ((msg = in.readLine()) != null) {

                System.out.println("[RECEBENDO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] " + msg);

                verificarOperacao(msg);
            }

            if (msg == null) {
                System.out.println("[DESCONECTOU] -> " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] ");
                SocketList list = SocketList.init();
                list.remove(cliente);
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

                System.out.println("[LOGIN] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]" + " " + json.getNome());
            }

        } else if (json.getCod().equals("logout")) {
            if (sList.remove(cliente)) {
                System.out.println("[LOGOUT] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");
            }
        }
    }

}

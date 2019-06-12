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
import sistemas.distribuidos.distchat.JsonConvert;

/**
 *
 * @author laairoy
 */
public class ServidorThread extends Thread {

    private final Socket cliente;
    UIServidor tela;

    public ServidorThread(Socket cliente, UIServidor tela) {
        this.cliente = cliente;
        this.tela = tela;
    }

    @Override
    public void run() {
        tela.atualizaLog("[CONEXAO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");
        System.out.println("[CONEXAO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String msg;

            while ((msg = in.readLine()) != null) {
                
                tela.atualizaLog("[RECEBENDO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] " + msg);
                System.out.println("[RECEBENDO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] " + msg);

                verificarOperacao(msg);
            }

            if (msg == null) {
                tela.atualizaLog("[DESCONECTOU] -> " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] ");
                System.out.println("[DESCONECTOU] -> " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] ");
                SocketList list = SocketList.init(tela);
                list.remove(cliente);
            }

            in.close();

        } catch (IOException e) {
            tela.atualizaLog("[ERRO_THREAD] <-> " + e);
            System.out.println("[ERRO_THREAD] <-> " + e);
        }
    }

    private void verificarOperacao(String msg) throws IOException {
        SocketList sList = SocketList.init(tela);
        JsonConvert json = new JsonConvert(msg);
        switch (json.getCod()) {
            case "login":
                if (sList.add(cliente, json)) {
                    tela.atualizaLog("[LOGIN] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]" + " Usuario: " + json.getNome());
                    System.out.println("[LOGIN] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]" + " Usuario: " + json.getNome());
                }
                break;

            case "logout":
                if (sList.remove(cliente)) {
                    tela.atualizaLog("[LOGOUT] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");
                    System.out.println("[LOGOUT] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");

                }
                break;
            case "chat":
                if (json.getStatus().equals("uni")) {
                    sList.msgUnicast(cliente, json);
                } else {
                    sList.msgBroadCast(cliente, json);
                }

                break;

        }
    }

}

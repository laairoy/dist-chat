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
        atualizarStatus("[CONEXAO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

            String msg;

            while ((msg = in.readLine()) != null) {

                atualizarStatus("[RECEBENDO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] " + msg);

                verificarOperacao(msg);
            }

            if (msg == null) {
                atualizarStatus("[DESCONECTOU] -> " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] ");

                SocketList list = SocketList.init(tela);
                list.remove(cliente);
            }

            in.close();

        } catch (IOException e) {
            SocketList sList = SocketList.init(tela);

            try {
                if (sList.remove(cliente)) {
                    atualizarStatus("[DESCONECTOU] -> " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "] ");

                }
            } catch (IOException ex) {
                atualizarStatus("[ERRO_THREAD] <-> " + e);

            }

        }
    }

    private void verificarOperacao(String msg) throws IOException {
        SocketList sList = SocketList.init(tela);
        JsonConvert json = new JsonConvert(msg);
        switch (json.getCod()) {
            case "login":
                if (sList.add(cliente, json)) {
                    atualizarStatus("[LOGIN] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]" + " Usuario: " + json.getNome());

                }
                break;

            case "logout":
                if (sList.remove(cliente)) {
                    atualizarStatus("[LOGOUT] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");

                }
                break;
            case "chat":
                if (json.getStatus().equals("uni")) {
                    sList.msgUnicast(cliente, json);
                } else {
                    sList.msgBroadCast(cliente, json);
                }

                break;
            case "pronto":
                switch (json.getStatus()) {
                    case "sucesso":
                        if (sList.addBingo(cliente, json)) {
                            atualizarStatus("[LOGIN BINGO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]" + " Usuario: " + json.getNome());

                        }
                        break;
                    case "falha":
                        if (sList.removeBingo(cliente)) {
                            atualizarStatus("[LOGOUT BINGO] <- " + "[" + cliente.getInetAddress() + ":" + cliente.getPort() + "]");

                        }
                        break;
                }
                break;
            case "marca":
                
                break;
        }
    }

    private void atualizarStatus(String str) {
        tela.atualizaLog(str);
        System.out.println(str);
    }

}

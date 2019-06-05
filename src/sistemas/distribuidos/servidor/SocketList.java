/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import sistemas.distribuidos.distchat.JsonCorvert;

/**
 *
 * @author laairoy
 */
public class SocketList {

    private static ArrayList<Socket> userList;
    private static ArrayList<String> userListNames;
    private static SocketList socketList;

    private SocketList() {
        this.userList = new ArrayList<>();
        this.userListNames = new ArrayList<>();
    }

    public static SocketList init() {
        if (userList == null) {
            socketList = new SocketList();
        }
        return socketList;
    }

    public boolean add(Socket cli, JsonCorvert json) throws IOException {
        if (!userList.contains(cli)) {
            userList.add(cli);
            userListNames.add(json.getNome());

            JsonCorvert confirmar = new JsonCorvert();
            confirmar.setCod("rlogin");
            confirmar.setStatus("true");
       
            enviarMsg(cli, confirmar.toString());

            enviarLista();

            return true;
        }
        return false;
    }

    public boolean remove(Socket cli) throws IOException {
        JsonCorvert logout = new JsonCorvert();
        if (userList.contains(cli)) {
            int index = userList.indexOf(cli);
            
            logout.setCod("rlogout");
            logout.setStatus("true");
            
            userList.remove(index);
            userListNames.remove(index);
            
            enviarMsg(cli, logout.toString());
            
            enviarLista();

            return true;
        }
        return false;
    }

    private void enviarLista() throws IOException {
        JsonCorvert json = new JsonCorvert();
        json.setCod("lista");
        for (int i = 0; i < userList.size(); i++) {
            json.addToList(userListNames.get(i), userList.get(i).getInetAddress().toString(), userList.get(i).getPort());
        }

        enviarBroadcast(json.toString());
    }

    private void enviarMsg(Socket cli, String msg) throws IOException {
        new PrintStream(cli.getOutputStream()).println(msg);
        System.out.println("[ENVIANDO] -> " + "[" + cli.getInetAddress() + ":" + cli.getPort() + "] " + msg);
    }

    public void enviarBroadcast(String msg) throws IOException {
        System.out.println("[BROADCAST]");
        for (Socket cli : userList) {
            System.out.print("  ");
            enviarMsg(cli, msg);
        }

    }
}

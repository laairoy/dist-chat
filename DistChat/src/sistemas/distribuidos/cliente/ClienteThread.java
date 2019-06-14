/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import sistemas.distribuidos.distchat.ClienteListModel;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sistemas.distribuidos.distchat.BingoListModel;
import sistemas.distribuidos.distchat.JsonConvert;
import sistemas.distribuidos.distchat.MsgListModel;

/**
 *
 * @author laairoy
 */
public class ClienteThread extends Thread {

    private final InputStream entradaDados;

    public ClienteThread(InputStream entradaDados) {
        this.entradaDados = entradaDados;
    }

    @Override
    public void run() {
        Scanner recebido = new Scanner(entradaDados);
        while (recebido.hasNextLine()) {

            String msg = recebido.nextLine();
            System.out.println("[RECEBIDO] <- " + msg);
            verificarOperacao(msg);

        }
    }

    private void verificarOperacao(String msg) {
        JsonConvert json = new JsonConvert(msg);
        switch (json.getCod()) {
            case "lista":
                getList(json.getList());
                break;
            case "rlogout":
                if (json.getStatus().equals("true") || json.getStatus().equals("sucesso")) {
                    System.exit(0);
                }
                break;
            case "listapronto":
                // System.out.println("Pegando listapronto");
                getListBingo(json.getList());
                break;
            case "chat":
                getMsg(json.getList(), json.getMsg());
                break;
        }
    }

    private void getMsg(JSONArray lista, String msg) {
        MsgListModel listMsg = MsgListModel.init();

        try {
            JSONObject temp = new JSONObject(lista.get(0).toString());
            listMsg.addElement(temp.getString("NOME"), msg);
            //System.out.println(temp.getString("NOME") + msg);
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClienteThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e) {
            System.out.println("erro");
        }
    }

    private void getList(JSONArray lista) {
        ClienteListModel cliList = ClienteListModel.init();
        cliList.removeAll();

        for (Object json : lista) {
            try {
                JSONObject temp = new JSONObject(json.toString());
                cliList.addElement(temp.getString("NOME"), temp.getString("IP"), temp.getInt("PORTA"));
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException | JSONException e) {
                System.out.println("erro");
            }
        }

    }

    private void getListBingo(JSONArray lista) {
        BingoListModel bingoList = BingoListModel.init();
        bingoList.removeAll();

        for (Object json : lista) {
            try {
                JSONObject temp = new JSONObject(json.toString());
                bingoList.addElement(temp.getString("NOME"), temp.getString("IP"), temp.getInt("PORTA"));

                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClienteThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JSONException e) {
                System.out.println("JSON INVALIDO");
            }

        }

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import java.io.InputStream;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import sistemas.distribuidos.distchat.JsonConvert;

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
        
        if (json.getCod().equals("lista")) {
            getList(json.getList());
        }
        if (json.getCod().equals("rlogout")) {
            if (json.getStatus().equals("true") || json.getStatus().equals("sucesso")) {
                System.exit(0);
            }
        }
    }
    
    private void getList(JSONArray lista) {
        ClienteListModel cliList = ClienteListModel.init();        
        cliList.removeAll();
        
        for (Object json : lista) {
            JSONObject temp = new JSONObject(json.toString());
            cliList.addElement(temp.getString("NOME"), temp.getString("IP"), temp.getString("PORTA"));
        }
        
    }
    
}
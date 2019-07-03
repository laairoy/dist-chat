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
    private UIClienteChat tela;
    private BingoTimer bingo = null;

    public ClienteThread(InputStream entradaDados, UIClienteChat tela) {
        this.entradaDados = entradaDados;
        this.tela = tela;
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

        try {
            switch (json.getCod()) {
                case "lista":
                    JSONArray list = json.getList();
                    if (list != null) {
                        getList(list);
                    }
                    break;
                case "rlogout":
                    if (json.getStatus().equals("true") || json.getStatus().equals("sucesso")) {
                        System.exit(0);
                    }
                    break;
                case "listapronto":
                    JSONArray listp = json.getList();
                    if (listp != null) {
                        getListBingo(listp);
                    }
                    break;
                case "chat":
                    JSONArray listm = json.getList();
                    String msgm = json.getMsg();
                    if (listm != null && msgm != null) {
                        getMsg(listm, msgm);
                    }
                    break;
                case "tempo":
                    if (bingo != null) {
                        bingo.setRun(false);
                    }
                    bingo = new BingoTimer(tela);
                    bingo.setRun(true);
                    bingo.start();

                    break;
                case "cartela":
                    JSONArray cartela = json.getCartela();
                    if (cartela != null) {
                        cartelaToTela(cartela);
                        try {
                            // bingo.setStarted(true);
                        } catch (Exception e) {
                        }
                    }

                    break;
                case "sorteado":
                    tela.mostraSorteado(json.getCartelaNum());
                    break;
                case "rpronto":
                    if (json.getMsg() != null) {
                        if (json.getStatus().equals("false") || json.getStatus().equals("falha")) {
                            tela.jogoIniciado(json.getMsg());
                        }
                    } else if (json.getStatus().equals("falha")) {

                        if (bingo != null) {
                            bingo.setRun(false);
                            bingo = null;

                        }
                    }
                    break;
                case "rbingo":
                    JSONArray gritouBingo = json.getList();
                    if (gritouBingo != null){
                        if (json.getStatus().equals("sucesso")) {
                            ganhouBingo(gritouBingo);
                        }
                    }                    
                    break;
            }
        } catch (Exception e) {
            System.out.println("[ERRO]: " + e);
        }
    }

    private void getMsg(JSONArray lista, String msg) {
        MsgListModel listMsg = MsgListModel.init();

        try {
            JSONObject temp = new JSONObject(lista.get(0).toString());
            listMsg.addElement(temp.getString("NOME"), msg);
            //System.out.println(temp.getString("NOME") + msg);
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (JSONException e) {
            System.out.println("[ERRO]: " + e);
        } catch (InterruptedException ex) {
            System.out.println("[ERRO]: " + ex);
        }
    }

    private void getList(JSONArray lista) {
        if (lista.length() < 1) {
            return;
        }
        ClienteListModel cliList = ClienteListModel.init();

        cliList.addAll(lista.toList());
//        cliList.removeAll();
        /*
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
        }*/

    }

    private void getListBingo(JSONArray lista) {
//        if (lista.length() < 1) {
//            return;
//        }

        BingoListModel bingoList = BingoListModel.init();
        bingoList.addAll(lista.toList());
//        bingoList.removeAll();
//        
//        for (Object json : lista) {
//            try {
//                JSONObject temp = new JSONObject(json.toString());
//                bingoList.addElement(temp.getString("NOME"), temp.getString("IP"), temp.getInt("PORTA"));
//                
//                TimeUnit.MILLISECONDS.sleep(10);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ClienteThread.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (JSONException e) {
//                System.out.println("JSON INVALIDO");
//            }
//            
//        }

    }

    public void cartelaToTela(JSONArray cartela) {
        int[] temp = new int[cartela.length()];
        for (int i = 0; i < cartela.length(); i++) {
            temp[i] = (Integer) cartela.get(i);
        }
        tela.mostrarCartela(temp);

    }
    
    
    public void ganhouBingo(JSONArray gritouBingo){
        
        try {
            JSONObject temp = new JSONObject(gritouBingo.get(0).toString());
            tela.ganhouBingo(temp.getString("NOME"));
            
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (JSONException e) {
            System.out.println("[ERRO]: " + e);
        } catch (InterruptedException ex) {
            System.out.println("[ERRO]: " + ex);
        }
    }
}

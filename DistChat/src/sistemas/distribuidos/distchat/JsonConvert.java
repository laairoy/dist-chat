/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.distchat;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author laairoy
 */
public class JsonConvert {

    private JSONObject json;
    private ArrayList<JSONObject> list;

    public JsonConvert() {
        list = new ArrayList<>();
        json = new JSONObject();
    }

    public JsonConvert(String data) {
        try {
            list = new ArrayList<>();
            json = new JSONObject(data);

        } catch (JSONException e) {
            printErro(e);
        }

    }

    public void put(String chave, String valor) {
        json.put(chave, valor);
    }

    public String getCod() {
        String cod = null;
        try {
            cod = json.getString("COD");
        } catch (JSONException e) {
            printErro(e);
        }
        return cod;
    }

    public void setCod(String codigo) {
        json.put("COD", codigo);
    }

    public String getNome() {
        String nome = null;
        try {
            nome = json.getString("NOME");
        } catch (JSONException e) {
            printErro(e);
        }
        return nome;
    }

    public void setNome(String nome) {
        json.put("NOME", nome);
    }

    public void setStatus(String status) {
        json.put("STATUS", status);
    }

    public String getStatus() {
        String status = null;
        try {
            status = json.getString("STATUS");
        } catch (JSONException e) {
            printErro(e);
        }
        return status;
    }

    public void setMsg(String msg) {
        json.put("MSG", msg);
    }

    public String getMsg() {
        String msg = null;
        try {
            msg = json.getString("MSG");
        } catch (JSONException e) {
            //msg = "";
        }
        return msg;
    }

    public void addToList(String nome, String ip, Integer porta) {
        JSONObject temp = new JSONObject();
        temp.put("NOME", nome);
        temp.put("IP", ip.replace("/", ""));
        temp.put("PORTA", porta.toString());

        list.add(temp);

        json.put("LISTACLIENTE", list);
    }

    public void addToList() {

        json.put("LISTACLIENTE", new ArrayList<>());
    }

    public JSONArray getList() {
        JSONArray temp = null;
        try {
            temp = new JSONArray(json.get("LISTACLIENTE").toString());
            return temp;
        } catch (JSONException e) {
            printErro(e);
        }

        return temp;
    }

    @Override
    public String toString() {
        return json.toString();
    }

    private void printErro(JSONException e) {
        System.out.println("[ERRO MSG] <- " + e);
    }

    public void addCartela(ArrayList<Integer> cartela) {
        json.put("CARTELA", cartela.toArray());
    }

    public void addCartela(int num) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(num);
        json.put("CARTELA", temp.toArray());
        // json.put("CARTELA", num);
    }

    public int getCartelaNum() {
        int num = 0;
        try {
            num = json.getJSONArray("CARTELA").getInt(0);
        } catch (Exception e) {
            try {
                num = json.getInt("CARTELA");
            } catch (Exception e2) {
                System.out.println("Erro: " + e2);
            }
        }
        return num;
    }

    public JSONArray getCartela() {
        return json.getJSONArray("CARTELA");
    }
}

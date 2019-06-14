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
        } catch (JSONException e){
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
        } catch(JSONException e){
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
            printErro(e);
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
}

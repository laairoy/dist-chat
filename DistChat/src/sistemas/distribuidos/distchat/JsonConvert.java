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
            System.out.println("[ERRO_JSON] <-> " + e);
        }

    }

    public void put(String chave, String valor) {
        json.put(chave, valor);
    }

    public String getCod() {
        return (String) json.get("COD");
    }

    public void setCod(String codigo) {
        json.put("COD", codigo);
    }

    public String getNome() {
        return (String) json.get("NOME");
    }

    public void setNome(String nome) {
        json.put("NOME", nome);
    }

    public void setStatus(String status) {
        json.put("STATUS", status);
    }

    public String getStatus() {
        return json.getString("STATUS");
    }

    public void setMsg(String msg) {
        json.put("MSG", msg);
    }

    public String getMsg() {
        return json.getString("MSG");
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
        JSONArray temp = new JSONArray(json.get("LISTACLIENTE").toString());

        return temp;
    }

    @Override
    public String toString() {
        return json.toString();
    }
}

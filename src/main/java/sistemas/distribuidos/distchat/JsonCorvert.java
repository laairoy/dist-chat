/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.distchat;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import static org.json.JSONObject.NULL;

/**
 *
 * @author laairoy
 */
public class JsonCorvert {

    private JSONObject reader;
    private ArrayList<JSONObject> list;

    public JsonCorvert() {
        list = new ArrayList<>();
        reader = new JSONObject();
        reader.put("COD", NULL);
        reader.put("NOME", NULL);
        reader.put("MSG", NULL);
        reader.put("LISTACLIENTE", NULL);
        reader.put("STATUS", NULL);
    }

    public JsonCorvert(String data) {
        try {
            list = new ArrayList<>();
            reader = new JSONObject(data);
            //System.out.println(reader.get("COD"));
            //System.out.println(reader.get("LISTACLIENTE"));

        } catch (JSONException e) {

        }

    }

    public void put(String chave, String valor) {
        reader.put(chave, valor);
    }

    public String getCod() {
        //System.out.prinln();
        return (String) reader.get("COD");
    }

    public void setCod(String codigo) {
        reader.put("COD", codigo);
    }

    public String getNome() {
        return (String) reader.get("NOME");
    }

    public void setNome(String nome) {
        reader.put("NOME", nome);
    }
    public void setStatus(String status){
        reader.put("STATUS", status);
    }
    
    public void addToList(String nome, String ip, Integer porta) {
        JSONObject temp = new JSONObject();
        temp.put("NOME", nome);
        temp.put("IP", ip.replace("/", ""));
        temp.put("PORTA", porta.toString());

        list.add(temp);

        reader.put("LISTACLIENTE", list);
    }

    @Override
    public String toString() {
        return reader.toString();
    }
}

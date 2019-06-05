/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.distchat;

import org.json.JSONException;


/**
 *
 * @author laairoy
 */
public class Main {

    public static void main(String[] args) {
        try {
            
            JsonCorvert job = new JsonCorvert();
            job.put("COD", "login");
            job.put("NOME", "marden");
            
            job.addToList("marden", "123.123.123", 1234);
            job.addToList("marden", "123.123.123", 1234);

            
            System.out.println(job.toString());


        } catch (JSONException e) {
            System.out.println("Deu pau");
        }
    }
}

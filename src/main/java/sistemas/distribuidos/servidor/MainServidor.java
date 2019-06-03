/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.IOException;
import java.net.SocketException;

/**
 *
 * @author laairoy
 */
public class MainServidor {
    public static void main(String[] args){
        try{
        Servidor servidor = new Servidor(22000);
        servidor.start();
        } catch(SocketException e){
            System.out.println("problemas com a porta");
        } catch (IOException e){
        }
    }
}

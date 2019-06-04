/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
//import java.net.http.WebSocket;
//import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author laairoy
 */
public class MainCliente {

    private final Cliente cliente;

    public MainCliente(String ip, int porta) throws IOException {
        this.cliente = Cliente.init(ip, porta);
    }

    public static void main(String[] args) {
        try {
            Cliente cliente = Cliente.init("127.0.0.1", 22000);
            Scanner teclado = new Scanner(System.in);
            while (true) {
                String x = teclado.nextLine();
                if (x.equals("login")) {
                    cliente.login("marden");
                } else if (x.equals("logout")) {
                    cliente.logout("marden");
                }

            }

            //cliente.logout("marden");
        } catch (UnknownHostException | SocketException ex) {
            System.out.println("Erro Socket");
        } catch (IOException ex) {
            System.out.println("Erro de IO");
        }
    }

    public void login(String nome) throws IOException {
        this.cliente.login(nome);
    }
    
}

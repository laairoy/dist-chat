/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import java.io.InputStream;
import java.util.Scanner;


/**
 *
 * @author laairoy
 */
public class ClienteReceberMsg extends Thread {

    private final InputStream entradaDados;

    public ClienteReceberMsg(InputStream entradaDados) {
        this.entradaDados = entradaDados;
        start();
    }

    @Override
    public void run() {
        System.out.println("Thread msg recebida!");
        Scanner recebido = new Scanner(entradaDados);
        while (recebido.hasNextLine()) {
            System.out.println("->" + recebido.nextLine());
        }
    }

}

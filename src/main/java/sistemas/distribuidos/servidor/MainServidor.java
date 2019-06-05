/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.IOException;
import java.net.SocketException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author laairoy
 */
public class MainServidor {
    public static void main(String[] args){
        try{
            int porta;
            Scanner entrada = new Scanner(System.in);
            
            System.out.print("Digita a porta: ");
            
            porta = entrada.nextInt();
            
        Servidor servidor = new Servidor(porta);
        servidor.start();
        } catch(SocketException e){
            System.out.println("problemas com a porta");
        } catch (IOException e){
        } catch (InputMismatchException e){
            System.out.println("Você não digitou um número válido para porta!");
        }
    }
}

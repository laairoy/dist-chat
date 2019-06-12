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
import javax.swing.JOptionPane;

/**
 *
 * @author laairoy
 */
public class MainServidor {
    public static void main(String[] args){
        try{
            int porta;
            porta = Integer.parseInt(JOptionPane.showInputDialog("Digite a porta:"));
            
            UIServidor tela = new UIServidor();
            Servidor servidor = new Servidor(porta, tela);
            tela.setVisible(true);
            servidor.start();
        } catch(SocketException e){
            System.out.println("problemas com a porta");
        } catch (IOException e){
        } catch (InputMismatchException e){
            System.out.println("Você não digitou um número válido para porta!");
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Valor inválido!");
        }
    }
}

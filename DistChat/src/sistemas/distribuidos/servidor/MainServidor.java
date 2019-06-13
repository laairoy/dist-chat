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
            JOptionPane.showMessageDialog(null, "Problemas com a porta");
            System.exit(0);
        } catch (IOException e){
        } catch (InputMismatchException e){
            JOptionPane.showMessageDialog(null, "Você não digitou um número válido para porta!");
            System.exit(0);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Valor inválido!");
            System.exit(0);
        }
    }
}

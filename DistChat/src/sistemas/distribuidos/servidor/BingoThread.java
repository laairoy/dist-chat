/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javafx.collections.transformation.SortedList;

/**
 *
 * @author laairoy
 */
public class BingoThread extends Thread {

    private boolean started;
    private Map<Socket, Map<Integer, Boolean>> listaCartelas;
    private ArrayList<Integer> numSorteados;
    private int count;
    private int numSorteado;
    private boolean pausa;

    public BingoThread() {
        clear();
    }

    private void clear() {
        this.started = false;
        this.numSorteados = new ArrayList<>();
        this.listaCartelas = new HashMap<>();
        this.count = 0;
        this.pausa = false;
        atualizaTempo();
    }

    private void sortearCartelas() {
        for (Socket sock : listaCartelas.keySet()) {
            Map<Integer, Boolean> temp = new HashMap<>();
            ArrayList<Integer> cartela = new ArrayList<>();
            
            int range = 1;

            for (int i = 0; i < 25; i++) {
                int num;

                if (i != 0 && i % 5 == 0) {
                    // System.out.println("num: " + i);
                    range += 15;
                }

                do {
                    num = (int) (Math.random() * 15 + range);
                } while (temp.containsKey(num) == true);
                temp.put(num, false);

                cartela.add(num);

            }
            Collections.sort(cartela);
            cartela.set(12, 0);

            listaCartelas.replace(sock, temp);
            SocketList sList = SocketList.init();

            try {
                sList.enviarCartela(sock, cartela);
            } catch (IOException ex) {
                System.out.println("Erro: " + ex);
            }

        }

    }

    private int sortearNumero() {
        int numero;

        do {
            numero = (int) (Math.random() * 75 + 1);
        } while (numSorteados.contains(numero) == true);

        numSorteados.add(numero);

        try {
            SocketList sList = SocketList.init();
            sList.enviarNumero(numero);
        } catch (IOException ex) {
            System.out.println("Erro: " + ex);
        }

        return numero;

    }

    public boolean marcarNumero(Socket cli) {
        if (listaCartelas.get(cli).containsKey(numSorteado) == false) {
            return false;
        }
        listaCartelas.get(cli).replace(numSorteado, true);
        return true;
    }

    public int removerJogador(Socket sock) {
        listaCartelas.remove(sock);

        if (started == false) {
            count = 0;
        }

        return listaCartelas.size();
    }

    private void atualizaTempo() {
        SocketList sList = SocketList.init();
        sList.atualizarTempo(count);
    }

    public boolean adicionarJogador(Socket sock) {
        if (started == true) {
            return false;
        }

        count = 0;
        //this.start();

        listaCartelas.put(sock, null);
        return true;
    }

    public void pausarBingo() {
        pausa = !pausa;
        System.out.println("[Bingo] pausa: " + pausa);
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void run() {
        while (listaCartelas.size() > 0) {

            try {
                TimeUnit.SECONDS.sleep(1);
                if (pausa == false) {
                    count++;
                    atualizaTempo();
                    //System.out.println("Tempo: " + count);
                    if (started == false && count == 30) {
                        started = true;
                        count = 0;
                        sortearCartelas();

                    } else if (started == true && count == 10) {
                        count = 0;

                        numSorteado = sortearNumero();

                    }
                }

            } catch (InterruptedException ex) {
                System.out.print(ex);

            }
        }
        clear();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.servidor;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author laairoy
 */
public class BingoThread extends Thread {

    private boolean started;
    private Map<Socket, Map<Integer, Boolean>> listaCartelas;
    private ArrayList<Integer> numSorteados;
    private int count;

    public BingoThread() {
        clear();
    }

    private void clear() {
        this.started = false;
        this.numSorteados = new ArrayList<>();
        this.listaCartelas = new HashMap<>();
        this.count = 0;
        atualizaTempo();
    }

    private void sortearCartelas() {
        for (Socket sock : listaCartelas.keySet()) {
            Map<Integer, Boolean> temp = new HashMap<>();
            ArrayList<Integer> cartela = new ArrayList<>();
            int range = 1;

            for (int i = 0; i < 75; i++) {
                int num;
                if (i == 12) {
                    num = 0;
                } else {
                    do {
                        num = (int) (Math.random() * 15 + range);
                    } while (temp.containsKey(num) == true);
                    temp.put(num, false);
                }

                cartela.add(num);

                if ((float) i % 5 == 0) {
                    range += 15;
                }
            }
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

    private void marcarNumero(int num) {
        for (Socket soc : listaCartelas.keySet()) {
            if (listaCartelas.get(soc).containsKey(num) == true) {
                listaCartelas.get(soc).replace(num, true);
            }
        }
    }

    public int removerJogador(Socket sock) {
        listaCartelas.remove(sock);

        if (started == false) {
            count = 0;
        }

        return listaCartelas.size();
    }
    private void atualizaTempo(){
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

    @Override
    public void run() {
        while (listaCartelas.size() > 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
                count++;
                atualizaTempo();
                System.out.println("Tempo: " + count);
                if (started == false && count == 30) {
                    started = true;
                    count = 0;
                    sortearCartelas();

                } else if (started == true && count == 10) {
                    count = 0;

                    int num = sortearNumero();
                    System.out.println("Numero Sorteado: " + num);
                    //marcarNumero(num);

                }

            } catch (InterruptedException ex) {
                System.out.print(ex);
            }
        }
        clear();
    }

}

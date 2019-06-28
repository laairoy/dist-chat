/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author laairoy
 */
public class BingoTimer extends Thread {

    private boolean started;
    private int count;
    private boolean run;
    UIClienteChat cliente;

    public BingoTimer(UIClienteChat cliente) {
        this.cliente = cliente;
        clear();
    }

    private void clear() {
        this.started = false;

        this.count = 30;
        atualizaTempo();
        this.run = false;
    }

    private void atualizaTempo() {
        cliente.atualizarTempo(count);
    }

    public void setRun(boolean var) {
        run = var;
    }

    @Override
    public void run() {
        
        while (run == true) {
            count--;
            atualizaTempo();
            try {
                TimeUnit.SECONDS.sleep(1);                
                //System.out.println("Tempo: " + count);
                if (started == false && count == 0) {
                    started = true;
                    count = 10;
                } else if (started == true && count == 0) {
                    count = 10;
                }
                
                
            } catch (InterruptedException ex) {
                System.out.print(ex);

            }
        }
        clear();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.distchat;

/**
 *
 * @author laairoy
 */
public class DadosCliente {

    public String getNome() {
        return nome;
    }

    public String getIp() {
        return ip;
    }

    public int getPorta() {
        return porta;
    }

    public DadosCliente(String nome, String ip, int porta) {
        this.nome = nome;
        this.ip = ip;
        this.porta = porta;
    }
    private final String nome;
    private final String ip;
    private final int porta;

}

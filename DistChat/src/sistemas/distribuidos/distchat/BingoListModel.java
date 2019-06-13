/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.distchat;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author laairoy
 */

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author laairoy
 */


public class BingoListModel extends AbstractListModel {

    private static BingoListModel list;
    private final ArrayList<DadosCliente> aList;

    private BingoListModel() {
        aList = new ArrayList<>();
    }

    public static BingoListModel init() {
        if (list == null) {
            list = new BingoListModel();
        }
        return list;
    }

    public void addElement(String nome, String ip, int porta) {

        aList.add(new DadosCliente(nome, ip, porta));
        fireIntervalAdded(this, aList.size() - 1, aList.size() - 1);
    }

    public void removeAll() {
        aList.clear();
        fireIntervalRemoved(this, 0, 0);
    }

    @Override
    public int getSize() {

        return aList.size();
    }

    @Override
    public Object getElementAt(int arg0) {
        return aList.get(arg0).getNome();
    }

    public DadosCliente getDadosCliente(int index) {
        return aList.get(index);
    }

}

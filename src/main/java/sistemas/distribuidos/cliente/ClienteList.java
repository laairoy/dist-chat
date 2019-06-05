/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemas.distribuidos.cliente;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author laairoy
 */
public class ClienteList extends AbstractListModel {

    private static ClienteList list;
    private ArrayList<String> aList;

    private ClienteList(){
        aList = new ArrayList<>();
    }
    
    public static ClienteList init() {
        if (list == null) {
            list = new ClienteList();
        }
        return list;
    }
    
    public void addElement(String nome, String ip, String porta) {
        aList.add(nome);
        fireIntervalAdded(this, aList.size() - 1, aList.size() - 1);
    }
    
    public void removeAll(){
        aList.clear();
        fireIntervalRemoved(this, 0, 0);
    }

    @Override
    public int getSize() {
        return aList.size();
    }

    @Override
    public Object getElementAt(int arg0) {
        return aList.get(arg0);
    }

}
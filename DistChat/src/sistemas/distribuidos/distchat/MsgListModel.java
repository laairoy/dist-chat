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
public class MsgListModel extends AbstractListModel {

    private static MsgListModel list;
    private final ArrayList<String> aList;

    private MsgListModel() {
        aList = new ArrayList<>();
    }

    public static MsgListModel init() {
        if (list == null) {
            list = new MsgListModel();
        }
        return list;
    }

    public void addElement(String nome, String msg) {

        aList.add(nome + ": " + msg);
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
        return aList.get(arg0);
    }

}

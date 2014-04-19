/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Tamojit9
 */
public class JTable extends JPanel{
    private static final long serialVersionUID = 1L;
    JPanel actualTable = new JPanel(new GridLayout(1, 1));
    ArrayList<JPanel> Rows = new ArrayList<>();
    
    Cell getCell(Object o) {
        return new Cell(o);
    }
    
    Cell getCell(String o) {
        return new Cell(o);
    }
    
    public JTable() {
        setLayout(new GridLayout(1, 1));
    }
    
    void addRow(Object[] values) {
        JPanel row = new JPanel(new GridLayout(1, values.length));
        for (Object object : values) {
            row.add(getCell(object));
        }
        Rows.add(row);
        actualTable.setLayout(new GridLayout(Rows.size(), 1));
        actualTable.add(row);
    }
    
    void addRowHeaders(Object[] values) {
        JPanel row = new JPanel(new GridLayout(1, values.length));
        for (Object object : values) {
            row.add(getCell(object.toString()));
        }
        Rows.add(row);
        actualTable.setLayout(new GridLayout(Rows.size(), 1));
        actualTable.add(row);
    }
    
    void centerText(int ind) {
        assert(ind < Rows.size()); 
        Component[] c = Rows.get(ind).getComponents();
        for (Component component : c) {
            ((Cell)(component)).centerText();
        }
    }
    
    void addRow(JPanel row) {
        Rows.add(row);
        actualTable.setLayout(new GridLayout(Rows.size(), 1));
        actualTable.add(row);
    }
    
    JPanel getRow(int ind) {
        return Rows.get(ind);
    }
    
    Component getCell(int ind, int j) {
        assert(ind < Rows.size()); 
        Component[] c = Rows.get(ind).getComponents();
        assert(j < c.length); 
        return c[j];
    }
    
    void removeRow(int ind) {
        Rows.get(ind).setVisible(false);
        actualTable.remove(Rows.get(ind));
    }
    
    void setEditable(int ind, boolean val) {
        assert(ind < Rows.size()); 
        Component[] c = Rows.get(ind).getComponents();
        for (Component component : c) {
            ((JTextField)(component)).setEditable(val);
            ((JTextField)(component)).revalidate();
        }
    }
    
    void setEditable(int ind, int j, boolean val) {
        assert(ind < Rows.size()); 
        Component[] c = Rows.get(ind).getComponents();
        assert(j < c.length); 
        for(int k = 0; k < c.length; k++) {
            Component component = c[k];
            if(k == j) {
                ((JTextField)(component)).setEditable(val);
                ((JTextField)(component)).revalidate();
            }
        }
    }
    
    void pack() {
        this.add(new JScrollPane(actualTable));
    }
    
    public static void main(String[] argv) {
        JFrame jf = new JFrame();
        jf.setLayout(new GridLayout(1, 1));
        JTable jt = new JTable();
        jt.addRowHeaders(new Object[]{"test1", "test2", "test3"});
        jt.addRow(new Object[]{"test1", "test2"});
        jt.addRow(new Object[]{"test1", "test2"});
        jt.addRow(new Object[]{"test1", "test2"});
        jt.addRow(new Object[]{"test1", "test2"});
        jt.pack();
        jt.centerText(1);
        jf.add(jt);
        jf.setSize(500, 500);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}

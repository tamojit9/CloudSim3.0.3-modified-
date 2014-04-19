/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;

/**
 *
 * @author Tamojit9
 */
public class Button extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;
    int dyleft = 0, dyright = 0;
    int dx1, dx2, dx4, dx3;
    int dy1, dy2, dy4, dy3;
    int width, height;
    int dx = 20, dy = 20, decent;
    public static int press = 1, unPress = 0;
    Rectangle r = null;
    String text = "";
    Color back, fore, border = Color.gray;
    int borderWidth = 3;
    
    /**
     * Creates new form Button
     */
    public Button() {
        initComponents();
        width = getWidth(); height = getHeight();
        setFont(new java.awt.Font("Segoe UI Light", 0, 38));
        setBackGroundColor(Colors.DARKGREEN0);
        setForeGroundColor(Color.white);
    }

    public void setBorderCololr(Color c) {
        border = c;
    }
    
    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }
    
    public void setBackGroundColor(Color back) {
        this.back = back;
    }

    public void setForeGroundColor(Color fore) {
        this.fore = fore;
    }
    
    int detectEdge(Point p) {
        if(p.x <= dx) return 1;
        if(width - p.x <= dx) return 3;
        if(height - p.y <= dy) return 4;
        if(p.y <= dy) return 2;
        return 0;
    }
    
    void leftPress() {
        dy4 = -10;
        width -= 10;
    }
    
    void leftUnPress() {
        dy4 = 0;
        width += 10;
    }
    
    void rightPress() {
        dx1 = 10;
        dx4 = 10;
        dy3 = -10;
    }
    
    void rightUnPress() {
        dx1 = 0;
        dx4 = 0;
        dy3 = 0;
    }
    
    void downPress() {
        dx3 = -10;
        dx4 = 10;
        height -= 10;
    }
    
    void downUnPress() {
        dx3 = 0;
        dx4 = 0;
        height += 10;
    }
    
    void upPress() {
        dx1 = 10;
        dx2 = -10;
        dy3 = -10;
        dy4 = -10;
    }
    
    void upUnPress() {
        dx1 = 0;
        dx2 = 0;
        dy3 = 0;
        dy4 = 0;
    }
    
    void produceEffect(int edgeNumber, int action) {
        if(edgeNumber == 1)  {
            if(action == press) leftPress(); 
            else leftUnPress(); 
        } else if(edgeNumber == 3)  {
            if(action == press) rightPress(); 
            else rightUnPress(); 
        } else if(edgeNumber == 4)  {
            if(action == press) downPress();
            else downUnPress(); 
        } else if(edgeNumber == 2)  {
            if(action == press) upPress();
            else upUnPress();
        } else {
            if(action  == press) {
                dx1 = dx4 = 2;
                dy1 = dy2 = 2;
                dy3 = dy4 = -2;
                dx3 = dx2 = -2;
            } else {
                dx1 = dx4 = dx3 = dx2 = 0;
                dy1 = dy4 = dy3 = dy2 = 0;
            }
        }
        repaint();
    }
    
    void setHints(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
    
    void clearBackGround(Graphics2D g2d) {
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
    
    void setAppropriateSize(Graphics2D g2d) {
        g2d.setFont(getFont());
        if(r == null) {
            r = getGraphics().getFontMetrics().getStringBounds(text, g2d).getBounds();
            decent = g2d.getFontMetrics().getDescent();
        }
        setSize(Math.max(getWidth(), r.width + 20), Math.max(getHeight(), r.height));
        width = getWidth();
        height = getHeight();
    }
    
    void drawString(Graphics2D g2d) {
        g2d.setColor(fore);
        g2d.drawString(text, g2d.getFontMetrics().charWidth(' '), (getHeight()>>1) + decent);  
    }
    
    void drawBorder(Graphics2D g2d) {
        Polygon p = new Polygon(new int[]{dx1+borderWidth, width+dx2-borderWidth, width + dx3-borderWidth, dx4+borderWidth}, 
                new int[]{dy1+borderWidth, dy2+borderWidth, height + dy3-borderWidth, height+dy4-borderWidth}, 4);
        g2d.setColor(back);
        g2d.fillPolygon(p);
    }
    
    void drawBackGround(Graphics2D g2d) {
        g2d.setColor(border);
        Polygon p = new Polygon(new int[]{dx1, width+dx2, width + dx3, dx4}, 
                new int[]{dy1, dy2, height + dy3, height+dy4}, 4);
        g2d.fillPolygon(p);
        drawBorder(g2d);
        g2d.setClip(p);
        
    }
    
    @Override
    public void paint(Graphics g) {
        if(width == 0) {
            width = getPreferredSize().width;
            height = getPreferredSize().height;
        }
        Graphics2D g2d = (Graphics2D) g;
        setHints(g2d);
        setAppropriateSize(g2d);
        clearBackGround(g2d);
        drawBackGround(g2d);
        drawString(g2d);
    }
    
    public void onClick(MouseAdapter ma) {
        addMouseListener(ma);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // TODO add your handling code here:
        int edge = detectEdge(evt.getPoint());
        produceEffect(edge, press);
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // TODO add your handling code here:
        int edge = detectEdge(evt.getPoint());
        produceEffect(edge, unPress);
    }//GEN-LAST:event_formMouseReleased

    public void setText(String txt) {
        text = txt;
        r = null;
        repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

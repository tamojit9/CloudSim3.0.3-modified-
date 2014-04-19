/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Tamojit9
 */
public class ImagePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    @Override
    public void paint(Graphics g) {
        try {
            g.drawImage(ImageIO.read(getClass().getResource("/UI/bg3.jpg")), 0, 0, getWidth(), getHeight(), null);
            paintChildren(g);
        } catch (IOException ex) {
            Logger.getLogger(CloudMod.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

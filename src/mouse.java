
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Johnny 28
 */
class mouse implements MouseListener {
    
    FlightList obj;

    public mouse(FlightList obj) {
        this.obj=obj;
    }
   

    @Override
    public void mouseClicked(MouseEvent e) {
        DisplayManager dm=new DisplayManager();
        dm.displayDetails(obj, obj.objsearch);
        //obj.dispose();
        obj.setVisible(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        obj.jButton1.setFont(obj.jButton1.getFont().deriveFont(Font.BOLD));
    }

    @Override
    public void mouseExited(MouseEvent e) {
         obj.jButton1.setFont(obj.jButton1.getFont().deriveFont(Font.PLAIN));
    }
    
}

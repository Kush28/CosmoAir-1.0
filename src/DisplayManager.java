
import java.io.IOException;
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
public class DisplayManager implements Display{

    @Override
    public void displaySearchPage() {
        SearchFlight sf=new SearchFlight();
        sf.setSize(685, 478);
        sf.setLocationRelativeTo(null);
        sf.setVisible(true);
    }

    @Override
    public void displayFlightList(SearchFlight objSearch) {
        FlightList fl;
        try {
            fl = new FlightList(objSearch);
            fl.setSize(685, 478);
            fl.setLocationRelativeTo(null);
            fl.setVisible(true);
        }
        catch (IOException ex) {
            System.out.println("Trouble reading...");
        }
    }

    @Override
    public void displayDetails(FlightList objdisplay, SearchFlight objsearch) {
        int b = 0;
        CustomerDetails cd= new CustomerDetails(objdisplay, objsearch);
        cd.setSize(570, 478);
        cd.setLocationRelativeTo(null);
        cd.setVisible(true);
    }

    @Override
    public void displayTicket(CustomerDetails cd) {
        ConfirmSlip obj = new ConfirmSlip(cd);
        obj.setSize(585, 478);
        obj.setLocationRelativeTo(null);
        obj.setVisible(true);
    }
    
}

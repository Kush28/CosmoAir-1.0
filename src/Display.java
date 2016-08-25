/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Johnny 28
 */
public interface Display {
    public void displaySearchPage();
    public void displayFlightList(SearchFlight objSearch);
    public void displayDetails(FlightList objdisplay,SearchFlight objsearch);
    public void displayTicket(CustomerDetails cd);
}

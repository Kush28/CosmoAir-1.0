/**
 * 
 * @author ProgJazz
 */
import java.io.*;

public class SilkAirSchedule {
    Flight flights[];
    int silkSize;

    public SilkAirSchedule() throws IOException {
        DataManager data = new DataManager();
        flights = data.readSilkAirFile();
        silkSize = data.silkSize();
    }
    
    public int getHour(int i, int j) {
        if(j == 0) {
            String depart = flights[i].getDepTime();
            int dep = Integer.parseInt(depart.substring(0, 2));
            return dep;
        }
        else {
            String arrive = flights[i].getArrTime();
            int arr = Integer.parseInt(arrive.substring(0, 2));
            return arr;
        }
    }
    
    public int getMinute(int i, int j) {
        if(j == 0) {
            String depart = flights[i].getDepTime();
            int departure = Integer.parseInt(depart.substring(2, 4));
            return departure;
        }
        else {
            String arrive = flights[i].getArrTime();
            int arrival = Integer.parseInt(arrive.substring(2, 4));
            return arrival;
        }
    }
    
}

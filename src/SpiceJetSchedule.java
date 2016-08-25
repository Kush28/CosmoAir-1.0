/**
 * 
 * @author ProgJazz
 */

import java.util.*;
import java.io.*;

public class SpiceJetSchedule {
    Flight flights[];
    int spiceSize;
    
    public SpiceJetSchedule() throws IOException {
        DataManager fr = new DataManager();
        flights = fr.readSpiceJetFile();
        spiceSize = fr.spiceSize();
    }
    
    public int getHour(int i, int j) {
        if(j == 1) {
            String arrive = flights[i].getArrTime();
            StringTokenizer part = new StringTokenizer(arrive, ":");
            String str = part.nextToken();
            int arr = Integer.parseInt(str);
            if(arrive.contains("PM"))
                arr += 12;
            return arr;
        }
        else {
            String depart = flights[i].getDepTime();
            StringTokenizer part = new StringTokenizer(depart, ":");
            String str = part.nextToken();
            int dep = Integer.parseInt(str);
            if(depart.contains("PM"))
                dep += 12;
            return dep;
        }
    }
    
    public int getMinute(int i, int j) {
        if(j == 1) {
            String arrive = flights[i].getArrTime();
            StringTokenizer part = new StringTokenizer(arrive, ": ");
            String str = part.nextToken();
            str = part.nextToken();
            return (Integer.parseInt(str));
        }
        else {
            String depart = flights[i].getDepTime();
            StringTokenizer part = new StringTokenizer(depart, ": ");
            String str = part.nextToken();
            str = part.nextToken();
            return (Integer.parseInt(str));
        }
    }
    
}

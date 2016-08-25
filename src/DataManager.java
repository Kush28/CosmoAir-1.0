/**
 * 
 * @author SohamLiquifire
 */

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class DataManager {
    Flight flights[];
    int sizeSpice;
    int sizeSilk;
    FileReader reader = null;
    BufferedReader br = null;
    String line; 
    
    public Flight[] readSilkAirFile() {
        try {
            String str;
            int count = 0;
            try {
                reader = new FileReader("Files/2015.silkair.csv");
                br = new BufferedReader(reader);
                str = br.readLine();
                while(str != null) {
                    count++;
                    str = br.readLine();
                }
                br.close();
            }
            catch(IOException e) {
                JOptionPane.showMessageDialog(null, "Silk Air Database not found...");
                JOptionPane.showMessageDialog(null, "Please load proper Database and try again...");
                System.exit(0);
            }
            sizeSilk = count - 3;
            int i = sizeSilk;
            
            flights = new Flight[i];
            for (int j=0;j<i;j++)
                flights[j] = new Flight();

            line = null;
            i = 0;
            reader = new FileReader("Files/2015.silkair.csv");
            br = new BufferedReader(reader);
            while (i < 3) {
                line = br.readLine();
                i++;
            }
            line = br.readLine();
            i = 0;
            String tokens;
            while(line != null) {
                StringTokenizer data = new StringTokenizer(line, "|");
                tokens = data.nextToken();
                StringTokenizer dataPart = new StringTokenizer(tokens, "|");
                flights[i].setSource(dataPart.nextToken());

                tokens = data.nextToken();
                String tmp;
                StringTokenizer dataPart1 = new StringTokenizer(tokens, ",");
                int j = dataPart1.countTokens();
                while (--j >= 0) {
                    tmp = dataPart1.nextToken();
                    if (tmp.compareTo("Sun") == 0)
                        flights[i].setDays(0, true);
                    else if (tmp.compareTo("Mon") == 0)
                        flights[i].setDays(1, true); 
                    else if (tmp.compareTo("Tue") == 0)
                        flights[i].setDays(2, true);
                    else if (tmp.compareTo("Wed") == 0)
                        flights[i].setDays(3, true);
                    else if (tmp.compareTo("Thu") == 0)
                        flights[i].setDays(4, true);
                    else if (tmp.compareTo("Fri") == 0)
                        flights[i].setDays(5, true);
                    else if (tmp.compareTo("Sat") == 0)
                        flights[i].setDays(6, true);
                }

                tokens = data.nextToken();
                StringTokenizer dataPart2 = new StringTokenizer(tokens, "|");
                flights[i].setFlightNo(dataPart2.nextToken());
                tokens = data.nextToken();
                StringTokenizer dataPart3 = new StringTokenizer(tokens, "/");
                flights[i].setDepTime(dataPart3.nextToken());
                flights[i].setArrTime(dataPart3.nextToken());
                i++;
                line = br.readLine();
            }

            br.close();
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Silk Air Database not found...");
            JOptionPane.showMessageDialog(null, "Please load proper Database and try again...");
            System.exit(0);
        }
        return flights;
    }
    
    //Silk Air number of Flights
    int silkSize() throws IOException {
        return sizeSilk;
    }
    
    public Flight[] readSpiceJetFile() {
        String effectiveFrom[], effectiveTill[];
        String str = null, temp;
        int i = 0;
        try {
            reader = new FileReader("Files/2015.spicejet.csv");
            br = new BufferedReader(reader);
            str = br.readLine();
            while(str != null) {
                i++;
                str = br.readLine();
            }
            br.close();
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Silk Air Database not found...");
            JOptionPane.showMessageDialog(null, "Please load proper Database and try again...");
            System.exit(0);
        }
                
        sizeSpice = i - 5;
        flights = new Flight[i - 5];
        for(int j=0;j<i-5;j++)
            flights[j]=new Flight();
        
        effectiveFrom = new String[i - 5];
        effectiveTill = new String[i - 5];
        i=0;
        
        try {
            reader = new FileReader("Files/2015.spicejet.csv");
            br = new BufferedReader(reader);
            for (int j=0;j<=5;j++) {
                str = br.readLine();
            }
            
            while(str != null) {
                    StringTokenizer data = new StringTokenizer(str,"|");
                    flights[i].setSource(data.nextToken().trim());
                    flights[i].setDestination(data.nextToken().trim());
                    temp = data.nextToken();
                    
                    if(temp.compareTo("DAILY") == 0){
                        for(int x=0;x<=7;x++)
                            flights[i].setDays(x, true);
                    }
                    else {
                        StringTokenizer dataPart = new StringTokenizer(temp, ",");
                        int j = dataPart.countTokens();
                        while(j-- > 0) {
                            String tempDays = dataPart.nextToken();
                            tempDays = tempDays.trim();
                            if(tempDays.compareTo("SUNDAY") == 0){
                                flights[i].setDays(0, true);
                                flights[i].setDays(7, true);
                            }
                            else if(tempDays.compareTo("MONDAY") == 0)
                            	flights[i].setDays(1, true); 
                            else if(tempDays.compareTo("TUESDAY") == 0)
                            	flights[i].setDays(2, true);
                            else if(tempDays.compareTo("WEDNESDAY") == 0)
                                flights[i].setDays(3, true);
                            else if(tempDays.compareTo("THURSDAY") == 0)
                                flights[i].setDays(4, true);
                            else if(tempDays.compareTo("FRIDAY") == 0)
                                flights[i].setDays(5, true);
                            else if(tempDays.compareTo("SATURDAY") == 0)
                                flights[i].setDays(6, true);
                        }
                    }
                    
                    flights[i].setFlightNo(data.nextToken());
                    flights[i].setDepTime(data.nextToken());
                    flights[i].setArrTime(data.nextToken());
                    flights[i].setVia(data.nextToken());
                    effectiveFrom[i] = data.nextToken();
                    temp = data.nextToken();
                    if(temp.contains("OCT")) {
                        effectiveTill[i] = temp;
                    }
                    else {
                        i--;
                        sizeSpice--;
                    }
                i++;
                str = br.readLine();
            }
            br.close();
        }
        catch (IOException e) {
           JOptionPane.showMessageDialog(null, "Spice Jet Database not found...");
           JOptionPane.showMessageDialog(null, "Please load proper Database and try again...");
           System.exit(0);
        }
        return flights;
    }
    
    //Spice Jet number of Flights
    int spiceSize() {
        return sizeSpice;
    }
}
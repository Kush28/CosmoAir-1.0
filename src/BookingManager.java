/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ProgJazz
 */

import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

public class BookingManager {
    public String flightNo, name;
    public int date, noPassenger, seats, localSeatId;
    
    BufferedWriter bw;
    FileWriter fw;
    FileReader fr;
    BufferedReader br;
    
    public BookingManager(String flightNo, String name, int date, int noPassenger) {
        this.flightNo = flightNo;
        this.name = name;
        this.date = date;
        this.noPassenger = noPassenger;
    }
    
    public void bookTicket(int seat) {
        int localId = 0, countLines = 0, i = 0, localSeats = 0;
        String actualVerifier = null;
        String localVerifier;
        
        try {
            fr = new FileReader("Files/PassengerDatabase.csv");
            br = new BufferedReader(fr);
            String verifier;
            
            if (br.readLine() != null) {
                fr = new FileReader("Files/PassengerDatabase.csv");
                br = new BufferedReader(fr);
                try {
                    while ((verifier = br.readLine()) != null) {
                        countLines++;
                    }
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                }

                fr = new FileReader("Files/PassengerDatabase.csv");
                br = new BufferedReader(fr);
                try {
                    while (i != countLines-1) {
                        actualVerifier = br.readLine();
                        i++;
                    }
                    actualVerifier = br.readLine();
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                StringTokenizer verifierPart = new StringTokenizer(actualVerifier, "|");
                localVerifier = verifierPart.nextToken();
                localId = Integer.parseInt(localVerifier);
                
                    fw = new FileWriter("Files/PassengerDatabase.csv", true);
                    bw = new BufferedWriter(fw);
                    bw.append(++localId + "|" + date + "|" + name + "|" + flightNo + "|" + noPassenger + "\n");
                
            }
            else if (br.readLine() == null) {
                fw = new FileWriter("Files/PassengerDatabase.csv", true);
                bw = new BufferedWriter(fw);
                bw.append(++localId + "|" + date + "|" + name + "|" + flightNo + "|" + noPassenger + "\n");
            }
            bw.close();
            fw.close();
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Passenger Database not Found...");
            JOptionPane.showMessageDialog(null, "Please load correct Database and try again...");
            System.exit(0);
        }
    }
}

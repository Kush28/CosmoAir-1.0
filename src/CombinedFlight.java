/**
 * 
 * @author ProgJazz
 */

import java.io.*;

public class CombinedFlight extends FinalSetterGetter {
    int flightCount, seats = 0;
    BufferedReader br;
    FileReader fr;
    
    final int timeDiffSingapore = 150;    //Singapore Time Difference 150 Minute...
    
    //Time Difference Calculator and Checking...
    public static int timeDifference(int arrHour, int arrMinute, int depHour, int depMinute) {
        int diff;
        if (depHour < arrHour)
            diff = ((depHour + 24) - arrHour) * 60;
        else
            diff = (depHour - arrHour) * 60;
        return (diff + (depMinute - arrMinute));
    }
    
    //Modified Constructor...
    CombinedFlight(String source, int spiceDay, int silkDay) throws IOException {
        int i, j;
        SilkAirSchedule silkAir = new SilkAirSchedule();
        SpiceJetSchedule spiceJet = new SpiceJetSchedule();
        for (i=0;i<spiceJet.spiceSize;i++) {
            if (spiceJet.flights[i].getSource().compareTo(source) == 0 && spiceJet.flights[i].getFrequency(spiceDay)) { 
                for (j=0;j<silkAir.silkSize;j++) { 
                    String silkSource = silkAir.flights[j].getSource();
                    int depHour = silkAir.getHour(j, 0);
                    int depMinute = silkAir.getMinute(j, 0);
                    String spiceDest = spiceJet.flights[i].getDestination();                    
                    int arrHour = spiceJet.getHour(i, 1);
                    int arrMinute = spiceJet.getMinute(i, 1);                    
                    if (spiceDest.compareTo(silkSource.substring(0,silkSource.lastIndexOf("(")-1).toUpperCase().trim()) == 0) {
                        if (silkAir.flights[j].getFrequency(silkDay)) {
                            int timeDifference = timeDifference(arrHour, arrMinute, depHour, depMinute);
                            if (timeDifference >= 120 && timeDifference <= 360) {
                                flightCount++;} } } } } } }
    
    //Sorting Flights according to Flight Durations...
    public void sortFlights(int flightDuration[], CombinedFlight flight[]) {
        int i, j, k;
        
        CombinedFlight temp;
        for (i=0;i<flightCount-1;i++) {
            for (j=i+1;j<flightCount;j++) {
                if (flightDuration[i] > flightDuration[j]) {
                    
                    //Swapping Durations...
                    k = flightDuration[j];
                    flightDuration[j] = flightDuration[i];
                    flightDuration[i] = k;
                    
                    //Swapping Flights...
                    temp = flight[j];
                    flight[j] = flight[i];
                    flight[i] = temp; } } } }
    
    //THE THOR METHOD...GRRR...I AM THOR...THE LEGENDARY GUARDIAN OF ASGUARD...SON OF ODIN...LORD OF THE VIKINGS...
    public CombinedFlight[] combine(String source,int spiceDay,int silkDay) throws IOException {
        int i, j, totalDuration;
        SilkAirSchedule silkAir = new SilkAirSchedule();
        SpiceJetSchedule spiceJet = new SpiceJetSchedule();
        int duration[] = new int[flightCount];
        CombinedFlight flightCombo[] = new CombinedFlight[flightCount];
        for(i=0;i<flightCount;i++)
            flightCombo[i] = new CombinedFlight(source, spiceDay, silkDay);
        
        int current = 0;
        for(i=0;i<spiceJet.spiceSize;i++) {
            if(spiceJet.flights[i].getSource().compareTo(source) == 0 && spiceJet.flights[i].getFrequency(spiceDay)) { 
                for(j=0;j<silkAir.silkSize;j++) {
                    String silkSource=silkAir.flights[j].getSource(); 
                    int depTimeHr = silkAir.getHour(j, 0);
                    int depTimeMin = silkAir.getMinute(j, 0);
                    String spiceDest = spiceJet.flights[i].getDestination();
                    spiceDest = spiceDest.trim();

                    int arrTimeHr = spiceJet.getHour(i, 1);
                    int arrTimeMin = spiceJet.getMinute(i, 1);
                    
                    if(spiceDest.compareTo(silkSource.substring(0,silkSource.lastIndexOf("(") - 1).toUpperCase().trim()) == 0 ) { 
                        if(silkAir.flights[j].getFrequency(silkDay)) {
                            int time = timeDifference(arrTimeHr, arrTimeMin, depTimeHr, depTimeMin); 
                            if(time >= 120 && time <= 360) {
                                if (seats < 15) {
                                flightCombo[current].setDeptSpice(spiceJet.flights[i].getDepTime());
                                flightCombo[current].setSpiceFlightNo(spiceJet.flights[i].getFlightNo());
                                flightCombo[current].setArrSpice(spiceJet.flights[i].getArrTime());
                                flightCombo[current].setIntermediate(spiceDest);
                                flightCombo[current].setVia(spiceJet.flights[i].getVia());
                                flightCombo[current].setDeptSilk(timeConverter(silkAir.flights[j].getDepTime()));
                                flightCombo[current].setSilkFlightNo(silkAir.flights[j].getFlightNo());
                                flightCombo[current].setArrSilk(timeConverter(silkAir.flights[j].getArrTime()));
                                totalDuration = timeDifference(spiceJet.getHour(i, 0), spiceJet.getMinute(i, 0), silkAir.getHour(j, 1), silkAir.getMinute(j, 1)) - timeDiffSingapore;
                                duration[current] = totalDuration;
                                flightCombo[current].setDuration(Integer.toString(totalDuration / 60) + "Hours " + Integer.toString(totalDuration % 60) +  " Minutes");
                                current++; } } } } } } }
        sortFlights(duration, flightCombo);
        return flightCombo;
    }
    
    //String to Time Converter...
    String timeConverter(String time) {
        String timeVar;
        String hour = time.substring(0, 2);
        String minute = time.substring(2, 4);
        if (Integer.parseInt(hour) > 12)
            timeVar = Integer.parseInt(hour) - 12 + ":" + minute + " PM"; 
        else
            timeVar = hour+ ":" + minute + " AM";
        if(time.contains("+"))
            timeVar += "(+1)";
        return timeVar; 
    }
    
}
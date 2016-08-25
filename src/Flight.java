/**
 * 
 * @author ProgJazz
 */

public class Flight {
    private String flightNo, source, destination, halt, depTime, arrTime;
    boolean days[] = new boolean[8];
    int seats;
    
    //Setter Methods...
    public void setFlightNo(String flightNo){
        this.flightNo = flightNo;
    }
    public void setSource(String source){
        this.source = source;
    }
    public void setDestination(String dest){
        destination = dest;
    }
    public void setVia(String halt){
        this.halt = halt;
    }
    public void setDepTime(String depTime){
        this.depTime = depTime;
    }
    public void setArrTime(String arrTime){
        this.arrTime = arrTime;
    }
    public void setDays(int day, boolean check) {
    	days[day] = check;
    }
    
    //Getter Methods...
    public String getFlightNo(){
        return flightNo;
    }
    public String getSource(){
        return source;
    }
    public String getDestination(){
    	return destination;
    }
    public String getVia(){
    	return this.halt;
    }
    public String getDepTime(){
    	return this.depTime;
    }
    public String getArrTime(){
    	return this.arrTime;
    }
    public boolean getFrequency(int i) {
    	return days[i];
    }
    public int getCapacity() {
    	return seats;
    }
}

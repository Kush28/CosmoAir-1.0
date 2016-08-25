/**
 * 
 * @author ProgJazz
 */
public class FinalSetterGetter {
    public String depSilk, arrSilk, arrSpice, depSpice, intermediate, totalTime, via, spiceFlightNo, silkFlightNo;
    
    //Final Setter Methods...
    public void setSilkFlightNo(String flightNo) {
        silkFlightNo= flightNo;
    }
    public void setSpiceFlightNo(String flightNo) {
        spiceFlightNo= flightNo;
    }
    public void setIntermediate(String inter) {
        intermediate= inter;
    }
    public void setDuration(String time) {
        totalTime= time;
    }
    public void setVia(String via) {
        this.via = via;
    }
    public void setDeptSilk(String depTime) {
        this.depSilk = depTime;
    }
    public void setArrSilk(String arrTime) {
        this.arrSilk= arrTime;
    }
    public void setDeptSpice(String depTime) {
        this.depSpice = depTime;
    }
    public void setArrSpice(String arrTime) {
        this.arrSpice= arrTime;
    }
    
    //Final Getter Methods...
    public String getSilkFlightNo() {
        return(silkFlightNo);
    }
    public String getSpiceFlightNo() {
        return(spiceFlightNo);
    }
    public String getIntermediate() {
        return(intermediate);
    }
    public String getDuration(){ 
        return(totalTime);
    }
    public String getVia() {
        return(via );
    }
    public String getDeptSilk() {
        return(depSilk);
    }
    public String getArrSilk() {
        return(arrSilk);
    }
    public String getDeptSpice() {
        return(depSpice) ;
    }
    public String getArrSpice() {
       return(arrSpice);
    }
}

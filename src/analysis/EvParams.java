/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author robak
 */
public class EvParams {

    protected Calendar from;
    protected Calendar to;
    protected int max;
    protected String type;
    
    public EvParams(Calendar from, Calendar to){
        type="ft";
        this.from=from;
        this.to=to;
    }
    public EvParams(Calendar from, int max){
        type="fm";
        this.from=from;
        this.max=max;
    }

    public Calendar getFrom(){
        return this.from;
    }
    public Calendar getTo(){
        if(type.equals("ft")) return this.to;
        else return null;
    }
    public int getMax(){
        if(type.equals("fm")) return this.max;
        else return -1;
    }

}

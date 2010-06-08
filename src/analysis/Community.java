/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import hiberex.User;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author wrozka
 */
public class Community {
    private Set<Number> cluster;
    private Map<Number, User> users;

    //Statistics
    private double stdDev;
    private double avg;
    private int count;
    
    public Set<Number> getCluster() {
        return cluster;
    }

    public Community(Set<Number> cluster, Map<Number, User> users)  {
        this.cluster = cluster;
        this.users = users;
    }

    public double getStdDev() {
        return stdDev;
    }

    public double getAvg() {
        return avg;
    }

    public int getCount() {
        return count;
    }
}

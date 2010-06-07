/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package analysis;

import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import hiberex.User;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections15.Transformer;

/**
 *
 * @author wrozka
 */
public class UserLabeller implements Transformer<Number,String> {
    private Map<Number, User> users;
    public UserLabeller(Map<Number, User> users) {
        this.users = users;
    }
    
    public String transform(Number v) {
        return users.get(v).getName();
    }
}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.roarsoftware.lastfm.Session;
import net.roarsoftware.lastfm.User;

/**
 *
 * @author wrozka
 */
public class Crawler {
    private static String key = "0d64b5ba5584e52f89f0648f1b384bff";
   
    public static void main(String[] args){
        String name = "kiwiora";

            addUserFriends(name);
        
    }

    public static void addUserFriends(String name) {
        hiberex.User user = hiberex.User.getUserByName(name);
        Collection<User> friends = User.getFriends(name, key);
        
        List l = new ArrayList();
        for(User usr : friends) {
            
            hiberex.User friend = hiberex.User.getUserByName(usr.getName());
            friend.setName(usr.getName());
            friend.setAge(usr.getAge());
            friend.setCountry(user.getCountry());
            if (usr.getGender() != null)
                friend.setGender(usr.getGender().charAt(0));

            System.out.println(usr.getName() + " " + usr.getCountry() + " " + usr.getGender());

            l.add(friend);
        }

        user.setFriends(l);
     
    }

}

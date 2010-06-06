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
public class UserCrawler {
   
    public static void main(String[] args){
        List<hiberex.User> users = hiberex.User.getUsers();
        for(hiberex.User u : users) {
            if (u.getFriends().size() == 0) {
                System.out.println(u.getName());
                addUserFriends(u);
            }

        }
    }

    public static void addUserFriends(hiberex.User user) {
        Collection<User> friends = User.getFriends(user.getName(), Crawler.KEY);
        
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

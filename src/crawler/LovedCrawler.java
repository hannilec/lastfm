/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;

import hiberex.Shout;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.roarsoftware.lastfm.Session;
import net.roarsoftware.lastfm.Track;
import net.roarsoftware.lastfm.User;

/**
 *
 * @author wrozka
 */
public class LovedCrawler {
    public static void main(String[] args){
        for (hiberex.User u : hiberex.User.getUsers()) {
            if (u.getLovedtracks().size() == 0) {
                System.out.println("== " + u.getName() + " ==");
                addLoved(u);
            }
        }
        System.out.println("end");
    }

    public static void addLoved(hiberex.User user) {
        System.out.println("LovedTracks: " + user.getName());
        Collection<Track> tracks =  User.getLovedTracks(user.getName(), Crawler.KEY);
        

        List l = new ArrayList();
        for(Track trc : tracks) {
            System.out.println(trc.getName());
            hiberex.Track track = hiberex.Track.getTrack(trc.getName(), trc.getArtist());
            l.add(track);

        }
        System.out.println("Saving loved tracks");
        user.setLovedtracks(l);
    }
}

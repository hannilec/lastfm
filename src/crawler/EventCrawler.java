/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;

//import hiberex.Event;
//import hiberex.User;
//import java.util.ArrayList;
import hiberex.AdditionalFunc;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import net.roarsoftware.lastfm.Event;
//import net.roarsoftware.lastfm.Geo;
import net.roarsoftware.lastfm.PaginatedResult;
import net.roarsoftware.lastfm.User;
/**
 *
 * @author robak
 */
public class EventCrawler {


    static List<hiberex.Event> evInDB;
    static List<hiberex.User> users;

    public static void main(String[] args){
       //  AdditionalFunc.createDatabase();
       // clear(hiberex.Event.getAllEvents());
        //new hiberex.Event("testEvent",new Date(10,10,10));
        evInDB=hiberex.Event.getAllEvents();
        users=hiberex.User.getUsers(100);//hiberex.User.getUsers(); FIXME - wszystkich pobierac
        System.out.println(evInDB.size());
        System.out.println("===================================");
        for(hiberex.Event e:evInDB){
            //System.out.println(e.getTitle());
        }
        System.out.println("===================================");

        
            for (hiberex.User u : users) {
               
                    System.out.println("== " + u.getName() + " ==");
                    try{
                        //addEvents(u);
                        addPastEvents(u);
                    }catch(net.roarsoftware.lastfm.CallException e){
                        
                    }
            }
            
            System.out.println("end");
        }

        public static void addEvents(hiberex.User u) {
            //System.out.println("LovedTracks: " + user.getName());


            System.out.println("future events");
            //future events
            Collection<Event> events =User.getEvents(u.getName(), Crawler.KEY);

                System.out.println(events.size());

            //List l = new ArrayList();
            for(Event ev: events) {
                System.out.println(ev.getTitle());
                 addEvent(ev);//add to db if not there
            }

            System.out.println("Saving events");
            //user.setLovedtracks(l);
        }

        public static void addPastEvents(hiberex.User u){


            System.out.println("----past events----");
            //past events
            PaginatedResult<Event> pagevents=User.getPastEvents(u.getName(), Crawler.KEY);

            //pagevents.
            int pages=pagevents.getTotalPages();
            System.out.println("pages: "+pages);

                Collection<Event> pageResults = pagevents.getPageResults();
                for(Event e:pageResults){
                    System.out.println(e.getTitle());
                    //addEvent(e);//add to db if not there
                }


            //if more than one page
            for(int i=2;i<=pages;i++){
                pagevents=User.getPastEvents(u.getName(), i, 1000, Crawler.KEY);
                pageResults = pagevents.getPageResults();
                for(Event e:pageResults){
                    System.out.println(e.getTitle());
                   // addEvent(e);//add to db if not there
                }

            }



        }

    private static void clear(List<hiberex.Event> allEvents) {
        for(hiberex.Event e:allEvents){
            //if(e.getTitle()==null){
                AdditionalFunc.deleteObject(e);
            //}
        }
    }

    private static boolean isInDB(Event e){
        //boolean res=false;
        for(hiberex.Event ev:evInDB){
            if(ev.getTitle().equals(e.getTitle())) return true;
        }
        return false;
    }


    private static void addEvent(Event e) {

        System.out.println(e.getTitle()+" isindb "+isInDB(e));
        if(isInDB(e)) return;


        hiberex.Event nev=new hiberex.Event(e.getTitle(),e.getStartDate());//add to db

        Collection<User> attendees=null;
        boolean wp=false;
        int i=0;
        while(!wp & i<40){

            try{
                attendees=Event.getAttendees(String.valueOf(e.getId()), Crawler.KEY);
                wp=true;
            }catch(java.lang.NullPointerException ex){
                System.out.println("jeszcze raz");
                wp=false;
                i++;
            }
            

        }
        if(i<40){
        System.out.println("att: "+attendees.size());
        


        //connect with attendees
        
        for(User u:attendees){
            hiberex.User att;
            System.out.println(u.getName());
            if((att=userInDB(u.getName()))!=null){//if this user is in db
                System.out.println("--user in db");
                att.addEvent(nev);
                nev.addAttendees(att);
            }
        }

        }
        evInDB.add(nev);//add to local copy list

    }

    private static hiberex.User userInDB(String name) {

        for(hiberex.User u:users){
            if(u.getName().equals(name)) return u;
        }
        return null;

        //throw new UnsupportedOperationException("Not yet implemented");
    }


}

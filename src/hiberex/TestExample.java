/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

/**
 *
 * @author robak
 */
//package de.laliluna.example;

//import java.lang.reflect.Field;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
//import java.util.Map;
//import java.util.Set;

//import org.hibernate.HibernateException;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.Transaction;


//import de.laliluna.hibernate.SessionFactoryUtil;

public class TestExample {


  /**
   * @param args
   */
  public static void main(String[] args) {

   /*  

    Map<Integer,Event> evs=new HashMap<Integer,Event>();
    Event ev=new Event();
    User us=new User();
    us.setName("tomus");
    ev.setTitle("ala");
    evs.put(0, ev);
    ev=new Event();
    ev.setTitle("ola");
    evs.put(1,ev);
    us.setEvents(evs);
    createObject(us);
    
    

   us=new User();
   us.setName("jurek");
   createObject(us);

*/
   // AdditionalFunc.createDatabase();
    /*Event ev=new Event();
    ev.setTitle("costammm");
    Map s=new HashMap();
    s.put(0, us);
    ev.setAttendees(s);
    createObject(ev);*/
/*
      System.out.println("start=="+AdditionalFunc.start);
      User us2=new User();
      System.out.println("user created");
      us2.setName("joo");
      
      List ltrack=new ArrayList<Track>();
      Track tr=new Track();
      tr.setName("trac");
      Track tr2=new Track();
      tr2.setName("trac2");
      ltrack.add(tr);
      ltrack.add(tr2);
//      createObject(tr);
  //    createObject(tr2);
      us2.setLovedtracks(ltrack);
      System.out.println("loved added");
    //  createObject(us2);

      
      Shout sh=new Shout("body",new Date(),us2);
      sh.setBody("alala");
      sh.setAuthor(us2);

     // sh.setReferto(us2);
      //createObject(sh);
      User us=new User();
      us.setName("jas2");
      List lst=new ArrayList();
      lst.add(sh);
      us.setShouts(lst);
      //createObject(us);

      us2.setName(us2.getName()+"upd");
      //updateObject(us2);
      System.out.println("al created ");

      // us.delete();
      System.out.println("===========");
      sh.delete();
      Shout s=new Shout("body",new Date(),us2);
      Shout f=new Shout("body2",new Date(),us2);

      System.out.println("===========");
      // us2.delete();
      System.out.println("===========");
*/


      User u = User.getUserByName("mrocznaowca");
      System.out.println(u.getName());
      //Hibernate.initialize(u);
     // Hibernate.initialize(u.getFriends());
      List<User> friends=u.getFriends();
      System.out.println("ujjjj"+friends.size());
      /*
      for(User a:friends){
          System.out.println(a.getName());
      }*/
     // printUsers();

     // System.out.println(User.getUserWithoutFriends());



  }
      private static void printUsers() {
          System.out.println("== Users ==");
      List<Object> uss=AdditionalFunc.getObject("User",null,User.class);
      for(Object us:uss){
          User a=(User)us;
          System.out.println(a.getName());
      }
      System.out.println("== End ==");
      }

  }




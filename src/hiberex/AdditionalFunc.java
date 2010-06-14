/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

import java.lang.reflect.Field;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
//import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringEscapeUtils;
import org.hibernate.HibernateException;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author robak
 */
public class AdditionalFunc {

   private static AdditionalFunc instance = null;


   protected AdditionalFunc() {
      // Exists only to defeat instantiation.
   }
   public static AdditionalFunc getInstance() {
      if(instance == null) {
         instance = new AdditionalFunc();
      }
      return instance;
   }



    final static Logger logger = LoggerFactory.getLogger(TestExample.class);

    static boolean start=false;

  public static void createObject(Object obj) {
    //System.out.println("-------------------");
    Transaction tx = null;
    Session session = SessionFactoryUtil.getInstance().getCurrentSession();
    try {
      tx = session.beginTransaction();
      session.save(obj);
      tx.commit();
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        try {
// Second try catch as the rollback could fail as well
          tx.rollback();
        } catch (HibernateException e1) {
          logger.debug("Error rolling back transaction");
        }
// throw again the first exception
        throw e;
      }
    }
    //System.out.println("-------------------");
  }


   public static void updateObject(Object obj) {
    if(!start){
        start=true;

        Transaction tx = null;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
          tx = session.beginTransaction();
          session.update(obj);
          tx.commit();
        } catch (RuntimeException e) {
          if (tx != null && tx.isActive()) {
            try {
    // Second try catch as the rollback could fail as well
              tx.rollback();
            } catch (HibernateException e1) {
             logger.debug("Error rolling back transaction");
            }
    // throw again the first exception
            throw e;
          }
        }

    start=false;
    }
  }


  public static void deleteObject(Object obj) {
    Transaction tx = null;
    Session session = SessionFactoryUtil.getInstance().getCurrentSession();
    try {
      tx = session.beginTransaction();
      session.delete(obj);
      tx.commit();
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        try {
// Second try catch as the rollback could fail as well
          tx.rollback();
        } catch (HibernateException e1) {
         logger.debug("Error rolling back transaction");
        }
// throw again the first exception
        throw e;
      }
    }
  }

   public static void createTable(String name,List<String> fields) {
    Transaction tx = null;
    Session session = SessionFactoryUtil.getInstance().getCurrentSession();
    try {

      String query="create table "+name+" (";
      for(String field:fields){
          query+=field;
          query+=',';
      }
      query=query.substring(0,query.length()-1);
      query+=")";
      //System.out.println("result:"+query);
      tx = session.beginTransaction();
      session.createSQLQuery(query).executeUpdate();

      //session.save(honey);
      tx.commit();
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        try {
// Second try catch as the rollback could fail as well
          tx.rollback();
        } catch (HibernateException e1) {
          logger.debug("Error rolling back transaction");
        }
// throw again the first exception
        throw e;
      }
    }
  }

  public static void listTable(String name) {
      if(!start){
        start=true;
    Transaction tx = null;
    Session session = SessionFactoryUtil.getInstance().getCurrentSession();
    try {
      tx = session.beginTransaction();
      List lines = session.createSQLQuery("select name from "+name).list();

      for (Iterator iter = lines.iterator(); iter.hasNext();) {
        Object element = (Object) iter.next();
        //logger.debug("{}", element);
        System.out.println(element);
      }
      tx.commit();
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        try {
// Second try catch as the rollback could fail as well
          tx.rollback();
        } catch (HibernateException e1) {
          logger.debug("Error rolling back transaction");
        }
// throw again the first exception
        throw e;
      }


    }
    start=false;
  }


  }


      public static int getCount(String name,String dep){
        Transaction tx = null;
        dep=escapeString(dep);
        int res=0;
        String query="select count(id) from "+name;    /*  TODO    */
    //if(adddep!=null) query+=" "+adddep;//joins and so
    if(dep!=null) query+=" where " + dep;
    //List<Object> res=new ArrayList();
    Session session = SessionFactoryUtil.getInstance().getCurrentSession();
    try {
      tx = session.beginTransaction();
      //System.out.println("Q:"+query);
      List lines = session.createSQLQuery(query).list();

      if(lines!=null && lines.size()>0){
          res=(Integer)lines.get(0);
      }


      //System.out.print(usr.getName());
      tx.commit();
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        try {
// Second try catch as the rollback could fail as well
          tx.rollback();
        } catch (HibernateException e1) {
          logger.debug("Error rolling back transaction");
        }
// throw again the first exception
        throw e;
      }


    }



    return res;

    }


      public static List<Pair> getPairs(String name, String first, String sec){

              if(!start){
        start=true;
        Transaction tx = null;

        List<Pair> res=new ArrayList();
        List list=null;
        String query="select "+first+","+sec+" from "+name;
        //if(adddep!=null) query+=" "+adddep;//joins and so
        //if(dep!=null) query+=" where " + dep;
        //List<Object> res=new ArrayList();
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
          tx = session.beginTransaction();
          //System.out.println("Q:"+query);
          list = session.createSQLQuery(query).list();
          for(Object ob:list){
              Object[] obt=(Object[])ob;
              /*System.out.println(obt.length);
              for(Object a:obt){
                  System.out.println();
              }*/
              res.add(new Pair((Integer)obt[0],(Integer)obt[1]));
          }



          //System.out.print(usr.getName());
          tx.commit();
        } catch (RuntimeException e) {
          if (tx != null && tx.isActive()) {
            try {
    // Second try catch as the rollback could fail as well
              tx.rollback();
            } catch (HibernateException e1) {
              logger.debug("Error rolling back transaction");
            }
    // throw again the first exception
            throw e;
          }


        }


        start=false;
    return res;}
         return null;

    }


    /*public static List<Pair> getFriends(String name) {
         if(!start){
        start=true;
        Transaction tx = null;

        List<Pair> res=new ArrayList();
        List list=null;
        String query="select user_id, user2_id  from "+name;
        //if(adddep!=null) query+=" "+adddep;//joins and so
        //if(dep!=null) query+=" where " + dep;
        //List<Object> res=new ArrayList();
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
          tx = session.beginTransaction();
          //System.out.println("Q:"+query);
          list = session.createSQLQuery(query).list();
          for(Object ob:list){
              Object[] obt=(Object[])ob;
           
              res.add(new Pair((Integer)obt[0],(Integer)obt[1]));
          }



          //System.out.print(usr.getName());
          tx.commit();
        } catch (RuntimeException e) {
          if (tx != null && tx.isActive()) {
            try {
    // Second try catch as the rollback could fail as well
              tx.rollback();
            } catch (HibernateException e1) {
              logger.debug("Error rolling back transaction");
            }
    // throw again the first exception
            throw e;
          }


        }


        start=false;
    return res;}
         return null;

        //throw new UnsupportedOperationException("Not yet implemented");
    }

*/
    public static List<Integer> getIds(String name,String dep){
         if(!start){
        start=true;
        //dep=escapeString(dep);
        Transaction tx = null;

        List<Integer> res=null;
        String query="select id from "+name;    
        //if(adddep!=null) query+=" "+adddep;//joins and so
        if(dep!=null) query+=" where " + dep;
        System.out.println("Q:"+query);
        //List<Object> res=new ArrayList();
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
          tx = session.beginTransaction();
          //System.out.println("Q:"+query);
          res = session.createSQLQuery(query).list();

          


          //System.out.print(usr.getName());
          tx.commit();
        } catch (RuntimeException e) {
          if (tx != null && tx.isActive()) {
            try {
    // Second try catch as the rollback could fail as well
              tx.rollback();
            } catch (HibernateException e1) {
              logger.debug("Error rolling back transaction");
            }
    // throw again the first exception
            throw e;
          }


        }


        start=false;
    return res;}
         return null;

    }




    public static List<User> getUsersById(Set<Number> usids){
        List<User> us=null;
        if(!start){
            start=true;
            Transaction tx = null;

            us=new ArrayList<User>();
            Session session = SessionFactoryUtil.getInstance().getCurrentSession();
            try {
            tx = session.beginTransaction();



            for (Iterator iter = usids.iterator(); iter.hasNext();) {

                Object element = (Object) iter.next();
                //System.out.println(element);
                us.add((User)session.get(User.class, (Integer)element));

            }



            tx.commit();
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive()) {
                    try {
    // Second try catch as the rollback could fail as well
                        tx.rollback();
                    } catch (HibernateException e1) {
                        logger.debug("Error rolling back transaction");
                    }
    // throw again the first exception
                throw e;
                }


           }
           start=false;
    //return res;
        }

        return us;
    }


    private static String escapeString(String str){
        if(str==null) return null;
        int k;
        while((k=str.indexOf('\''))!=-1){
            str.replace("\'", "\'\'");
        }
        return str;
    }



    public static List<Object> getObject(String name,String adddep,String dep,Class cls) {
      if(!start){
        start=true;
        adddep=escapeString(adddep);
        dep=escapeString(dep);

    Transaction tx = null;
    String dot="";
    if(name.indexOf('_')==-1){
        dot+=".";
    }else{
        dot+=".user_";
    }
    String query="select "+name+dot+"id from "+name;    /*  TODO    */
    if(adddep!=null) query+=" "+adddep;//joins and so

    if(dep!=null && !dep.contains("FETCH")) query+=" where " + dep;
        else if(dep!=null) query+=" " + dep;


    List<Object> res=new ArrayList();
    Session session = SessionFactoryUtil.getInstance().getCurrentSession();
    try {
      tx = session.beginTransaction();
      System.out.println("Q:"+query);
      List lines = session.createSQLQuery(query).list();

      for (Iterator iter = lines.iterator(); iter.hasNext();) {
        Object element = (Object) iter.next();
        //logger.debug("{}", element);
        //System.out.println(element);
       // String cls=name+".class";
        res.add(session.get(cls, (Integer)element));
        //System.out.println(usr.getName());
      }
      

      //System.out.print(usr.getName());
      tx.commit();
    } catch (RuntimeException e) {
      if (tx != null && tx.isActive()) {
        try {
// Second try catch as the rollback could fail as well
          tx.rollback();
        } catch (HibernateException e1) {
          logger.debug("Error rolling back transaction");
        }
// throw again the first exception
        throw e;
      }


    }
    start=false;
    return res;
  }
    return null;

  }

   public static void createDatabase(){


      start=true;
      List arg=new ArrayList<String>();
      arg.add("id int, playlist_id int, track_id int");
      createTable("Playlist_Track",arg);

      arg=new ArrayList<String>();
      arg.add("id int, user_id int, event_id int");
      createTable("User_Event",arg);

      arg=new ArrayList<String>();
      arg.add("id int, user_id int, playlist_id int");
      createTable("User_Playlist",arg);

      arg=new ArrayList<String>();
      arg.add("id int, user_id int, user2_id int");
      createTable("User_User",arg);

      arg=new ArrayList<String>();
      arg.add("id int, user_id int, shout_id int");
      createTable("User_Shout",arg);

      arg=new ArrayList<String>();
      arg.add("id int, user_id int, track_id int");
      createTable("User_LovedTrack",arg);

      arg=new ArrayList<String>();
      arg.add("id int, user_id int, track_id int");
      createTable("User_Track",arg);

      arg=new ArrayList<String>();
      arg.add("id int, user_id int, tag_id int");
      createTable("User_Tag",arg);


      arg=new ArrayList<String>();
      arg.add("id int, artist_id int, artist2_id int");
      createTable("Artist_Artist",arg);

      arg=new ArrayList<String>();
      arg.add("id int, artist_id int, track_id int");
      createTable("Artist_Track",arg);

      arg=new ArrayList<String>();
      arg.add("id int, artist_id int, tag_id int");
      createTable("Artist_Tag",arg);
      //createTable("Artist_Track",arg);



      arg=new ArrayList<String>();
      arg.add("id int, event_id int, venue_id int");
      createTable("Event_Venue",arg);

      arg=new ArrayList<String>();
      arg.add("id int, artist_id int, venue_id int");
      createTable("Artist_Venue",arg);

      //createTable("Album_Track",arg);

      arg=new ArrayList<String>();
      arg.add("id int, event_id int, shout_id int");
      createTable("Event_Shout",arg);

      arg=new ArrayList<String>();
      arg.add("id int, event_id int, tag_id int");
      createTable("Event_Tag",arg);

      arg=new ArrayList<String>();
      arg.add("id int, track_id int, tag_id int");
      createTable("Track_Tag",arg);


      arg=new ArrayList<String>();
      arg.add("id int, group_id int, user_id int");
      createTable("Group_User",arg);

      arg=new ArrayList<String>();
      arg.add("id int, event_id int, artist_id int");
      createTable("Event_Artist",arg);

    dropTable("Shout");
    dropTable("Venue");
    dropTable("Playlist");
    dropTable("User");
    dropTable("Track");
    dropTable("Event");
    dropTable("Artist");
    dropTable("Tag");


    createTableForObject(new Tag(),null);
    createTableForObject(new Artist(),null);
   // createTableForObject(new Album(),null);
    createTableForObject(new Event(),null);
    createTableForObject(new Track(),null);
    createTableForObject(new User(),null);
    createTableForObject(new Playlist(),null);
    createTableForObject(new Venue(),null);
    //createTableForObject(new Group(),null);

    createTableForObject(new Shout(),null);
    
    
    start=false;
  }

private static void createTableForObject(Object obj,String additional){


      Field[] fs=obj.getClass().getDeclaredFields();
      List<String> fields=new ArrayList<String>();
     if(additional!=null) fields.add(additional);
      //System.out.println(obj.getClass().getSimpleName());
      //query+=obj.getClass().getSimpleName();
      //query+=" (";
      String field;
      for(Field f:fs){
          //f.getName()
          System.out.println(f.getName());
          field=f.getName();//name of field
          //query+=' ';
          System.out.println(f.getType().toString());
          String type=f.getType().toString();
          if(type.equals("class java.lang.Integer")){
              if(field.equals("id")){
                 fields.add(field+" integer PRIMARY KEY NOT NULL");
                // fields.add(" primary key");
              }else
                 fields.add(field+" int");
              System.out.println(fields.get(0));
          }else if(type.equals("class java.lang.String")){
              fields.add(field+" varchar(300)");
              System.out.println("string");
          }else if(type.equals("class java.util.Date")){
             fields.add(field+" date");
              System.out.println("date");
          }else if(type.startsWith("interface")){
             // fields.add(field+"_id int unsigned");
             //connected by hibernate
          }else if(type.equals("class hiberex.AdditionalFunc")){
             //omit
          }else if(type.startsWith("class")){
              type=type.substring(type.lastIndexOf('.')+1);
              //System.out.println("type:-----------"+type);
              fields.add(field+"_id int");
              //fields.add("PRIMARY KEY(id)");
              fields.add(" FOREIGN KEY ("+field+"_id) REFERENCES "+type+"(id)");

          }else if(type.startsWith("long")){
              fields.add(field+" bigint");
          }else{
             fields.add(field+" "+type);
          }
      }

      createTable(obj.getClass().getSimpleName(),fields);
  }

  private static void dropTable(String name){

      Transaction tx = null;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
          tx = session.beginTransaction();
          session.createSQLQuery("drop table "+name).executeUpdate();
          
          tx.commit();
        } catch (RuntimeException e) {
          if (tx != null && tx.isActive()) {
            try {
    // Second try catch as the rollback could fail as well
              tx.rollback();
            } catch (HibernateException e1) {
              logger.debug("Error rolling back transaction");
            }
    // throw again the first exception
            throw e;
          }


        }
  }


  public static void alterTable(String name){
         Transaction tx = null;
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
          tx = session.beginTransaction();
          session.createSQLQuery("alter table Track add column artist_id int").executeUpdate();

          tx.commit();
        } catch (RuntimeException e) {
        if (tx != null && tx.isActive()) {
            try {
    // Second try catch as the rollback could fail as well
              tx.rollback();
            } catch (HibernateException e1) {
              //logger.debug("Error rolling back transaction");
            }
    // throw again the first exception
            throw e;
          }
        }
  }


  public static List<Integer> getMostConnUsr(int max){
     if(!start){
        start=true;

        Transaction tx = null;

        //List<Integer> res=null;

        String query="select user_id,count(distinct u.cnt) as sum from (select user_id,event_id as cnt from User_Event " +
                "union all " +
                "select user_id,user2_id as cnt from User_User " +
                "union all " +
                "select user_id,track_id as cnt from User_LovedTrack) as u group by user_id order by sum desc " +
                "fetch first "+max+" rows only";

        System.out.println("Q:"+query);
        List<Object> resp;//=new ArrayList();
        List<Integer> res=new ArrayList();
        Session session = SessionFactoryUtil.getInstance().getCurrentSession();
        try {
          tx = session.beginTransaction();
          //System.out.println("Q:"+query);
          resp = session.createSQLQuery(query).list();

          for(Object a: resp){
              Object[] obt=(Object[])a;
              res.add((Integer)obt[0]);
             // System.out.println(obt[0]+" "+obt[1]);
          }



          //System.out.print(usr.getName());
          tx.commit();
        } catch (RuntimeException e) {
          if (tx != null && tx.isActive()) {
            try {
    // Second try catch as the rollback could fail as well
              tx.rollback();
            } catch (HibernateException e1) {
              logger.debug("Error rolling back transaction");
            }
    // throw again the first exception
            throw e;
          }


        }


        start=false;
    return res;}
         return null;
  }


  

}

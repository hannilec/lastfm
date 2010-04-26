/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
//import java.util.Map;

/**
 *
 * @author robak
 */

public class User {
        /**
            <id>1000002</id>
            <name>RJ</name>
            <realname>Richard Jones </realname>
            <url>http://www.last.fm/user/RJ</url>
            <image>http://userserve-ak.last.fm/serve/126/8270359.jpg</image>
            <country>UK</country>
            <age>27</age>
            <gender>m</gender>
            <subscriber>1</subscriber>
            <playcount>54189</playcount>
            <playlists>4</playlists>
            <bootstrap>0</bootstrap>
            <registered unixtime="1037793040">2002-11-20 11:50</registered>
         */
        private Integer id;
	private String name;
	private String realname;
        private String country;
        private Integer age;
        private char gender;
     //   private boolean subscribe;
        private long playcount;
       // private String type="User";
        private List playlists;
        //private int bootstrap;
        private String regUnixtime;
        private List events;
        private List friends;
        private List shouts;
        private List lovedtracks;
        private List toptags;
        private List tracks;

        private AdditionalFunc af=AdditionalFunc.getInstance();


    public static User getUserByName(String name) {
        List<Object> res = AdditionalFunc.getObject("User", "name like '" + name +"'", User.class);

        if (res.size() == 0)
        {
            User usr = new User();
            usr.setName(name);
            return usr;
        }
        if (res.size() == 1)
            return (User)res.get(0);

        return null;
    }

    public static User getUserWithoutFriends() {
        List<Object> res = AdditionalFunc.getObject("User", null, User.class);
        
        for(Object o:res){
            User u = (User)o;
            u.getFriends();
        }

        return null;
    }

     public User(){
         /*playlists=new ArrayList();
         events=new ArrayList();
         friends=new ArrayList();
         shouts=new ArrayList();
         lovedtracks=new ArrayList();
         tracks=new ArrayList();
         toptags=new ArrayList();*/
                  if(!af.start){
             System.out.println("creating user");
             af.start=true;
             af.createObject(this);
             System.out.println("usr creat");
             af.start=false;
         }else{
             System.out.println("user ");
         }

     }
     
    public void delete(){
        af.deleteObject(this);
    }
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
        //af.updateObject(this);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
        //if(!af.start)
        af.updateObject(this);
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
        af.updateObject(this);
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
        af.updateObject(this);
    }

    /**
     * @return the age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
        af.updateObject(this);
    }

    /**
     * @return the gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(char gender) {
        this.gender = gender;
        af.updateObject(this);
    }

    /**
     * @return the subscribe
     */
   /* public boolean isSubscribe() {
        return subscribe;
    }*/

    /**
     * @param subscribe the subscribe to set
     */
    /*public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
        af.updateObject(this);
    }*/

    /**
     * @return the playcount
     */
    public long getPlaycount() {
        return playcount;
    }

    /**
     * @param playcount the playcount to set
     */
    public void setPlaycount(long playcount) {
        this.playcount = playcount;
        af.updateObject(this);
    }

    /**
     * @return the playlists
     */
    public List getPlaylists() {
        return playlists;
    }

    /**
     * @param playlists the playlists to set
     */
    public void setPlaylists(List playlists) {
        this.playlists = playlists;
        af.updateObject(this);
    }

    /**
     * @return the regUnixtime
     */
    public String getRegUnixtime() {
        return regUnixtime;
    }

    /**
     * @param regUnixtime the regUnixtime to set
     */
    public void setRegUnixtime(String regUnixtime) {
        this.regUnixtime = regUnixtime;
        af.updateObject(this);
    }

    /**
     * @return the events
     */
    public List getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List events) {
        this.events = events;
        af.updateObject(this);
    }

    /**
     * @return the friends
     */
    public List getFriends() {
        return friends;
    }

    /**
     * @param friends the friends to set
     */
    public void setFriends(List friends) {
        this.friends = friends;
        af.updateObject(this);
    }

    /**
     * @return the shouts
     */
    public List getShouts() {
        return shouts;
    }

    /**
     * @param shouts the shouts to set
     */
    public void setShouts(List shouts) {
        this.shouts = shouts;
        af.updateObject(this);
    }

    /**
     * @return the lovedtracks
     */
    public List getLovedtracks() {
        return lovedtracks;
    }

    /**
     * @param lovedtracks the lovedtracks to set
     */
    public void setLovedtracks(List lovedtracks) {
        this.lovedtracks = lovedtracks;
        af.updateObject(this);
    }

    /**
     * @return the toptags
     */
    public List getToptags() {
        return toptags;
    }

    /**
     * @param toptags the toptags to set
     */
    public void setToptags(List toptags) {
        this.toptags = toptags;
        af.updateObject(this);
    }

    /**
     * @return the tracks
     */
    public List getTracks() {
        return tracks;
    }

    /**
     * @param tracks the tracks to set
     */
    public void setTracks(List tracks) {
        this.tracks = tracks;
        af.updateObject(this);
    }

 

 

}

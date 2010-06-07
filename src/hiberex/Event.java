/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

import java.util.ArrayList;
import java.util.Date;
//import java.util.List;
import java.util.List;

/**
 *
 * @author robak
 */
public class Event {
/*
 *<lfm status="ok">
<event xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#" >
  <id>1073657</id>
  <title>Neko Case</title>
  <artists>
    <artist>Neko Case</artist>
    <headliner>Neko Case</headliner>
  </artists>
  <venue>
    <id>8783057</id>
    <name>Ryman Auditorium</name>
    <location>
      <city>Nashville</city>
      <country>United States</country>
      <street>116 Fifth Avenue North</street>
      <postalcode>37219</postalcode>
      <geo:point>
         <geo:lat>36.16148</geo:lat>
         <geo:long>-86.777959</geo:long>
      </geo:point>
    </location>
    <url>http://www.last.fm/venue/8783057</url>
  </venue>
  <startDate>Sat, 25 Jul 2009 20:00:00</startDate>
  <description></description>
  <image size="small">http://userserve-ak.last.fm/serve/34/17278547.jpg</image>
  <image size="medium">http://userserve-ak.last.fm/serve/64/17278547.jpg</image>
  <image size="large">http://userserve-ak.last.fm/serve/126/17278547.jpg</image>
  <attendance>2</attendance>
  <reviews>0</reviews>
  <tag>lastfm:event=1073657</tag>
  <url>http://www.last.fm/event/1073657</url>
  <website>http://www.ryman.com/</website>
  <tickets>
    <ticket supplier="TicketMaster">http://www.last.fm/affiliate_redirect.php?restype=29&amp;id=1073657&amp;supplier=12</ticket>
  </tickets>
</event></lfm>
 */

  /*
   *<shouts event="328799" total="5">
  <shout>
    <body>Blah</body>
    <author>joanofarctan</author>
    <date>Fri, 12 Dec 2008 13:20:41</date>
  </shout>
  ...
</shouts>
   */
    private Integer id;
    private String title;
    private List artists;
    private List venue;
    private Date startDate;
    private List attendees;
    private List shouts;
    private List tag;

    private AdditionalFunc af=new AdditionalFunc();



    public Event(){
        /*artists=new ArrayList();
        venue=new ArrayList();
        attendees=new ArrayList();
        shouts=new ArrayList();
        tag=new ArrayList();*/
        if(!af.start){
            af.start=true;
            af.createObject(this);
            af.start=false;
        }else{
             System.out.println("events ");
        }
    }

    public Event(String title,Date date){
           if(!af.start){
            af.start=true;
                this.title=title;
                this.startDate=date;
            af.createObject(this);
            af.start=false;
        }else{
             System.out.println("events ");
        }
    }
   
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
        af.updateObject(this);
    }

   




    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        af.updateObject(this);
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
     * @return the artists
     */
    public List getArtists() {
        return artists;
    }

    /**
     * @param artists the artists to set
     */
    public void setArtists(List artists) {
        this.artists = artists;
        af.updateObject(this);
    }

    /**
     * @return the venue
     */
    public List getVenue() {
        return venue;
    }

    /**
     * @param venue the venue to set
     */
    public void setVenue(List venue) {
        this.venue = venue;
        af.updateObject(this);
    }

    /**
     * @return the attendees
     */
    public List getAttendees() {
        return attendees;
    }

    /**
     * @param attendees the attendees to set
     */
    public void setAttendees(List attendees) {
        this.attendees = attendees;
        af.updateObject(this);
    }

    public void addAttendees(User u){
        if(this.attendees==null){
            this.attendees=new ArrayList();
        }
        this.attendees.add(u);
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
     * @return the tag
     */
    public List getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(List tag) {
        this.tag = tag;
        af.updateObject(this);
    }


    public static List<Event> getAllEvents(){
        List lst=AdditionalFunc.getObject("Event", null, null, Event.class);
        System.out.println(lst.size());
        System.out.println("got it");
        return lst;
    }




   
   

}

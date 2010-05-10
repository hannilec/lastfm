/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

import java.util.ArrayList;
import java.util.Date;
//import java.util.List;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
//import java.util.List;

/**
 *
 * @author robak
 */
public class Track {

    /*
 <track>
  <id>1019817</id>
  <name>Believe</name>
  <mbid/>
  <url>http://www.last.fm/music/Cher/_/Believe</url>
  <duration>240000</duration>
  <streamable fulltrack="1">1</streamable>
  <listeners>69572</listeners>
  <playcount>281445</playcount>
  <artist>
    <name>Cher</name>
    <mbid>bfcc6d75-a6a5-4bc6-8282-47aec8531818</mbid>
    <url>http://www.last.fm/music/Cher</url>
  </artist>
  <album position="1">
    <artist>Cher</artist>
    <title>Believe</title>
    <mbid>61bf0388-b8a9-48f4-81d1-7eb02706dfb0</mbid>
    <url>http://www.last.fm/music/Cher/Believe</url>
    <image size="small">http://userserve-ak.last.fm/serve/34/8674593.jpg</image>
    <image size="medium">http://userserve-ak.last.fm/serve/64/8674593.jpg</image>
    <image size="large">http://userserve-ak.last.fm/serve/126/8674593.jpg</image>
  </album>
  <toptags>
    <tag>
      <name>pop</name>
      <url>http://www.last.fm/tag/pop</url>
    </tag>
    ...
  </toptags>
  <wiki>
    <published>Sun, 27 Jul 2008 15:44:58 +0000</published>
    <summary>...</summary>
    <content>...</content>
  </wiki>
</track>
 */


    private Integer id;
    private String name;
    private long listeners;
    private long playcount;

    private List artist;
   // private List album;
    private List tags;
    private Date published;
    
    private AdditionalFunc af=AdditionalFunc.getInstance();

    public Track(){
      /*  artist=new ArrayList();
        tags=new ArrayList();
        published=null;*/
        if(!af.start){
            System.out.println("not from start");
            af.start=true;
            af.createObject(this);
            af.start=false;
            
        }else{
             //System.out.println("track ");
        }
    }

    public static Track getTrack(String name, String artist) {
        List<Object> res = AdditionalFunc.getObject("Track", "name like '" + StringEscapeUtils.escapeSql(name) +"'",
                                                             //"artist like '" + artist +"'",
                                                             Track.class);

        if (res.size() == 0)
        {
            Track track = new Track();
            track.setName(name);
            //track.setArtist(Artist.getArtist(artist));
            return track;
        }
        if (res.size() == 1)
            return (Track)res.get(0);

        return null;
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
        af.updateObject(this);
    }

    /**
     * @return the listeners
     */
    public long getListeners() {
        return listeners;
    }

    /**
     * @param listeners the listeners to set
     */
    public void setListeners(long listeners) {
        this.listeners = listeners;
        af.updateObject(this);
    }

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
     * @return the published
     */
    public Date getPublished() {
        return published;
    }

    /**
     * @param published the published to set
     */
    public void setPublished(Date published) {
        this.published = published;
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
       // af.updateObject(this);
    }

    /**
     * @return the artist
     */
    public List getArtist() {
        return artist;
    }

    /**
     * @param artist the artist to set
     */
    public void setArtist(List artist) {
        this.artist = artist;
        af.updateObject(this);
    }

    /**
     * @return the tags
     */
    public List getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List tags) {
        this.tags = tags;
        af.updateObject(this);
    }




   
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Map;

/**
 *
 * @author robak
 */
public class Playlist {
/*
     <playlist>
        <id>2615079</id>
        <title>Duck playlist</title>
        <description>Duck and cover</description>
        <date>2008-05-22T09:40:09</date>
        <size>10</size>
        <duration>2143</duration>
        <streamable>0</streamable>
        <creator>http://www.last.fm/user/RJ</creator>
        <url>http://www.last.fm/user/RJ/library/playlists/1k1t3_duck_playlist</url>
        <image size="small">http://userserve-ak.last.fm/serve/34/5985590.jpg</image>
        <image size="medium">http://userserve-ak.last.fm/serve/64/5985590.jpg</image>
        <image size="large">http://userserve-ak.last.fm/serve/126/5985590.jpg</image>
      </playlist>
 */
 /*
  <playlist version="1">
  <title>Cher - Believe</title>
  <annotation>Cher - Believe</annotation>
  <creator>http://www.last.fm/music/Cher/Believe</creator>
  <date>2008-06-17T11:08:56</date>
  <trackList>
    <track>
      <title>Believe</title>
      <identifier>http://www.last.fm/music/Cher/_/Believe</identifier>
      <album>Believe</album>
      <creator>Cher</creator>
      <duration>240000</duration>
      <info>http://www.last.fm/music/Cher/_/Believe</info>
      <image>
        http://cdn.last.fm/coverart/130x130/2026126-648749258.jpg
      </image>
      <extension application="http://www.last.fm">
        <artistpage>http://www.last.fm/music/Cher</artistpage>
        <albumpage>http://www.last.fm/music/Cher/Believe</albumpage>
        <trackpage>http://www.last.fm/music/Cher/_/Believe</trackpage>
      </extension>
    </track>
    ...
  </trackList>
</playlist>
  */
    private Integer id;
    private String title;
 //   private String type="Playlist";
    private Date date;
    private User creator;
    private List tracklist;

        private AdditionalFunc af=AdditionalFunc.getInstance();


    public Playlist(){
        /*tracklist=new ArrayList();
        date=new Date();*/
        this.creator=null;
        if(!af.start){
            af.start=true;
            af.createObject(this);
            af.start=false;
        }else{
             System.out.println("playlist ");
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
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
        af.updateObject(this);
    }

    /**
     * @return the creator
     */
    public User getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(User creator) {
        this.creator = creator;
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
     * @return the tracklist
     */
    public List getTracklist() {
        return tracklist;
    }

    /**
     * @param tracklist the tracklist to set
     */
    public void setTracklist(List tracklist) {
        this.tracklist = tracklist;
        af.updateObject(this);
    }


}

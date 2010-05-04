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
public class Artist {
/*
 <artist>
  <name>Cher</name>
  <mbid>bfcc6d75-a6a5-4bc6-8282-47aec8531818</mbid>
  <url>http://www.last.fm/music/Cher</url>
  <image size="small">http://userserve-ak.last.fm/serve/50/285717.jpg</image>
  <image size="medium">http://userserve-ak.last.fm/serve/85/285717.jpg</image>
  <image size="large">http://userserve-ak.last.fm/serve/160/285717.jpg</image>
  <streamable>1</streamable>
  <stats>
    <listeners>196440</listeners>
    <plays>1599101</plays>
  </stats>
  <similar>
    <artist>
      <name>Madonna</name>
      <url>http://www.last.fm/music/Madonna</url>
      <image size="small">http://userserve-ak.last.fm/serve/50/5112299.jpg</image>
      <image size="medium">http://userserve-ak.last.fm/serve/85/5112299.jpg></image>
      <image size="large">http://userserve-ak.last.fm/serve/160/5112299.jpg</image>
    </artist>
    ...
  </similar>
  <tags>
    <tag>
      <name>pop</name>
      <url>http://www.last.fm/tag/pop</url>
    </tag>
    ...
  </tags>
  <bio>
    <published>Thu, 13 Mar 2008 03:59:18 +0000</published>
    <summary>...</summary>
    <content>...</content>
  </bio>
</artist>
 */
    private Integer id;
    private String name;
    private long listeners;
    private long plays;
    private Date published;

    private List similar;
    private List tags;


        private AdditionalFunc af=AdditionalFunc.getInstance();

    public Artist(){
       /* similar=new ArrayList();
        tags=new ArrayList();*/
        if(!af.start){
            af.start=true;
            af.createObject(this);
            af.start=false;
            
        }
    }

    public static Artist getArtist(String artist) {
        List<Object> res = AdditionalFunc.getObject("Artist", "name like '" + artist + "'", Artist.class);

        if (res.size() == 0) {
            Artist art = new Artist();
            art.setName(artist);
            return art;
        }

        if (res.size() == 1) {
            return (Artist) res.get(0);
        }

        return null;
    }
//    private String type="Artist";

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
     * @return the plays
     */
    public long getPlays() {
        return plays;
    }

    /**
     * @param plays the plays to set
     */
    public void setPlays(long plays) {
        this.plays = plays;
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
     * @return the similar
     */
    public List getSimilar() {
        return similar;
    }

    /**
     * @param similar the similar to set
     */
    public void setSimilar(List similar) {
        this.similar = similar;
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

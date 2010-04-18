/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

/**
 *
 * @author robak
 */
public class Tag {
/*
 *    <name>disco</name>
      <tagcount>48336</count>
      <url>www.last.fm/tag/disco</url>
 */

    private Integer id;
    private String name;
    private long tagcount;
    private String url;
//    private String type="Tag";

    private AdditionalFunc af=AdditionalFunc.getInstance();


    public Tag(){

        if(!af.start){
            af.start=true;
            af.createObject(this);
            af.start=false;
        }else{
             System.out.println("tags ");
        }
    }
    public Tag(String name){
        this.name=name;
        af.createObject(this);

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
     * @return the tagcount
     */
    public long getTagcount() {
        return tagcount;
    }

    /**
     * @param tagcount the tagcount to set
     */
    public void setTagcount(long tagcount) {
        this.tagcount = tagcount;
         af.updateObject(this);
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
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

    
}

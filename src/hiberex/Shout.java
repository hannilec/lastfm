/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

import java.util.Date;

/**
 *
 * @author robak
 */
public class Shout {

    /*
  <shout>
    <body>Blah</body>
    <author>joanofarctan</author>
    <date>Fri, 12 Dec 2008 13:20:41</date>
  </shout>
     * 
     */
    private Integer id;
    private String body;
    private Date date;
    private User author;


      private AdditionalFunc af=AdditionalFunc.getInstance();
   // private Integer user_id;

    public Shout(){
        
        if(!af.start){
            af.start=true;
            af.createObject(this);
            af.start=false;
        }else{
             System.out.println("shouts ");
        }
    }

    public Shout(String body,Date date,User author){
        this.body=body;
        this.date=date;
        this.author=author;

        af.createObject(this);
    }

    public void delete(){
        
        af.deleteObject(this);
    }
    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
        af.updateObject(this);
    }

    /**
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(User author) {
        this.author = author;
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

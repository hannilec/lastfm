/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

//import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
 
/**
 *
 * @author robak
 */
public class Group {
    private Integer id;
    private String name;
    private List members;

        private AdditionalFunc af=AdditionalFunc.getInstance();

    public Group(){
        //members=new ArrayList();
        if(!af.start){
            af.start=true;
            af.createObject(this);
            af.start=false;
            
        }
    }

    public Group(String name,List members){
        this.name=name;
        this.members=members;

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
     * @return the members
     */
    public List getMembers() {
        return members;
    }

    /**
     * @param members the members to set
     */
    public void setMembers(List members) {
        this.members = members;
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

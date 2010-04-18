/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hiberex;

/**
 *
 * @author robak
 */
public class Venue {
/*
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
 */
    private Integer id;
    private String name;
    private String city;
    private String country;
 //   private String type="Venue";


        private AdditionalFunc af=AdditionalFunc.getInstance();

        
    public Venue(){
        
        if(!af.start){
            af.start=true;
            af.createObject(this);
            af.start=false;
        }
    }

    public Venue(String name,String city,String country){
        this.name=name;
        this.city=city;
        this.country=country;
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
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
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

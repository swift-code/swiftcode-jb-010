package models;

/**
 * Created by lubuntu on 10/22/16.
 */
import com.avaje.ebean.Model;
import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Profile extends Model {
    public  Profile(String firstName, String lastName ){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String firstName;
    public String lastName;
    public String company;
    public static Model.Finder<Long, Profile> find = new Model.Finder<Long, Profile>(Profile.class);
}

package models;

import com.avaje.ebean.Model;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by lubuntu on 10/22/16.
 */
@Entity
public class User extends Model {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;
    public String emailId;
    public String password;
    @OneToOne
    public Profile profile;
    @ManyToMany
    @JoinTable(name = "user_connection",
            joinColumns = {
                    @JoinColumn(name= "user_id")
                    },
            inverseJoinColumns = {
                    @JoinColumn(name = "connection_id")
            }
    )
    public Set<User> connections;
    @OneToMany( mappedBy = "sender")
    public List<ConnectionRequest> connectionRequestsSent;
    @OneToMany( mappedBy = "reciever")
    public List<ConnectionRequest> connectionRequestsRecieved;
    public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(User.class);

    public User(String emailId, String password) {
        this.emailId = emailId;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
}

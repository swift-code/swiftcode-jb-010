package forms;

import com.sun.org.apache.xml.internal.utils.ListingErrorHandler;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lubuntu on 10/22/16.
 */
public class LoginForm {
    @Constraints.Required
    public String emailId;
    @Constraints.Required
    public String password;
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        User user = User.find.where().eq("email_id", emailId).findUnique();
    if (user == null)
    {
        errors.add(new ValidationError("message", "Incorrect email or passsword"));
    }
        else if(!BCrypt.checkpw(password, user.password)){
        errors.add(new ValidationError("message","Incorrect email or password"));
    }
        return errors;

    }

    public String getEmail() {
        return emailId;
    }

    public void setEmail(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

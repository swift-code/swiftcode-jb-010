package forms;

import models.User;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lubuntu on 10/22/16.
 */
public class SignupForm{
    @Constraints.Required
    public String emailId;
    public String password;
    @Constraints.Required
    public String firstName;
    @Constraints.Required
    public String lastName;
    public List<ValidationError> validat(){
        List<ValidationError> errors = new ArrayList<>();
        User user = User.find.where().eq("email_id", emailId).findUnique();
        if(user != null) {
            errors.add(new ValidationError("message", "this email already exosts"));
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

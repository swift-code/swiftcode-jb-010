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
    public String email;
    @Constraints.Required
    public String password;
    @Constraints.Required
    public String fristName;
    @Constraints.Required
    public String lastName;
    public List<ValidationError> validat(){
        List<ValidationError> errors = new ArrayList<>();
        User user = User.find.where().eq("email", email).findUnique();
        if(user != null) {
            errors.add(new ValidationError("message", "this email already exosts"));
        }
        return errors;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFristName() {
        return fristName;
    }

    public void setFristName(String fristName) {
        this.fristName = fristName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

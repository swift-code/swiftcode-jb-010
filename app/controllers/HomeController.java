package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ConnectionRequest;
import models.Profile;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * Created by lubuntu on 10/23/16.
 */
public class HomeController extends Controller {
    @Inject
    ObjectMapper objectMapper;
    public Result getProfile(Long id){
        User user = User.find.byId(id);
        Profile profile = user.profile.find.byId(id);
        ObjectNode userJson = objectMapper.createObjectNode();
        userJson.put("firstName", profile.firstName);
        userJson.put("lastName", profile.lastName);
        userJson.put("email", user.emailId);
        userJson.put("company", profile.company);
        userJson.set(
                "connections",
                objectMapper.valueToTree(
                        user.connections.stream().map(
                                connection ->{
                                    User connectionUser = User.find.byId(id);
                                    Profile connectionProfile = connectionUser.profile.find.byId(id);
                                    ObjectNode connectionJson = objectMapper.createObjectNode();
                                    connectionJson.put("firstName", connectionProfile.firstName);
                                    connectionJson.put("lastName", connectionProfile.lastName);
                                    connectionJson.put("email", connectionUser.emailId);
                                    connectionJson.put("company", connectionProfile.company);
                                    return connectionJson;
                                }
                        ).collect(Collectors.toList())
                )
        );

    } ));


    }
}

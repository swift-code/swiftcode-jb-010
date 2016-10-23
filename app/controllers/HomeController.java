package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ConnectionRequest;
import models.Profile;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.stream.Collector;
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
        userJson.put("emailId", user.emailId);
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
                                    connectionJson.put("emailId", connectionUser.emailId);
                                    connectionJson.put("company", connectionProfile.company);
                                    return connectionJson;
                                }
                        ).collect(Collectors.toList())
                )
        );
        userJson.set(
                "connectionRequest",
                objectMapper.valueToTree(
                        user.connectionRequestsRecieved.stream().filter(
                                connectionRequest -> connectionRequest.status.equals(
                                        ConnectionRequest.Status.WAITING)
                        ).map(
                                connectionRequest ->{
                                    Profile senderProfile = Profile.find.byId(connectionRequest.sender.profile.id);
                                    ObjectNode connectionRequestJson = objectMapper.createObjectNode();
                                    connectionRequestJson.put("firstName", senderProfile.firstName);
                                    connectionRequestJson.put("lastName", senderProfile.lastName);
                                    //connectionRequest.put("email", senderRequest.emailId);
                                    //connectionRequest.put("company", senderProfile.company);
                                    return connectionRequest;
                                }
                        ).collect(Collectors.toList())

                )
        );

        User.find.all().stream()
                .filter(x -> !user.equals(x))
                .filter(x -> !user.connections.contains(x))
                .filter(x -> !x.connectionRequestsRecieved.stream().map( y -> y.sender ).collect(Collectors.toList()).contains(x))
                .filter(x -> !x.connectionRequestsSent.stream().map( y -> y.reciever ).collect(Collectors.toList()).contains(x))
        .map(x ->{
                    Profile suggestionProfile = Profile.find.byId(x.profile.id);
                    ObjectNode suggestionJson = objectMapper.createObjectNode();
                    suggestionJson.put("id", x.id);
                    suggestionJson.put("firstName", suggestionProfile.firstName);
                    //connectionRequest.put("emailId", senderRequest.emailId);
                    //connectionRequest.put("company", senderProfile.company);
                    return suggestionJson;
                }).collect(Collectors.toList());





        return ok(userJson);

    }



}


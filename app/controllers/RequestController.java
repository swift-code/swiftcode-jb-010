package controllers;

import models.ConnectionRequest;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.util.Locale;
import java.util.LongSummaryStatistics;

/**
 * Created by lubuntu on 10/23/16.
 */
public class RequestController extends Controller {

    public Result sendRequest(Long sId, Long rId) {
        if (sId == null || rId == null || User.find.byId(sId) == null || User.find.byId(rId) == null)
            return ok("Invalid userId or SenderId");
        else {

            ConnectionRequest c = new ConnectionRequest();
            c.sender.id = sId;
            c.reciever.id = rId;
            c.status = ConnectionRequest.Status.WAITING;
            ConnectionRequest.db().save(c);
            return ok();

        }
    }
    public Result acceptRequest(Long rId){
        ConnectionRequest c = ConnectionRequest.find.byId(rId);
        c.status = ConnectionRequest.Status.ACCEPTED;
        ConnectionRequest.db().save(c);
        c.sender.connections.add(c.reciever);
        c.reciever.connections.add(c.sender);
        User.db().save(c.sender);
        User.db().save(c.reciever);
        return ok();
    }

}



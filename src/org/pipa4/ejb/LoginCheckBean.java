package org.pipa4.ejb;

import org.pipa4.hibernate.Hasher;
import org.pipa4.hibernate.ResultDao;
import org.pipa4.models.BoolJson;
import org.pipa4.models.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Stateless
@LocalBean
@Path("/rest")
@ApplicationPath("/pip")
public class LoginCheckBean extends Application {
    public LoginCheckBean() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public Set<Class<?>> getClasses() {
        return null;
    }

    @GET
    @Path("/hello")
    @Produces("text/plain")
    public String sayHello() {
        return "Hello";
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        BoolJson boolJson = new BoolJson();
        User foundUser = (User)(new ResultDao().findById(User.class, user.getName()));
        if ( foundUser != null && foundUser.getPassword().equals(Hasher.md5Custom(user.getPassword()))){
            boolJson.setResult(true);
        } else {
            boolJson.setResult(false);
        }
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Content-Type", "application/json")
                .entity(boolJson.getResult())
                .build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        BoolJson boolJson = new BoolJson();
        ResultDao resultDao = new ResultDao();
        if (resultDao.findById(User.class, user.getName()) == null) {
            user.setPassword(Hasher.md5Custom(user.getPassword()));
            resultDao.saveUser(user);
            boolJson.setResult(true);
        } else {
            boolJson.setResult(false);
        }
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .entity(boolJson.getResult())
                .build();
    }
}

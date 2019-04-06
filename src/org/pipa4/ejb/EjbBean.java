package org.pipa4.ejb;

import org.pipa4.hibernate.Hasher;
import org.pipa4.hibernate.ResultDao;
import org.pipa4.hibernate.UserDao;
import org.pipa4.models.Result;
import org.pipa4.models.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Stateless
@LocalBean
@Path("/rest")
@ApplicationPath("/pip")
public class EjbBean extends Application {
    private ResultDao resultDao;
    private UserDao userDao;

    public EjbBean() {
        resultDao = new ResultDao();
        userDao = new UserDao();
    }

    @Override
    public Set<Class<?>> getClasses() {
        return null;
    }

    @GET
    @Path("/hello")
    @Consumes(MediaType.APPLICATION_JSON)
    public String hello(User user) {

        return "hello";
    }

    @GET
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    public String check(User user) {
        User newUser = new User();
        newUser.setName("privet");
        newUser.setPassword("zdarova");
        newUser.setAuthKey("kaka");

        try {
            userDao.saveUser(newUser);
        } catch (Exception e){
            return e.getMessage();
        }
        return "check";
    }

    @POST
    @Path("/points")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPoints(Result result) {
        JsonObjectBuilder jsonObjectBuilder;
        JsonArrayBuilder jsonArrayBuilder;

        jsonArrayBuilder = Json.createArrayBuilder();

        try {
            String userName = result.getFok();
            String auth = result.getAuth();

            if (checkAuth(userName, auth)) {
                List<Result> points = resultDao.findAllByName(userName);

                for (Result point : points) {
                    jsonObjectBuilder = Json.createObjectBuilder();
                    jsonObjectBuilder.add("x", point.getX());
                    jsonObjectBuilder.add("y", point.getY());
                    jsonObjectBuilder.add("r", point.getR());
                    jsonObjectBuilder.add("result", point.isResult());
                    jsonArrayBuilder.add(jsonObjectBuilder.build());
                }
            }
        } catch (Exception e){
            jsonObjectBuilder = Json.createObjectBuilder();
            jsonObjectBuilder.add("auth", "failed");
            jsonArrayBuilder.add(jsonObjectBuilder.build());
        }

        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Content-Type", "application/json")
                .entity(jsonArrayBuilder.build())
                .build();
    }

    @POST
    @Path("/set")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setPoint(Result result) {

        try {
            result.setResult(isInArea(result.getX(), result.getY(), result.getR()));

            if (checkAuth(result.getFok(), result.getAuth())) {
                resultDao.setPoint(result);
            }
        } catch (Exception e){ /**/}

        return getPoints(result);
    }

    private boolean isInArea(double x, double y, double r) {
        if((x >= 0 && y >= 0) && (x <= r) && (y <= (r))){
            return true;
        }
        else if(x <= 0 && y >= 0 && y <= (x+(r/2))*2 && x >= (-r/2) && y <= r){
            return true;
        }
        else return x >= 0 && y <= 0 && r >= Math.sqrt(y * y + x * x);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        try {
            User foundUser = (userDao.findById(user.getName()));
            if (foundUser != null && foundUser.getPassword().equals(Hasher.md5Custom(user.getPassword()))) {
                String authCode = String.valueOf(new Date().getTime());
                if (foundUser.getAuthKey() != null && foundUser.getAuthKey().contains("@") && new Date().getTime() - Long.parseLong(foundUser.getAuthKey().split("@")[0]) < TimeUnit.MINUTES.toMillis(10)) {
                    authCode += "@" + foundUser.getAuthKey().split("@")[1];
                } else {
                    authCode += "@" + UUID.randomUUID().toString();
                }
                user.setAuthKey(authCode);
                user.setPassword(foundUser.getPassword());
                userDao.updateUser(user);

                jsonObjectBuilder.add("access", "accept");
                jsonObjectBuilder.add("authCode", authCode);
            } else {
                jsonObjectBuilder.add("access", "denied");
            }
        } catch (Exception e){
            jsonObjectBuilder.add("error", e.getMessage());
            jsonObjectBuilder.add("auth", "failed");
        }

        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Content-Type", "application/json")
                .entity(jsonObjectBuilder.build())
                .build();
    }

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response auth(User user) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        try {
            User userToCompare = (userDao.findById(user.getName()));
            if (!userToCompare.getAuthKey().split("@")[1].equals(user.getAuthKey().split("@")[1])) {
                jsonObjectBuilder.add("access", "denied");
                jsonObjectBuilder.add("reason", "wrong auth key");
            } else if (new Date().getTime() - Long.parseLong(userToCompare.getAuthKey().split("@")[0]) > TimeUnit.MINUTES.toMillis(10)) {
                jsonObjectBuilder.add("access", "denied");
                jsonObjectBuilder.add("reason", "old auth key");
            } else {
                jsonObjectBuilder.add("access", "accept");

                userDao.updateUser(userToCompare);
            }
        } catch (Exception e){
            jsonObjectBuilder.add("auth", "failed");
        }

        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Content-Type", "application/json")
                .entity(jsonObjectBuilder.build())
                .build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        try {
            if (userDao.findById(user.getName()) == null) {
                user.setPassword(Hasher.md5Custom(user.getPassword()));
                userDao.saveUser(user);
            }
        } catch (Exception e){ /**/}

        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Content-Type", "application/json")
                .build();
    }


    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(User user) {
        try {
            if (checkAuth(user.getName(), user.getAuthKey())) {
                String authCode = 0 + "@" + UUID.randomUUID().toString();
                User userToUpdate = userDao.findById(user.getName());
                userToUpdate.setAuthKey(authCode);

                userDao.updateUser(userToUpdate);
            }
        } catch (Exception e) {/**/}

        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:3000")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Content-Type", "application/json")
                .build();
    }

    private boolean checkAuth(String name, String auth){
        try {
            User user = new User();
            user.setName(name);
            user.setAuthKey(auth);
            User userToCompare = (new UserDao().findById(user.getName()));
            if (!userToCompare.getAuthKey().split("@")[1].equals(user.getAuthKey().split("@")[1])) {
                return false;
            } else if (new Date().getTime() - Long.parseLong(userToCompare.getAuthKey().split("@")[0]) > TimeUnit.MINUTES.toMillis(10)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}

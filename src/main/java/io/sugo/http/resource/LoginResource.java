package io.sugo.http.resource;


import io.sugo.http.model.UserBean;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource extends Resource{

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserBean user) {
        String usernameSave = configure.getProperty("system.properties","username");
        String passwordSave = configure.getProperty("system.properties","password");

        String username = user.getUsername();
        String password = user.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            LOG.info("log fail");
            return Response.status(530).build();
        }

        if(username.equals(usernameSave) && password.equals(passwordSave)) {
            LOG.info("log success");
            return Response.ok().build();
        }

        LOG.info("log fail");
        return Response.status(530).build();
    }
}

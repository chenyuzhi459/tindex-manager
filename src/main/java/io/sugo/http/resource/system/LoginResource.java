package io.sugo.http.resource.system;


import com.sun.org.apache.regexp.internal.RE;
import io.sugo.http.model.UserBean;
import io.sugo.http.resource.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource extends Resource {
    private static final Logger LOG = Logger.getLogger(LoginResource.class);


    @GET
    @Path("/test")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response test(){
        return Response.ok("ok").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserBean user) {
        String usernameSave = configure.getProperty("system.properties","username");
        String passwordSave = configure.getProperty("system.properties","password");

        String username = user.getUsername();
        String password = user.getPassword();
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            LOG.info("login fail");
            return Response.status(530).build();
        }

        if(username.equals(usernameSave) && password.equals(passwordSave)) {
            LOG.info("login success");
            return Response.ok().build();
        }

        LOG.info("login fail");
        return Response.status(530).build();
    }
}

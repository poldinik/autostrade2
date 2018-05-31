package com.cyandrone.api;

import com.cyandrone.api.models.WebhookMessage;
import com.cyandrone.api.services.EmailService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/emails")
public class EmailResource {


    //manda email se il webhook rileva un evento con id messaggio postato
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postEmail(WebhookMessage m){
        return EmailService.sendEmail(m);
    }
}

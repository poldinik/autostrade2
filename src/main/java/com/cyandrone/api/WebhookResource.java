package com.cyandrone.api;

import com.cyandrone.api.models.WebhookMessage;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/webhooks")
public class WebhookResource {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response webhook(WebhookMessage m) throws InterruptedException {
        changeFlagStatus(m);
        return Response.ok().build();
    }


    private void changeFlagStatus(WebhookMessage m) throws InterruptedException{
        synchronized (this){
            Flag.getInstance().setMessageArrived(true);
            Flag.getInstance().setMessageArrivedId(m.getData().getId());
            System.out.println("Esecuzione Endpoint per notifica nuovo messaggio da parte di Cisco Spark");
            System.out.println("Il flag di notifica nuovo messaggio è: "+Flag.getInstance().getMessageArrived().toString());
            notifyAll();
        }
    }
}

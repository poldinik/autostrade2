package com.cyandrone.api;

import ciscospark.api.Message;
import com.cyandrone.api.models.WebhookMessage;
import com.cyandrone.api.services.EmailService;
import com.cyandrone.api.services.MessageService;


import javax.ejb.Stateful;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

@Path("messages")
public class MessageResource {

    /*
    * Message APIs
    * CRUD Operations
    * */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMessage(@HeaderParam("roomId") String roomId, Message message){

        return MessageService.createMessage(roomId, message);

    }

    @Path("/{messageId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage(@PathParam("messageId") String messageId){

        return MessageService.getMessage(messageId);

    }

    //aysnc resource

    @POST
    @Path("/async")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void asyncGetWithTimeout(@Suspended final AsyncResponse asyncResponse, @HeaderParam("roomId") String roomId, Message message) {
        asyncResponse.setTimeoutHandler(new TimeoutHandler() {

            @Override
            public void handleTimeout(AsyncResponse asyncResponse) {
                asyncResponse.resume(Response.status(Response.Status.SERVICE_UNAVAILABLE)
                        .entity("Operation time out.").build());
            }
        });
        asyncResponse.setTimeout(50, TimeUnit.SECONDS);

        new Thread(new Runnable() {

            @Override
            public void run() {

                Response result = null;
                try {
                    result = veryExpensiveOperation(roomId, message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Flag.getInstance().setMessageArrivedId(null);
                Flag.getInstance().setMessageArrived(false);
                System.out.println("restituisco la risposta");
                asyncResponse.resume(result);
            }


        }).start();
    }

    private Response veryExpensiveOperation(String roomId, Message message) throws InterruptedException {

        System.out.println("Esecuzione metodo ti attesa del feedback da Cisco Spark");
        Response newMessage = MessageService.createMessage(roomId, message);



        System.out.println("Il flag di notifica nuovo messaggio è: "+Flag.getInstance().getMessageArrived().toString());
        synchronized(this){
            while(!Flag.getInstance().getMessageArrived()){
                System.out.println("Inside sync while");
                System.out.println(Flag.getInstance().getMessageArrived().toString());
                wait();
            }
            System.out.println("Esco dal ciclo di attesa");
        }

        System.out.println("Prendo il nuovo messaggio del bot");
        return MessageService.getMessage(Flag.getInstance().getMessageArrivedId());
    }





    //FIXME: non funziona il meccanismo perché spark quando chiama l'endpoint non riceve lo stesso oggetto che ha ricevuto la app che chiama il metodo async




}

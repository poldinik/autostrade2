package com.cyandrone.api.services;

import ciscospark.api.*;
import com.cyandrone.api.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

public class RoomService {

    public static Response createRoom(){

        Spark spark = SparkBuilder.getInstance().getSpark();

        Room room = new Room();
        room.setTitle("New Room");
        room.setType("direct");


        Membership membership = new Membership();
        membership.setPersonEmail(Configuration.mailBot);

        try{

            room = spark.rooms().post(room);
            membership.setRoomId(room.getId());
            spark.memberships().post(membership);

            Webhook webhook=new Webhook();
            webhook.setName("WebHook ChatBot");
            webhook.setResource("messages");
            webhook.setEvent("created");

            //limita l'attivazione del webhook alla room corrente e alle risposte del bot
            webhook.setFilter("roomId="+room.getId()+"&"+"personEmail="+Configuration.mailBot);
            webhook.setTargetUrl(URI.create(Configuration.domain+Configuration.mockEndPoint));
            webhook=spark.webhooks().post(webhook);

            //TODO: serve fare una api per eliminare un webhook?

            return Response.ok(room, MediaType.APPLICATION_JSON).build();

        }catch (SparkException e){

            return Response.status(500).build();

        }
    }

    public static Response deleteRoom(String roomId){

        Spark spark = SparkBuilder.getInstance().getSpark();

        try{

            spark.rooms().path("/"+roomId).delete();
            return Response.ok().build();

        }catch (SparkException e){

            return Response.status(500).build();

        }
    }
}

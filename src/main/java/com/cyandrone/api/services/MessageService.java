package com.cyandrone.api.services;

import ciscospark.api.Message;
import ciscospark.api.Spark;
import ciscospark.api.SparkBuilder;
import ciscospark.api.SparkException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MessageService {

    public static Response createMessage(String roomId, Message message){

        Spark spark = SparkBuilder.getInstance().getSpark();

        try{

            message.setRoomId(roomId);
            //message.setText("Benvenuto");
            String textMessage = message.getText();
            message.setMarkdown("<@personEmail:highwaybot@sparkbot.io|HighwayBot> " + textMessage); //così riesco a nominarlo
            Message m = spark.messages().post(message);
            return Response.ok(m, MediaType.APPLICATION_JSON).build();

        }catch (SparkException e){

            return Response.status(500).build();

        }
    }

    public static Response getMessage(String messageId){

        Spark spark = SparkBuilder.getInstance().getSpark();

        try{

            Message mb = spark.messages().path("/"+messageId).get();
            System.out.println(mb.getText());
            return Response.ok(mb, MediaType.APPLICATION_JSON).build();

        }catch (SparkException e){

            return Response.status(500).build();

        }
    }
}

package com.cyandrone.api;

import com.cyandrone.api.services.RoomService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/rooms")
public class RoomResource {

    /*
     * Room APIs
     * CRUD Operations
     * */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRoom(){
        return RoomService.createRoom();
    }

    @Path("/{roomId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("roomId") String roomId){
        return RoomService.deleteRoom(roomId);
    }

}

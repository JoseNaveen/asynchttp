package com.joey.res;



import javax.inject.Inject;
import com.joey.testrestasync.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.Executor;

@Path("/msg")
public class MyMessage {

	@Inject
    private App app;

    @GET
    public void asyncGet(@Suspended final AsyncResponse asyncResponse) {
    		app.enqueueResponse(asyncResponse);
    		System.out.println(Thread.currentThread().getId() + " received the request. Enqueuing..");
    }
    
   
}
package io.gustavoemf.rest;

import io.quarkus.logging.Log;
import io.smallrye.common.annotation.NonBlocking;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/quiz")
public class QuizResource {

    @GET
    @Path("/ping")
    @Produces(TEXT_PLAIN)
    @NonBlocking
    public String ping() {
        Log.debug("Ping Quiz Resource");
        return "pong";
    }
}

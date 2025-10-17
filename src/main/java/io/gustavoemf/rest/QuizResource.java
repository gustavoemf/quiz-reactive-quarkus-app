package io.gustavoemf.rest;

import io.gustavoemf.domain.Quiz;
import io.gustavoemf.service.QuizService;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/quizzes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class QuizResource {

    private final QuizService quizService;

    public QuizResource(QuizService quizService) {
        this.quizService = quizService;
    }

    @GET
    public Uni<List<Quiz>> getAllQuizzes() {
        return quizService.findAllQuizzes()
                .replaceIfNullWith(List::of)
                .invoke(quizzes -> Log.debugf("Total number of quizzes found: %d", quizzes.size()));
    }

    @GET
    @Path("/{id}")
    public Uni<Response> getQuizz(@PathParam("id") Long id) {
        return quizService.findQuizById(id)
                .onItem().ifNotNull().transform(quiz -> {
                    Log.debugf("Found quiz: %s", quiz);
                    return Response.ok(quiz).build();
                })
                .replaceIfNullWith(() -> {
                    Log.debugf("No quiz found with id %d", id);
                    return Response.status(Response.Status.NOT_FOUND).build();
                });
    }

    @GET
    @Path("/ping")
    @Produces(TEXT_PLAIN)
    @NonBlocking
    public String ping() {
        Log.debug("Ping Quiz Resource");
        return "pong";
    }
}

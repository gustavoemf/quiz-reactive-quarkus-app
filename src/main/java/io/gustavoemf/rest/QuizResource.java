package io.gustavoemf.rest;

import io.gustavoemf.domain.Quiz;
import io.gustavoemf.service.QuizService;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

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
    @Path("/ping")
    @Produces(TEXT_PLAIN)
    @NonBlocking
    public String ping() {
        Log.debug("Ping Quiz Resource");
        return "pong";
    }
}

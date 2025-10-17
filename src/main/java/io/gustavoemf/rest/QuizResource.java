package io.gustavoemf.rest;

import io.gustavoemf.domain.Quiz;
import io.gustavoemf.service.QuizService;
import io.quarkus.logging.Log;
import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

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
    public Uni<Response> getQuiz(@PathParam("id") Long id) {
        return quizService.findQuizById(id)
                .onItem().ifNotNull().transform(quiz -> {
                    Log.debugf("Found quiz: %s", quiz);
                    return Response.ok(quiz).build();
                })
                .onItem().ifNull().continueWith(() -> {
                    Log.debugf("No quiz found with id %d", id);
                    return Response.status(Response.Status.NOT_FOUND).build();
                });
    }

    @POST
    public Uni<Response> createQuiz(@NotNull @Valid Quiz quiz, @Context UriInfo uriInfo) {
        return this.quizService.persistQuiz(quiz)
                .map(q -> {
                    var uri = uriInfo.getAbsolutePathBuilder().path(Long.toString(q.id)).build();
                    Log.debugf("New Quiz created with URI %s", uri.toString());
                    return Response.created(uri).build();
                });
    }

    @PUT
    @Path("/{id}")
    public Uni<Response> updateQuiz(@PathParam("id") Long id, @NotNull @Valid Quiz quiz) {
        if (quiz.id == null) {
            quiz.id = id;
        }

        return this.quizService.replaceQuiz(quiz)
                .onItem().ifNotNull().transform(q -> {
                    Log.debugf("Quiz replaced with new values %s", q);
                    return Response.noContent().build();
                })
                .replaceIfNullWith(() -> {
                    Log.debugf("No quiz found with id %d", quiz.id);
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

package io.gustavoemf.service;

import io.gustavoemf.domain.Quiz;
import io.gustavoemf.repository.QuizRepository;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class QuizService {

    public final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Uni<List<Quiz>> findAllQuizzes() {
        Log.debug("Getting all quizzes");
        return quizRepository.listAll();
    }

    public Uni<Quiz> findQuizById(Long id) {
        Log.debugf("Finding quiz by id = %d", id);
        return quizRepository.findById(id);
    }
}

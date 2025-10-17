package io.gustavoemf.service;

import io.gustavoemf.domain.Quiz;
import io.gustavoemf.mapping.QuizFullUpdateMapper;
import io.gustavoemf.repository.QuizRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class QuizService {

    public final QuizRepository quizRepository;
    public final QuizFullUpdateMapper quizFullUpdateMapper;

    public QuizService(QuizRepository quizRepository, QuizFullUpdateMapper quizFullUpdateMapper) {
        this.quizRepository = quizRepository;
        this.quizFullUpdateMapper = quizFullUpdateMapper;
    }

    public Uni<List<Quiz>> findAllQuizzes() {
        Log.debug("Getting all quizzes");
        return quizRepository.listAll();
    }

    public Uni<Quiz> findQuizById(Long id) {
        Log.debugf("Finding quiz by id = %d", id);
        return quizRepository.findById(id);
    }

    @WithTransaction
    public Uni<Quiz> persistQuiz(@NotNull @Valid Quiz quiz) {
        Log.debugf("Persisting quiz: %s", quiz);
        return this.quizRepository.persist(quiz);
    }

    @WithTransaction
    public Uni<Object> replaceQuiz(@NotNull @Valid Quiz quiz) {
        Log.debugf("Replacing quiz: %s", quiz);
        return quizRepository.findById(quiz.id)
                .onItem().ifNotNull().transform(q -> {
                    this.quizFullUpdateMapper.mapFullUpdate(quiz, q);
                    return q;
                });
    }
}

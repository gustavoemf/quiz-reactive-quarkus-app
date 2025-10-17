package io.gustavoemf.service;

import io.gustavoemf.domain.Quiz;
import io.gustavoemf.mapping.QuizFullUpdateMapper;
import io.gustavoemf.mapping.QuizPartialUpdateMapper;
import io.gustavoemf.repository.QuizRepository;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@ApplicationScoped
public class QuizService {

    public final QuizRepository quizRepository;
    public final QuizFullUpdateMapper quizFullUpdateMapper;
    public final QuizPartialUpdateMapper quizPartialUpdateMapper;
    private final Validator validator;

    public QuizService(QuizRepository quizRepository,
                       QuizFullUpdateMapper quizFullUpdateMapper,
                       QuizPartialUpdateMapper quizPartialUpdateMapper,
                       Validator validator) {
        this.quizRepository = quizRepository;
        this.quizFullUpdateMapper = quizFullUpdateMapper;
        this.quizPartialUpdateMapper = quizPartialUpdateMapper;
        this.validator = validator;
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
    public Uni<Quiz> replaceQuiz(@NotNull @Valid Quiz quiz) {

        Log.debugf("Replacing quiz: %s", quiz);
        return quizRepository.findById(quiz.id)
                .onItem().ifNotNull().transform(q -> {
                    this.quizFullUpdateMapper.mapFullUpdate(quiz, q);
                    return q;
                });
    }

    @WithTransaction
    public Uni<Quiz> partialUpdateQuiz(@NotNull Quiz quiz) {

        Log.infof("Partially updating quiz: %s", quiz);
        return quizRepository.findById(quiz.id)
                .onItem().ifNotNull().transform(q -> {
                    this.quizPartialUpdateMapper.mapPartialUpdate(quiz, q);
                    return q;
                })
                .onItem().ifNotNull().transform(this::validatePartialUpdate);
    }

    /**
     * Validates a {@link Quiz} for partial update according to annotation validation rules on the {@link Quiz} object.
     *
     * @param quiz The {@link Quiz}
     * @return The same {@link Quiz} that was passed in, assuming it passes validation. The return is used as a convenience so the method can be called in a functional pipeline.
     * @throws ConstraintViolationException If validation fails
     */
    private Quiz validatePartialUpdate(Quiz quiz) {

        var violations = this.validator.validate(quiz);

        if ((violations != null) && !violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        return quiz;
    }
}

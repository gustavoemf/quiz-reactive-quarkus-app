package io.gustavoemf.repository;

import io.gustavoemf.domain.Quiz;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QuizRepository implements PanacheRepository<Quiz> {
}

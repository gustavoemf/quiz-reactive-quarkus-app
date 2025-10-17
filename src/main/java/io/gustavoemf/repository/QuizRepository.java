package io.gustavoemf.repository;

import io.gustavoemf.domain.Quiz;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@WithSession
public class QuizRepository implements PanacheRepository<Quiz> {
}

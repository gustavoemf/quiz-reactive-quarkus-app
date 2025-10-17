package io.gustavoemf.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "quizzes", schema = "quiz_app")
public class Quiz extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    public String title;

    @Size(max = 500)
    public String description;

    public List<String> questions;
}

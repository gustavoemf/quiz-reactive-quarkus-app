package io.gustavoemf.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "questions", schema = "quiz_app")
public class Question extends BaseEntity {

    @NotBlank
    @Column(nullable = false)
    public String questionText;

    @ElementCollection
    @Column(name = "option_text")
    public List<String> options;

    public int correctOptionIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    public Quiz quiz;
}

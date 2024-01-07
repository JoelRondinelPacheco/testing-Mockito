package org.joel.mockito_app.examples.services;

import org.joel.mockito_app.examples.models.Exam;

import java.util.Optional;

public interface ExamService {
    Optional<Exam> findExamByName(String name);
    Exam findExamByNameWithQuestions(String name);
    Exam save(Exam exam);
}

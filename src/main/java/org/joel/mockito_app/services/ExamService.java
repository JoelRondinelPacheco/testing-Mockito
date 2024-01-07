package org.joel.mockito_app.services;

import org.joel.mockito_app.models.Exam;

import java.util.Optional;

public interface ExamService {
    Optional<Exam> findExamByName(String name);
}

package org.joel.mockito_app.repositories;

import org.joel.mockito_app.models.Exam;

import java.util.List;

public interface ExamRepository {
    List<Exam> findAll();
}

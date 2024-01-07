package org.joel.mockito_app.repositories;

import org.joel.mockito_app.models.Exam;

import java.util.List;

public interface ExamRepository {
    Exam save(Exam exam);
    List<Exam> findAll();

}

package org.joel.mockito_app.examples.repositories;

import org.joel.mockito_app.examples.models.Exam;

import java.util.List;

public interface ExamRepository {
    Exam save(Exam exam);
    List<Exam> findAll();

}

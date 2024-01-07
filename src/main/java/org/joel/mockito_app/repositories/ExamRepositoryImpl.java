package org.joel.mockito_app.repositories;

import org.joel.mockito_app.models.Exam;

import java.util.Arrays;
import java.util.List;

public class ExamRepositoryImpl implements ExamRepository{
    @Override
    public List<Exam> findAll() {
        return Arrays.asList(new Exam(5L, "Matematicas"), new Exam(6L, "Lenguaje"), new Exam(7L, "Historia"));
    }
}

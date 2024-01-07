package org.joel.mockito_app.services;

import org.joel.mockito_app.models.Exam;
import org.joel.mockito_app.repositories.ExamRepository;

import java.util.Optional;

public class ExamServiceImpl implements ExamService{

    private ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Optional<Exam> findExamByName(String name) {
        return examRepository.findAll()
                .stream()
                .filter(e -> e.getName().contains(name) || e.getName().equals(name))
                .findFirst();
    }
}

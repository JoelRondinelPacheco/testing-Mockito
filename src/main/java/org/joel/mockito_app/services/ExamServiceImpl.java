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
    public Exam findExamByName(String name) {
        Optional<Exam> examOptional = examRepository.findAll()
                .stream()
                .filter(e -> e.getName().contains(name) || e.getName().equals(name))
                .findFirst();
        Exam exam = null;
        if (examOptional.isPresent()) {
            exam = examOptional.orElseThrow();
        }
        return exam;
    }
}

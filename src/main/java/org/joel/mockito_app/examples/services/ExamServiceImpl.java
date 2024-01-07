package org.joel.mockito_app.examples.services;

import org.joel.mockito_app.examples.models.Exam;
import org.joel.mockito_app.examples.repositories.ExamRepository;
import org.joel.mockito_app.examples.repositories.QuestionRepository;

import java.util.List;
import java.util.Optional;

public class ExamServiceImpl implements ExamService{

    private ExamRepository examRepository;
    private QuestionRepository questionRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }
    public ExamServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public Optional<Exam> findExamByName(String name) {
        return examRepository.findAll()
                .stream()
                .filter(e -> e.getName().contains(name) || e.getName().equals(name))
                .findFirst();
    }

    @Override
    public Exam findExamByNameWithQuestions(String name) {
        Optional<Exam> examOptional = this.findExamByName(name);
        Exam exam = null;
        if (examOptional.isPresent()) {
            exam = examOptional.get();
            List<String> questions = questionRepository.findQuestionByExamId(exam.getId());
            exam.setQuestions(questions);
        }
        return exam;
    }

    @Override
    public Exam save(Exam exam) {
        if (!exam.getQuestions().isEmpty()) {
            questionRepository.saveQuestions(exam.getQuestions());
        }
        return examRepository.save(exam);
    }
}

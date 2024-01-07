package org.joel.mockito_app.examples.repositories;

import org.joel.mockito_app.examples.Data;

import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository{
    @Override
    public List<String> findQuestionByExamId(Long examId) {
        System.out.println("QuestionRepositoryImpl.findQuestionByExamId");
        return Data.QUESTIONS;
    }

    @Override
    public void saveQuestions(List<String> questions) {
        System.out.println("QuestionRepositoryImpl.saveQuestions");
    }
}

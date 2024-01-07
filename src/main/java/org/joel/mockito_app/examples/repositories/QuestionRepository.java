package org.joel.mockito_app.examples.repositories;

import java.util.List;

public interface QuestionRepository {
    List<String> findQuestionByExamId(Long examId);
    void saveQuestions(List<String> questions);
}

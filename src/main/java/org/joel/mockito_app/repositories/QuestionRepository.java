package org.joel.mockito_app.repositories;

import java.util.List;

public interface QuestionRepository {
    List<String> findQuestionByExamId(Long examId);
}

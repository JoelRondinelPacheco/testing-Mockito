package org.joel.mockito_app.services;

import org.joel.mockito_app.models.Exam;

public interface ExamService {
    Exam findExamByName(String name);
}

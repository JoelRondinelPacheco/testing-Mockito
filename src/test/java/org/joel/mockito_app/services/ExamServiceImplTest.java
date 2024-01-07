package org.joel.mockito_app.services;

import org.joel.mockito_app.models.Exam;
import org.joel.mockito_app.repositories.ExamRepository;
import org.joel.mockito_app.repositories.ExamRepositoryImpl2;
import org.joel.mockito_app.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ExamServiceImplTest {

    ExamRepository repository;
    ExamService service;
    QuestionRepository questionRepository;
    @BeforeEach
    void setUp() {
        repository = mock(ExamRepository.class);
        questionRepository = mock(QuestionRepository.class);
        service = new ExamServiceImpl(repository, questionRepository);
    }

    @Test
    void findExamByName() {
        List<Exam> exams = Arrays.asList(new Exam(5L, "Matemáticas"), new Exam(6L, "Lenguaje"), new Exam(7L, "Historia"));
        when(repository.findAll()).thenReturn(exams);
        Optional<Exam> exam = service.findExamByName("Matemáticas");
        assertTrue(exam.isPresent());
        assertEquals(5L, exam.orElseThrow().getId());
        assertEquals("Matemáticas", exam.orElseThrow().getName());
    }
    @Test
    void findExamByNameEmptyList() {
        List<Exam> exams = Collections.emptyList();

        when(repository.findAll()).thenReturn(exams);
        Optional<Exam> exam = service.findExamByName("Matemáticas");
        assertFalse(exam.isPresent());
    }
}
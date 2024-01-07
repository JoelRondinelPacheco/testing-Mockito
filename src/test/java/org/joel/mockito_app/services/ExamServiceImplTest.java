package org.joel.mockito_app.services;

import org.joel.mockito_app.models.Exam;
import org.joel.mockito_app.repositories.ExamRepository;
import org.joel.mockito_app.repositories.ExamRepositoryImpl2;
import org.joel.mockito_app.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) //Tambien para habilitar anotaciones
class ExamServiceImplTest {

    @Mock
    ExamRepository repository;
    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    ExamServiceImpl service;

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this); habilitar anotacioes
//        repository = mock(ExamRepository.class);
//        questionRepository = mock(QuestionRepository.class);
//        service = new ExamServiceImpl(repository, questionRepository);
    }

    @Test
    void findExamByName() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
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

    @Test
    void testExamQuestions() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);

        Exam exam = service.findExamByNameWithQuestions("Matemáticas");

        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("arimética"));
    }

    @Test
    void testExamQuestionVerify() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);

        Exam exam = service.findExamByNameWithQuestions("Matemáticas");

        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("arimética"));
        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(anyLong());
    }

    @Test
    void testExamVerifyDoesNotExists() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);

        Exam exam = service.findExamByNameWithQuestions("Matemáticas");

        assertNotNull(exam);
        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(anyLong());
    }

    @Test
    void testSaveExam() {
        // Given (precondiciones)
        Exam newE = Data.EXAM;
        newE.setQuestions(Data.QUESTIONS);
        when(repository.save(any(Exam.class))).then(new Answer<Exam>() {

            Long sequence = 8L;
            @Override
            public Exam answer(InvocationOnMock invocationOnMock) throws Throwable {
                Exam exam = invocationOnMock.getArgument(0);
                exam.setId(sequence++);
                return exam;
            }
        });

        // When (se ejecuta un metodo a probrar)
        Exam exam = service.save(newE);

        // Then (validamos)
        assertNotNull(exam.getId());
        assertEquals(8L, exam.getId());
        assertEquals("Física", exam.getName());
        verify(repository).save(any(Exam.class));
        verify(questionRepository).saveQuestions(anyList());
    }
}
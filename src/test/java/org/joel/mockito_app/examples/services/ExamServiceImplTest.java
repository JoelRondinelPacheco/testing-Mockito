package org.joel.mockito_app.examples.services;

import org.joel.mockito_app.examples.Data;
import org.joel.mockito_app.examples.models.Exam;
import org.joel.mockito_app.examples.repositories.ExamRepository;
import org.joel.mockito_app.examples.repositories.ExamRepositoryImpl;
import org.joel.mockito_app.examples.repositories.QuestionRepository;
import org.joel.mockito_app.examples.repositories.QuestionRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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
    ExamRepositoryImpl repository;
    @Mock
    QuestionRepositoryImpl questionRepository;

    @InjectMocks
    ExamServiceImpl service;

    @Captor
    ArgumentCaptor<Long> captor;
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

    @Test
    void testExceptionHandler() {
        when(repository.findAll()).thenReturn(Data.EXAMS_ID_NULL);
        when(questionRepository.findQuestionByExamId(isNull())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.findExamByNameWithQuestions("Matemáticas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());
        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(isNull());
    }

    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);
        service.findExamByNameWithQuestions("Matemáticas");

        verify(repository).findAll();
//        verify(questionRepository).findQuestionByExamId(argThat(arg -> arg.equals(6L)));
//        verify(questionRepository).findQuestionByExamId(argThat(arg -> arg != null && arg.equals(6L)));
        verify(questionRepository).findQuestionByExamId(argThat(arg -> arg != null && arg >= 5L));
        verify(questionRepository).findQuestionByExamId(eq(5L));
    }
    @Test
    void testArgumentMatchers2() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);
        service.findExamByNameWithQuestions("Matemáticas");

        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(argThat(new MyArgsMatchers()));
    }
    @Test
    void testArgumentMatchers3() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);
        service.findExamByNameWithQuestions("Matemáticas");

        verify(repository).findAll();
        verify(questionRepository).findQuestionByExamId(argThat((arg) -> arg != null && arg > 0));
    }


    public static class MyArgsMatchers implements ArgumentMatcher<Long> {

        private  Long argument;
        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }

        @Override
        public String toString() {
            return "Es un mensaje personalizado de error que imprime mockite en caso que falle eltest. [" + argument + "] Debe ser un número positivo";
        }
    }


    @Test
    void testArgumentCaptor() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);

        service.findExamByNameWithQuestions("Matemáticas");

//        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(questionRepository).findQuestionByExamId(captor.capture());

        assertEquals(5L, captor.getValue());
    }

    @Test
    void testDoThrow() {
        Exam exam = Data.EXAM;
        exam.setQuestions(Data.QUESTIONS);
        doThrow(IllegalArgumentException.class).when(questionRepository).saveQuestions(anyList());
        assertThrows(IllegalArgumentException.class, () -> {
            service.save(exam);
        });
    }

    @Test
    void testDoAnswer() {
        when(repository.findAll()).thenReturn(Data.EXAMS);

//        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return id == 5L ? Data.QUESTIONS : Collections.emptyList();
        }).when(questionRepository).findQuestionByExamId(anyLong());

        Exam exam = service.findExamByNameWithQuestions("Matemáticas");

        assertEquals(5, exam.getQuestions().size());
        assertTrue(exam.getQuestions().contains("geometría"));
        assertEquals(5L, exam.getId());
        assertEquals("Matemáticas", exam.getName());

        verify(questionRepository).findQuestionByExamId(anyLong());
    }

    @Test
    void testDoAnswerSaveExam() {
        // Given (precondiciones)
        Exam newE = Data.EXAM;
        newE.setQuestions(Data.QUESTIONS);
        doAnswer(new Answer<Exam>() {

            Long sequence = 8L;
            @Override
            public Exam answer(InvocationOnMock invocationOnMock) throws Throwable {
                Exam exam = invocationOnMock.getArgument(0);
                exam.setId(sequence++);
                return exam;
            }
            }).when(repository).save(any(Exam.class));

        // When (se ejecuta un metodo a probrar)
        Exam exam = service.save(newE);

        // Then (validamos)
        assertNotNull(exam.getId());
        assertEquals(8L, exam.getId());
        assertEquals("Física", exam.getName());
        verify(repository).save(any(Exam.class));
        verify(questionRepository).saveQuestions(anyList());
    }

    @Test
    void testDoCallRealMethod() {
        when(repository.findAll()).thenReturn(Data.EXAMS);
//        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(Data.QUESTIONS);
        doCallRealMethod().when(questionRepository).findQuestionByExamId(anyLong());
        Exam exam = service.findExamByNameWithQuestions("Matemáticas");

        assertEquals(5L, exam.getId());
        assertEquals("Matemáticas", exam.getName());
    }

    @Test
    void testSpy() {
        //Tambien se puede hacer con anotaciones
        ExamRepository examRepository = spy(ExamRepositoryImpl.class);
        QuestionRepository questionRepository = spy(QuestionRepositoryImpl.class);

        List<String> questions = Arrays.asList("aruemtica");
//        when(questionRepository.findQuestionByExamId(anyLong())).thenReturn(questions);
        doReturn(questions).when(questionRepository).findQuestionByExamId(anyLong());

        ExamService examService = new ExamServiceImpl(examRepository, questionRepository);
        Exam exam = examService.findExamByNameWithQuestions("Matemáticas");
        assertEquals(5, exam.getId());
        assertEquals("Matemáticas", exam.getName());
        assertTrue(exam.getQuestions().contains("arimética"));

        verify(examRepository).findAll();
        verify(questionRepository).findQuestionByExamId(anyLong());

    }
}
import org.joel.mockito_app.examples.Data;
import org.joel.mockito_app.examples.models.Exam;
import org.joel.mockito_app.examples.repositories.ExamRepository;
import org.joel.mockito_app.examples.repositories.ExamRepositoryImpl;
import org.joel.mockito_app.examples.repositories.QuestionRepository;
import org.joel.mockito_app.examples.repositories.QuestionRepositoryImpl;
import org.joel.mockito_app.examples.services.ExamService;
import org.joel.mockito_app.examples.services.ExamServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //Tambien para habilitar anotaciones
class ExamServiceImplTestSpy {

    @Spy
    ExamRepositoryImpl repository;
    @Spy
    QuestionRepositoryImpl questionRepository;

    @InjectMocks
    ExamServiceImpl service;
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
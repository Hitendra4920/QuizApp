package com.example.demo.service;



import com.example.demo.entity.Quiz;
import com.example.demo.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuiz() {
        Quiz quiz = new Quiz();
        quiz.setTitle("Spring Boot Quiz");

        when(quizRepository.save(any(Quiz.class))).thenAnswer(invocation -> {
            Quiz q = invocation.getArgument(0);
            q.setId(1L); // simulate DB-generated ID
            return q;
        });

        Quiz created = quizService.createQuiz("Spring Boot Quiz");

        assertNotNull(created);
        assertEquals("Spring Boot Quiz", created.getTitle());
        assertEquals(1L, created.getId());
        verify(quizRepository, times(1)).save(any(Quiz.class));
    }

    @Test
    void testGetAllQuizzes() {
        Quiz quiz1 = new Quiz();
        quiz1.setId(1L);
        quiz1.setTitle("Quiz 1");

        Quiz quiz2 = new Quiz();
        quiz2.setId(2L);
        quiz2.setTitle("Quiz 2");

        when(quizRepository.findAll()).thenReturn(List.of(quiz1, quiz2));

        List<Quiz> quizzes = quizService.getAllQuizzes();

        assertEquals(2, quizzes.size());
        assertEquals("Quiz 1", quizzes.get(0).getTitle());
        assertEquals("Quiz 2", quizzes.get(1).getTitle());
        verify(quizRepository, times(1)).findAll();
    }

    @Test
    void testGetQuizById_Found() {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Java Quiz");

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Optional<Quiz> result = quizService.getQuizById(1L);

        assertTrue(result.isPresent());
        assertEquals("Java Quiz", result.get().getTitle());
        verify(quizRepository, times(1)).findById(1L);
    }

    @Test
    void testGetQuizById_NotFound() {
        when(quizRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Quiz> result = quizService.getQuizById(99L);

        assertFalse(result.isPresent());
        verify(quizRepository, times(1)).findById(99L);
    }
}

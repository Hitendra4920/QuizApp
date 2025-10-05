package com.example.demo.service;

import com.example.demo.dto.QuestionForQuizDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.Quiz;
import com.example.demo.repository.QuestionRepository;
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

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuestionService questionService;

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Java Quiz");
    }

    @Test
    void testAddQuestion_success() {
        Question question = new Question();
        question.setId(1L);
        question.setText("What is Spring Boot?");
        question.setType("TEXT");

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(questionRepository.save(question)).thenReturn(question);

        Question saved = questionService.addQuestion(1L, question);

        assertNotNull(saved);
        assertEquals("What is Spring Boot?", saved.getText());
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    void testAddQuestion_textTooLong_shouldThrow() {
        Question question = new Question();
        question.setId(2L);
        question.setType("TEXT");

        // 301 characters
        question.setText("a".repeat(301));

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                questionService.addQuestion(1L, question));

        assertEquals("Text question cannot exceed 300 characters", exception.getMessage());
        verify(questionRepository, never()).save(any());
    }

    @Test
    void testAddQuestion_quizNotFound_shouldThrow() {
        Question question = new Question();
        question.setId(3L);
        question.setText("Sample question");
        question.setType("TEXT");

        when(quizRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                questionService.addQuestion(1L, question));

        assertEquals("Quiz not found", exception.getMessage());
        verify(questionRepository, never()).save(any());
    }

    @Test
    void testGetQuestionsByQuiz() {
        Question q1 = new Question();
        q1.setId(1L);
        Question q2 = new Question();
        q2.setId(2L);

        when(questionRepository.findByQuizId(1L)).thenReturn(List.of(q1, q2));

        List<Question> questions = questionService.getQuestionsByQuiz(1L);

        assertEquals(2, questions.size());
        verify(questionRepository, times(1)).findByQuizId(1L);
    }

    @Test
    void testGetQuestionsForQuiz_dtoMapping() {
        Question q1 = new Question();
        q1.setId(1L);
        q1.setText("Q1");
        q1.setType("SINGLE");

        Question q2 = new Question();
        q2.setId(2L);
        q2.setText("Q2");
        q2.setType("MULTIPLE");

        when(questionRepository.findByQuizId(1L)).thenReturn(List.of(q1, q2));

        List<QuestionForQuizDTO> dtos = questionService.getQuestionsForQuiz(1L);

        assertEquals(2, dtos.size());
        assertEquals("Q1", dtos.get(0).getText());
        assertEquals("Q2", dtos.get(1).getText());
        verify(questionRepository, times(1)).findByQuizId(1L);
    }
}

package com.example.demo.service;
import com.example.demo.entity.Question;
import com.example.demo.entity.QuizOption;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuizOptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuizOptionServiceTest {

    @Mock
    private QuizOptionRepository quizOptionRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuizOptionService quizOptionService;

    private Question question;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        question = new Question();
        question.setId(1L);
        question.setText("What is Java?");
        question.setType("SINGLE");
    }

    @Test
    void testAddOption_Success() {
        QuizOption option = new QuizOption();
        option.setText("Programming Language");
        option.setCorrect(true);

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(quizOptionRepository.save(any(QuizOption.class))).thenAnswer(i -> i.getArguments()[0]);

        QuizOption savedOption = quizOptionService.addOption(1L, option);

        assertNotNull(savedOption);
        assertEquals("Programming Language", savedOption.getText());
        assertEquals(question, savedOption.getQuestion());
        verify(questionRepository, times(1)).findById(1L);
        verify(quizOptionRepository, times(1)).save(option);
    }

    @Test
    void testAddOption_QuestionNotFound() {
        QuizOption option = new QuizOption();
        option.setText("Programming Language");

        when(questionRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            quizOptionService.addOption(99L, option);
        });

        assertEquals("Question not found", exception.getMessage());
        verify(questionRepository, times(1)).findById(99L);
        verifyNoInteractions(quizOptionRepository);
    }

    @Test
    void testGetOptionsByQuestion() {
        QuizOption option1 = new QuizOption();
        option1.setId(1L);
        option1.setText("Option 1");

        QuizOption option2 = new QuizOption();
        option2.setId(2L);
        option2.setText("Option 2");

        when(quizOptionRepository.findByQuestionId(1L)).thenReturn(List.of(option1, option2));

        List<QuizOption> options = quizOptionService.getOptionsByQuestion(1L);

        assertEquals(2, options.size());
        assertEquals("Option 1", options.get(0).getText());
        assertEquals("Option 2", options.get(1).getText());
        verify(quizOptionRepository, times(1)).findByQuestionId(1L);
    }
}

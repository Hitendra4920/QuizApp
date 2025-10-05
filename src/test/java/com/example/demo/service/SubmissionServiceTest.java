package com.example.demo.service;

import com.example.demo.dto.AnswerRequest;
import com.example.demo.dto.ScoreResponseDTO;
import com.example.demo.entity.*;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.QuizOptionRepository;
import com.example.demo.repository.SubmissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class SubmissionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuizOptionRepository quizOptionRepository;

    @Mock
    private SubmissionRepository submissionRepository;

    @InjectMocks
    private SubmissionService submissionService;

    private Question singleQ;
    private Question multiQ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // SINGLE question
        singleQ = new Question();
        singleQ.setId(1L);
        singleQ.setText("What is 2+2?");
        singleQ.setType("SINGLE");

        QuizOption option1 = new QuizOption();
        option1.setId(1L);
        option1.setText("4");
        option1.setCorrect(true);
        option1.setQuestion(singleQ);

        QuizOption option2 = new QuizOption();
        option2.setId(2L);
        option2.setText("3");
        option2.setCorrect(false);
        option2.setQuestion(singleQ);

        singleQ.setOptions(List.of(option1, option2));

        // MULTIPLE question
        multiQ = new Question();
        multiQ.setId(2L);
        multiQ.setText("Which are Java data types?");
        multiQ.setType("MULTIPLE");

        QuizOption option3 = new QuizOption();
        option3.setId(3L);
        option3.setText("int");
        option3.setCorrect(true);
        option3.setQuestion(multiQ);

        QuizOption option4 = new QuizOption();
        option4.setId(4L);
        option4.setText("String");
        option4.setCorrect(true);
        option4.setQuestion(multiQ);

        QuizOption option5 = new QuizOption();
        option5.setId(5L);
        option5.setText("extends");
        option5.setCorrect(false);
        option5.setQuestion(multiQ);

        multiQ.setOptions(List.of(option3, option4, option5));
    }

    @Test
    void testSubmitAnswers_correct() {
        when(questionRepository.findByQuizId(1L)).thenReturn(List.of(singleQ, multiQ));

        AnswerRequest singleAnswer = new AnswerRequest();
        singleAnswer.setQuestionId(1L);
        singleAnswer.setSelectedOptionIds(List.of(1L));

        AnswerRequest multiAnswer = new AnswerRequest();
        multiAnswer.setQuestionId(2L);
        multiAnswer.setSelectedOptionIds(List.of(3L, 4L));

        List<AnswerRequest> answers = List.of(singleAnswer, multiAnswer);

        ScoreResponseDTO response = submissionService.submitAnswers(1L, answers, 101L);

        assertEquals(2, response.getScore());
        assertEquals(2, response.getTotal());
    }

    @Test
    void testSubmitAnswers_incorrect() {
        when(questionRepository.findByQuizId(1L)).thenReturn(List.of(singleQ, multiQ));

        AnswerRequest singleAnswer = new AnswerRequest();
        singleAnswer.setQuestionId(1L);
        singleAnswer.setSelectedOptionIds(List.of(1L));

        AnswerRequest multiAnswer = new AnswerRequest();
        multiAnswer.setQuestionId(2L);
        multiAnswer.setSelectedOptionIds(List.of(3L)); // missing "String"

        List<AnswerRequest> answers = List.of(singleAnswer, multiAnswer);

        ScoreResponseDTO response = submissionService.submitAnswers(1L, answers, 101L);

        assertEquals(1, response.getScore());
        assertEquals(2, response.getTotal());
    }
}

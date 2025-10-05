package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.QuestionForQuizDTO;
import com.example.demo.dto.QuestionRequest;
import com.example.demo.entity.Question;
import com.example.demo.service.QuestionService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/{quizId}")
    public Question addQuestion(@PathVariable Long quizId,@Valid  @RequestBody Question question) {
        return questionService.addQuestion(quizId, question);
    }
    
    
//    @PostMapping("/{quizId}")
//    public Question addQuestion(@PathVariable Long quizId,
//                                @RequestBody QuestionRequest request) {
//        Question question = new Question();
//        question.setText(request.getText());
//        question.setType(request.getType());
//
//        return questionService.addQuestion(quizId, question);
//    }

    @GetMapping("/quiz/{quizId}")
    public List<Question> getQuestionsByQuiz(@PathVariable Long quizId) {
        return questionService.getQuestionsByQuiz(quizId);
    }
    
    @GetMapping("/quiz/{quizId}/take")
    public List<QuestionForQuizDTO> getQuestionsForQuiz(@PathVariable Long quizId) {
        return questionService.getQuestionsForQuiz(quizId);
    }

}

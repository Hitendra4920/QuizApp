package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Quiz;
import com.example.demo.service.QuizService;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public Quiz createQuiz(@RequestParam String title) {
        return quizService.createQuiz(title);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable Long id) {
        return quizService.getQuizById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
    }
}


package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.QuizOption;
import com.example.demo.service.QuizOptionService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/options")
public class QuizOptionController {

    private final QuizOptionService quizOptionService;

    public QuizOptionController(QuizOptionService quizOptionService) {
        this.quizOptionService = quizOptionService;
    }

    @PostMapping("/{questionId}")
    public QuizOption addOption(@PathVariable Long questionId,@Valid  @RequestBody QuizOption option) {
        return quizOptionService.addOption(questionId, option);
    }

    @GetMapping("/question/{questionId}")
    public List<QuizOption> getOptionsByQuestion(@PathVariable Long questionId) {
        return quizOptionService.getOptionsByQuestion(questionId);
    }
}

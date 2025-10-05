package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AnswerRequest;
import com.example.demo.dto.ScoreResponseDTO;
import com.example.demo.entity.Submission;
import com.example.demo.service.SubmissionService;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

//    @PostMapping("/{quizId}")
//    public Submission submitAnswers(@PathVariable Long quizId,
//                                    @RequestBody List<AnswerRequest> answers,
//                                    @RequestParam Long userId) {
//        return submissionService.submitAnswers(quizId, answers, userId);
//    }
    
    @PostMapping("/{quizId}")
    public ScoreResponseDTO submitAnswers(@PathVariable Long quizId,
                                          @RequestBody List<AnswerRequest> answers,
                                          @RequestParam Long userId) {
        return (ScoreResponseDTO) submissionService.submitAnswers(quizId, answers, userId);
    }

}

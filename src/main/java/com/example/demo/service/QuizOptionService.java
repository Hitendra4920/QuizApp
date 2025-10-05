package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Question;
import com.example.demo.entity.QuizOption;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuizOptionRepository;

import java.util.List;

@Service
public class QuizOptionService {

    private final QuizOptionRepository quizOptionRepository;
    private final QuestionRepository questionRepository;

    public QuizOptionService(QuizOptionRepository quizOptionRepository, QuestionRepository questionRepository) {
        this.quizOptionRepository = quizOptionRepository;
        this.questionRepository = questionRepository;
    }

    public QuizOption addOption(Long questionId, QuizOption option) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));
        option.setQuestion(question);
        return quizOptionRepository.save(option);
    }
    
   // public QuizOption addOption(Long questionId, QuizOption option) {
//        Question question = questionRepository.findById(questionId)
//                .orElseThrow(() -> new RuntimeException("Question not found"));
//
//        // SINGLE/MULTIPLE choice validation
//        if (("SINGLE".equalsIgnoreCase(question.getType()) || "MULTIPLE".equalsIgnoreCase(question.getType())) 
//            && !option.isCorrect() && question.getOptions().stream().noneMatch(QuizOption::isCorrect)) {
//            throw new RuntimeException("At least one option must be marked correct for SINGLE/MULTIPLE choice questions");
//        }
//
//        option.setQuestion(question);
//        return quizOptionRepository.save(option);
//    }


    public List<QuizOption> getOptionsByQuestion(Long questionId) {
        return quizOptionRepository.findByQuestionId(questionId);
    }
}

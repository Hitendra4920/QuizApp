//package com.example.demo.service;
//
//import org.springframework.stereotype.Service;
//
//import com.example.demo.dto.QuestionForQuizDTO;
//import com.example.demo.entity.Question;
//import com.example.demo.entity.Quiz;
//import com.example.demo.entity.QuizOption;
//import com.example.demo.repository.QuestionRepository;
//import com.example.demo.repository.QuizRepository;
//
//import java.util.List;
//
//@Service
//public class QuestionService {
//
//    private final QuestionRepository questionRepository;
//    private final QuizRepository quizRepository;
//
//    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository) {
//        this.questionRepository = questionRepository;
//        this.quizRepository = quizRepository;
//    }
//
////    public Question addQuestion(Long quizId, Question question) {
////        Quiz quiz = quizRepository.findById(quizId)
////                .orElseThrow(() -> new RuntimeException("Quiz not found"));
////        question.setQuiz(quiz);
////        return questionRepository.save(question);
////    }
//    
//    public Question addQuestion(Long quizId, Question question) {
//        Quiz quiz = quizRepository.findById(quizId)
//                .orElseThrow(() -> new RuntimeException("Quiz not found"));
//        question.setQuiz(quiz);
//
//        // --- Validation based on question type ---
//        if ("TEXT".equalsIgnoreCase(question.getType())) {
//            if (!question.getOptions().isEmpty()) {
//                throw new RuntimeException("Text questions cannot have options");
//            }
//        } else if ("SINGLE".equalsIgnoreCase(question.getType()) || 
//                   "MULTIPLE".equalsIgnoreCase(question.getType())) {
//            boolean hasCorrect = question.getOptions().stream()
//                                    .anyMatch(QuizOption::isCorrect);
//            if (!hasCorrect) {
//                throw new RuntimeException("At least one option must be marked correct for SINGLE/MULTIPLE choice questions");
//            }
//        } else {
//            throw new RuntimeException("Invalid question type");
//        }
//
//        return questionRepository.save(question);
//    }
//
//
//    public List<Question> getQuestionsByQuiz(Long quizId) {
//        return questionRepository.findByQuizId(quizId);
//    }
//    
//    public List<QuestionForQuizDTO> getQuestionsForQuiz(Long quizId) {
//        List<Question> questions = questionRepository.findByQuizId(quizId);
//
//        return questions.stream().map(q -> {
//            QuestionForQuizDTO dto = new QuestionForQuizDTO();
//            dto.setId(q.getId());
//            dto.setText(q.getText());
//            dto.setType(q.getType());  // no .name()
//
//
//            List<QuestionForQuizDTO.OptionDTO> options = q.getOptions().stream().map(o -> {
//                QuestionForQuizDTO.OptionDTO opt = new QuestionForQuizDTO.OptionDTO();
//                opt.setId(o.getId());
//                opt.setText(o.getText());
//                return opt;
//            }).toList();
//
//            dto.setOptions(options);
//            return dto;
//        }).toList();
//    }
//
//}
package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.QuestionForQuizDTO;
import com.example.demo.entity.Question;
import com.example.demo.entity.Quiz;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuizRepository;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    public QuestionService(QuestionRepository questionRepository, QuizRepository quizRepository) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
    }

    // Add question without validating options
//    public Question addQuestion(Long quizId, Question question) {
//        Quiz quiz = quizRepository.findById(quizId)
//                .orElseThrow(() -> new RuntimeException("Quiz not found"));
//        question.setQuiz(quiz);
//        return questionRepository.save(question);
//    }
//    
    public Question addQuestion(Long quizId, Question question) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));
        
        // Text-based question validation
        if ("TEXT".equalsIgnoreCase(question.getType()) && question.getText().length() > 300) {
            throw new RuntimeException("Text question cannot exceed 300 characters");
        }

        question.setQuiz(quiz);
        return questionRepository.save(question);
    }


    public List<Question> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public List<QuestionForQuizDTO> getQuestionsForQuiz(Long quizId) {
        List<Question> questions = questionRepository.findByQuizId(quizId);

        return questions.stream().map(q -> {
            QuestionForQuizDTO dto = new QuestionForQuizDTO();
            dto.setId(q.getId());
            dto.setText(q.getText());
            dto.setType(q.getType());

            List<QuestionForQuizDTO.OptionDTO> options = q.getOptions().stream().map(o -> {
                QuestionForQuizDTO.OptionDTO opt = new QuestionForQuizDTO.OptionDTO();
                opt.setId(o.getId());
                opt.setText(o.getText());
                return opt;
            }).toList();

            dto.setOptions(options);
            return dto;
        }).toList();
    }
}

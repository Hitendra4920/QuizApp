//package com.example.demo.service;
//
//import org.springframework.stereotype.Service;
//
//import com.example.demo.entity.Question;
//import com.example.demo.entity.Quiz;
//import com.example.demo.entity.QuizOption;
//import com.example.demo.entity.Submission;
//import com.example.demo.repository.QuestionRepository;
//import com.example.demo.repository.QuizOptionRepository;
//import com.example.demo.repository.QuizRepository;
//import com.example.demo.repository.SubmissionRepository;
//
//import java.util.List;
//
//@Service
//public class SubmissionService {
//
//    private final SubmissionRepository submissionRepository;
//    private final QuizRepository quizRepository;
//    private final QuestionRepository questionRepository;
//    private final QuizOptionRepository quizOptionRepository;
//
//    public SubmissionService(SubmissionRepository submissionRepository,
//                             QuizRepository quizRepository,
//                             QuestionRepository questionRepository,
//                             QuizOptionRepository quizOptionRepository) {
//        this.submissionRepository = submissionRepository;
//        this.quizRepository = quizRepository;
//        this.questionRepository = questionRepository;
//        this.quizOptionRepository = quizOptionRepository;
//    }
//
//    public Submission submitAnswers(Long quizId, List<AnswerRequest> answers, Long userId) {
//        Quiz quiz = quizRepository.findById(quizId)
//                .orElseThrow(() -> new RuntimeException("Quiz not found"));
//
//        int score = 0;
//        for (AnswerRequest answer : answers) {
//            Question question = questionRepository.findById(answer.getQuestionId())
//                    .orElseThrow(() -> new RuntimeException("Question not found"));
//
//            // Simple scoring: check if selected options match correct options
//            List<QuizOption> correctOptions = quizOptionRepository.findByQuestionId(question.getId())
//                    .stream()
//                    .filter(QuizOption::isCorrect)
//                    .toList();
//
//            if (correctOptions.stream().allMatch(o -> answer.getSelectedOptionIds().contains(o.getId())) &&
//                answer.getSelectedOptionIds().size() == correctOptions.size()) {
//                score++;
//            }
//        }
//
//        Submission submission = new Submission(userId, quiz, score);
//        return submissionRepository.save(submission);
//    }
//}

package com.example.demo.service;

import com.example.demo.dto.AnswerRequest;
import com.example.demo.dto.ScoreResponseDTO;
import com.example.demo.entity.Quiz;
import com.example.demo.entity.Submission;
import com.example.demo.entity.Question;
import com.example.demo.entity.QuizOption;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuizOptionRepository;
import com.example.demo.repository.SubmissionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizOptionRepository quizOptionRepository;

    public SubmissionService(SubmissionRepository submissionRepository,
                             QuizRepository quizRepository,
                             QuestionRepository questionRepository,
                             QuizOptionRepository quizOptionRepository) {
        this.submissionRepository = submissionRepository;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.quizOptionRepository = quizOptionRepository;
    }

//    public Submission submitAnswers(Long quizId, List<AnswerRequest> answers, Long userId) {
//        Quiz quiz = quizRepository.findById(quizId)
//                .orElseThrow(() -> new RuntimeException("Quiz not found"));
//
//        int score = 0;
//        for (AnswerRequest answer : answers) {
//            Question question = questionRepository.findById(answer.getQuestionId())
//                    .orElseThrow(() -> new RuntimeException("Question not found"));
//
//            List<QuizOption> correctOptions = quizOptionRepository.findByQuestionId(question.getId())
//                    .stream()
//                    .filter(QuizOption::isCorrect)
//                    .toList();
//
//            if (correctOptions.stream().allMatch(o -> answer.getSelectedOptionIds().contains(o.getId())) &&
//                answer.getSelectedOptionIds().size() == correctOptions.size()) {
//                score++;
//            }
//        }
//
//        Submission submission = new Submission(userId, quiz, score);
//        return submissionRepository.save(submission);
//    }
    
    public ScoreResponseDTO submitAnswers(Long quizId, List<AnswerRequest> answers, Long userId) {
        int score = 0;
        List<Question> questions = questionRepository.findByQuizId(quizId);

        for (AnswerRequest answer : answers) {
            Question q = questions.stream()
                    .filter(ques -> ques.getId().equals(answer.getQuestionId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            List<Long> correctOptionIds = q.getOptions().stream()
                    .filter(QuizOption::isCorrect)
                    .map(QuizOption::getId)
                    .toList();

            if (correctOptionIds.containsAll(answer.getSelectedOptionIds()) &&
                answer.getSelectedOptionIds().containsAll(correctOptionIds)) {
                score++;
            }
        }

        return new ScoreResponseDTO(score, questions.size());
    }

}


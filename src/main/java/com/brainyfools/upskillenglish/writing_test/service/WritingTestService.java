package com.brainyfools.upskillenglish.writing_test.service;

import com.brainyfools.upskillenglish.auth.model.User;
import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import com.brainyfools.upskillenglish.gemini.GeminiService;
import com.brainyfools.upskillenglish.writing_test.model.GraphData;
import com.brainyfools.upskillenglish.writing_test.model.PassageTestDto;
import com.brainyfools.upskillenglish.writing_test.model.ScoreDto;
import com.brainyfools.upskillenglish.writing_test.model.SolutionDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class WritingTestService {

    private static final Logger LOGGER = LogManager.getLogger(WritingTestService.class);
    private final GeminiService geminiService;
    private final UserRepository userRepository;

    public WritingTestService(GeminiService geminiService, UserRepository userRepository) {
        this.geminiService = geminiService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> generateGraph() {
        String prompt = """
                Generate JSON data for a pie chart comparison over two different years, focusing on topics commonly seen in IELTS writing tasks. The topics should be varied and can include categories such as:
                
                - Household expenditure
                - Types of transportation used by people
                - Causes of pollution
                - Student enrollment in different subjects
                - Employment sectors
                - Distribution of population by age group
                
                The data should include:
                1. Labels for the categories (e.g., Food, Housing, Transportation for household expenditure).
                2. Numerical values for each year (e.g., 1990 and 2020).
                3. Descriptions for the years (e.g., "Household expenditure in 1990" and "Household expenditure in 2020").
                4. A writing prompt asking students to describe the changes between the two years in about 150 words.
                5. An answer to the writing task that analyzes the data and makes comparisons.
                
                The JSON structure should be like this:
                {
                    "labels": ["Food", "Housing", "Transportation", "Education", "Other"],
                    "year1Values": [30.80, 25.63, 15.27, 10.40, 18.90],
                    "year2Values": [35.93, 20.31, 12.55, 18.10, 13.10],
                    "year1Label": "1990",
                    "year2Label": "2020",
                    "writingPrompt": "The pie charts below show the comparison of household expenditure in 1990 and 2020. Summarize the information by selecting and reporting the main features, and make comparisons where relevant. Write at least 150 words.",
                    "answer": "In 1990, the largest portion of household expenditure was on food, followed by housing and transportation. However, in 2020, spending on food increased, while spending on housing dropped significantly. Expenditure on education nearly doubled, suggesting a greater focus on personal development. Transportation and other categories saw slight changes."
                }
                
                Ensure the data reflects a logical and varied distribution of values between the two years. Provide a fresh topic for every request.
                """;
        GraphData graphData = geminiService.call(prompt, GraphData.class);
        LOGGER.info("Graph data: {}", graphData);
        return new ResponseEntity<>(graphData, HttpStatus.CREATED);
    }

    public ResponseEntity<?> generatePassageTest() {
        String prompt = """
                You are an expert in generating IELTS essays Writing Task.
                Please generate a unique, fresh topic for an IELTS essay, followed by a well-structured essay of approximately 250 words on the same topic.
                
                Make sure the topic is aligned with common IELTS themes such as education, environment, technology, or social issues.
                
                The essay should:
                - Demonstrate clear and logical reasoning.
                - Present strong and relevant arguments.
                - Use appropriate IELTS-level vocabulary and grammar.
                - Have a clear introduction, body paragraphs, and conclusion.
                
                Ensure that the topic and essay are fresh for each request, and the essay remains relevant to the topic provided.
                
                Response format (JSON):
                {
                    "topic": "String",
                    "answer": "String"
                }
                """;

        PassageTestDto passageTestDto = geminiService.call(prompt, PassageTestDto.class);
        LOGGER.info("Passage Test Dto: {}", passageTestDto);
        return new ResponseEntity<>(passageTestDto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getScore(SolutionDto solutionDto, Authentication authentication) {
        String prompt = String.format("""
                You are given a question/essay topic, a correct answer, and a user answer.
                Your task is to evaluate the user answer and score it out of 10, based on comparison with the question/essay topic and correct answer.
                
                Question/Essay topic: %s
                Correct answer: %s
                User answer: %s
                
                If the user answer is empty, assign a score of 0.
                
                Your response should be in the following JSON format:
                {
                    "score": 10
                }
                
                Evaluation Criteria:
                - The user answer should not be an exact 100%% match with the correct answer.
                - Score the answer as an IELTS examiner would, considering:
                  1. Grammar: Issues such as verb tense, subject-verb agreement, articles, prepositions.
                  2. Spelling & Punctuation: Accuracy in spelling and punctuation usage.
                  3. Coherence & Organization: Logical flow, paragraph structure, and clarity of ideas.
                  4. Task Response: How well the user addresses the question and adheres to any word limit or instructions.
                  5. Lexical Resource: Vocabulary richness and the ability to paraphrase.
                  6. If user doesn't answer anything, score 0.
                
                Justify the score based on these criteria.
                """, solutionDto.getQuestion(), solutionDto.getCorrectAnswer(), solutionDto.getUserAnswer());

        ScoreDto scoreDto = geminiService.call(prompt, ScoreDto.class);

        User user = (User) authentication.getPrincipal();
        user.increaseXp(scoreDto.getScore());
        LOGGER.info("Added score: {}", scoreDto.getScore());
        userRepository.save(user);

        return new ResponseEntity<>(scoreDto, HttpStatus.OK);
    }
}

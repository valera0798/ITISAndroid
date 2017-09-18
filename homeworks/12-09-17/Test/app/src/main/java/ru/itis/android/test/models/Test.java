package ru.itis.android.test.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Users on 09.09.2017.
 */

public class Test {
    protected List<Question> questionList;
    protected int currentQuestionIndex;
    protected int rightAnsweredQuestionsNumber;

    public Test() {
    }

    public Test(String[] questions, String[] answers, String separator, int rightAnswerIndex) {
        questionList = new ArrayList<>();
        currentQuestionIndex = 0;
        rightAnsweredQuestionsNumber = 0;
        initQuestionList(questions, answers, separator, rightAnswerIndex);
    }

    private void initQuestionList(String[] questions, String[] answers, String separator, int rightAnswerIndex) {
        String[] currentQuestionAnswers;
        for (int i = 0; i < questions.length; i++) {
            currentQuestionAnswers = answers[i].split(separator);
            questionList.add(new Question(questions[i],
                    currentQuestionAnswers,
                    currentQuestionAnswers[rightAnswerIndex]));
        }
    }

    public boolean isEnded() {
        return (currentQuestionIndex == questionList.size() - 1);
    }

    public Question getNextQuestion() {
        if (currentQuestionIndex != questionList.size() - 1)
            return questionList.get(++currentQuestionIndex);
        else
            // TODO исключение, которые нужно обработать
            return null;
    }

    public Question getPrevQuestion() {
        if (currentQuestionIndex != 0) {
            Question currentQuestion = questionList.get(--currentQuestionIndex);
            currentQuestion.clearAnswer();
            return currentQuestion;
        } else
            // TODO исключение, которые нужно обработать
            return null;
    }

    public Question getCurrentQuestion() {
        return questionList.get(currentQuestionIndex);
    }

    public int getCurrentQuestionIndex() { return currentQuestionIndex; }

    public int getQuestionsNumber() { return questionList.size(); }

    public int getRightAnsweredQuestionsNumber() {
        return rightAnsweredQuestionsNumber;
    }

    public void verifyCorrectness(Question currentQuestion) {
        if (currentQuestion.isRightAnswered()) rightAnsweredQuestionsNumber++;
    }
}

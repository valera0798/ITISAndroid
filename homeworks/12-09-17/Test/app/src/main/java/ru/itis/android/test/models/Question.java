package ru.itis.android.test.models;

/**
 * Created by Users on 09.09.2017.
 */

public class Question {
    private String text;
    private String[] answers;
    private String rightAnswer;
    private boolean isRightAnswered;

    public Question(String text, String[] answers, String rightAnswer) {
        this.text = text;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }

    public void answer(String answer) {
        isRightAnswered = rightAnswer.equals(answer);
    }

    public void clearAnswer() {
        isRightAnswered = false;
    }

    public boolean isRightAnswered() {
        return isRightAnswered;
    }

    public String getText() {
        return text;
    }

    public String[] getAnswers() {
        return answers;
    }
}

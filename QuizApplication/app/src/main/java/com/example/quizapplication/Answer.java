package com.example.quizapplication;

public class Answer {
    private int question;
    private boolean answer;

    public Answer(int question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    //int[] arrOfQuestions = {R.string.question_1, R.string.question_2, R.string.question_3, R.string.question_4, R.string.question_5 };
}

package com.thehoneycombworks;

public class Question {
    private String questionText = "";
    private String questionNote = "";
    private String answer = "";

    public Question(String questionText, String questionNote, String answer) {
        this.questionText = questionText;
        this.questionNote = questionNote;
        this.answer = answer;
    }

    public Question() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionNote() {
        return questionNote;
    }

    public void setQuestionNote(String questionNote) {
        this.questionNote = questionNote;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

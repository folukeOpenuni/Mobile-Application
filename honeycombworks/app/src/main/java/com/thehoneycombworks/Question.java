package com.thehoneycombworks;

public class Question {
    private String questionText = "";
    private String questionNote = "";

    public Question(String questionText, String questionNote) {
        this.questionText = questionText;
        this.questionNote = questionNote;
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

}

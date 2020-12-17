package com.thehoneycombworks;

import java.util.ArrayList;

public class Question {
    private String questionText = "";
    private String questionNote = "";
    private String answer = "";
    private String type = "String";
    private ArrayList<String> dropDownOption = new ArrayList<>();
    Enum String, email, date, dropDown;


    public Question(String questionText, String questionNote, String answer, String type, ArrayList<String> dropDownOption) {
        this.questionText = questionText;
        this.questionNote = questionNote;
        this.answer = answer;
        this.type = type;
        this.dropDownOption = dropDownOption;
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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getDropDownOption() {
        return dropDownOption;
    }

    public void setDropDownOption(ArrayList<String> dropDownOption) {
        this.dropDownOption = dropDownOption;
    }
}

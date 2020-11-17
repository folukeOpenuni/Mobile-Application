package com.example.quizapplication;
public class Answer {
    public static final String TABLE_NAME = "questions";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER = "answer";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_QUESTION + " TEXT,"
                    + COLUMN_ANSWER + " TEXT"
                    + ")";


    public void setAnswer(String answer) {
        this.answer = answer;
    }

    private int id;
    private String question;
    private String answer;


    public Answer(int id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }
}

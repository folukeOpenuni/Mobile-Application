package com.thehoneycombworks;

public class Questions {
    /**
     * all question attributes
     */
    private String question_1;
    private String question_2;
    private String question_3;
    private String question_3_note; //email notes
    private String question_4;
    private String question_5;
    private String question_5_note; //situation note
    private String question_6;
    private String question_6_note; //date note
    private String question_7;
    private String question_7_note; //important note
    private String question_8;
    private String question_8_note;     //commitment note



    /**
     * empty constructor
     */
    public Questions() {
        // Default constructor required for calls to DataSnapshot.getValue(Question.class)
    }

    /**
     * @param question_1 first question
     * @param question_2 second question
     * @param question_3 third question
     * @param question_3_note third question i.e. email note
     * @param question_4 fourth question
     * @param question_5 fifth question
     * @param question_5_note fifth question note
     * @param question_6 sixth question
     * @param question_6_note sixth question note
     * @param question_7 seventh question
     * @param question_7_note seventh question note
     * @param question_8 eight question
     * @param question_8_note eight question note
     */
    public Questions(String question_1, String question_2, String question_3,
                     String question_3_note, String question_4, String question_5,
                     String question_5_note, String question_6, String question_6_note,
                     String question_7, String question_7_note, String question_8, String question_8_note)
    {
        this.question_1 = question_1;
        this.question_2 = question_2;
        this.question_3 = question_3;
        this.question_3_note = question_3_note;
        this.question_4 = question_4;
        this.question_5 = question_5;
        this.question_5_note = question_5_note;
        this.question_6 = question_6;
        this.question_6_note = question_6_note;
        this.question_7 = question_7;
        this.question_7_note = question_7_note;
        this.question_8 = question_8;
        this.question_8_note = question_8_note;
    }

    public Questions(String question_1) {
        this.question_1 = question_1;
    }

    public String getQuestion_1() {
        return question_1;
    }

    public void setQuestion_1(String question_1) {
        this.question_1 = question_1;
    }

    public String getQuestion_2() {
        return question_2;
    }

    public void setQuestion_2(String question_2) {
        this.question_2 = question_2;
    }

    public String getQuestion_3() {
        return question_3;
    }

    public void setQuestion_3(String question_3) {
        this.question_3 = question_3;
    }

    public String getQuestion_3_note() {
        return question_3_note;
    }

    public void setQuestion_3_note(String question_3_note) {
        this.question_3_note = question_3_note;
    }

    public String getQuestion_4() {
        return question_4;
    }

    public void setQuestion_4(String question_4) {
        this.question_4 = question_4;
    }

    public String getQuestion_5() {
        return question_5;
    }

    public void setQuestion_5(String question_5) {
        this.question_5 = question_5;
    }

    public String getQuestion_5_note() {
        return question_5_note;
    }

    public void setQuestion_5_note(String question_5_note) {
        this.question_5_note = question_5_note;
    }

    public String getQuestion_6() {
        return question_6;
    }

    public void setQuestion_6(String question_6) {
        this.question_6 = question_6;
    }

    public String getQuestion_6_note() {
        return question_6_note;
    }

    public void setQuestion_6_note(String question_6_note) {
        this.question_6_note = question_6_note;
    }

    public String getQuestion_7() {
        return question_7;
    }

    public void setQuestion_7(String question_7) {
        this.question_7 = question_7;
    }

    public String getQuestion_7_note() {
        return question_7_note;
    }

    public void setQuestion_7_note(String question_7_note) {
        this.question_7_note = question_7_note;
    }

    public String getQuestion_8() {
        return question_8;
    }

    public void setQuestion_8(String question_8) {
        this.question_8 = question_8;
    }

    public String getQuestion_8_note() {
        return question_8_note;
    }

    public void setQuestion_8_note(String question_8_note) {
        this.question_8_note = question_8_note;
    }



}

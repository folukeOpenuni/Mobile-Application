package com.thehoneycombworks;

public class User {
    /**
     * all user attributes
     */
    private String firstName;
    private String lastName;
    private String email;
    private String habitToWorkOn;
    private String situationToTry;
    private String dateToWorkOnHabit;
    private String hindrance;
    private String toDo;

    /**
     * empty user constructor
     */
    public User() {
    }

    /**
     * constructor with all attributes
     * @param firstName user first name
     * @param lastName user surname
     * @param email email address of the user
     * @param habitToWorkOn the habit the user wants to work on
     * @param situationToTry specific situation to try and practice habit to work on
     * @param dateToWorkOnHabit date the user is going to work working on identify habit
     * @param hindrance What might stop the user or get in their way
     * @param toDo to dos to make sure there is no hindrance
     */
    public User(String firstName, String lastName, String email, String habitToWorkOn,
                String situationToTry, String dateToWorkOnHabit, String hindrance, String toDo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.habitToWorkOn = habitToWorkOn;
        this.situationToTry = situationToTry;
        this.dateToWorkOnHabit = dateToWorkOnHabit;
        this.hindrance = hindrance;
        this.toDo = toDo;
    }

    /**
     * method to get user first name
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * method to set user first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHabitToWorkOn() {
        return habitToWorkOn;
    }

    public void setHabitToWorkOn(String habitToWorkOn) {
        this.habitToWorkOn = habitToWorkOn;
    }

    public String getSituationToTry() {
        return situationToTry;
    }

    public void setSituationToTry(String situationToTry) {
        this.situationToTry = situationToTry;
    }

    public String getDateToWorkOnHabit() {
        return dateToWorkOnHabit;
    }

    public void setDateToWorkOnHabit(String dateToWorkOnHabit) {
        this.dateToWorkOnHabit = dateToWorkOnHabit;
    }

    public String getHindrance() {
        return hindrance;
    }

    public void setHindrance(String hindrance) {
        this.hindrance = hindrance;
    }

    public String getToDo() {
        return toDo;
    }

    public void setToDo(String toDo) {
        this.toDo = toDo;
    }
}

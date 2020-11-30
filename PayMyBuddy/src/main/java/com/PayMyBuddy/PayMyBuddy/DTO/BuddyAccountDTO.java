package com.PayMyBuddy.PayMyBuddy.DTO;

import java.time.LocalDate;

public class BuddyAccountDTO {

    private String emailBuddy;
    private String lastName;
    private String firstName;

    public String getEmailBuddy() {
        return emailBuddy;
    }

    public void setEmailBuddy(String emailBuddy) {
        this.emailBuddy = emailBuddy;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}

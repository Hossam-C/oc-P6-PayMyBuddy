package com.PayMyBuddy.PayMyBuddy.Entities;


import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="Relation")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="id_user")
    private int user;

    @Column(name="id_user_buddy")
    private int userBuddy;

    @Column(name="date_beginning")
    private LocalDate dateBeginning;

    @Column(name="date_end")
    private LocalDate dateEnd;

    public int getUser() {
        return user;
    }

    public void setUser(int id_user) {
        this.user = id_user;
    }

    public int getUserBuddy() {
        return userBuddy;
    }

    public void setUserBuddy(int id_user_buddy) {
        this.userBuddy = id_user_buddy;
    }

    public LocalDate getDateBeginning() {
        return dateBeginning;
    }

    public void setDateBeginning(LocalDate date_beginning) {
        this.dateBeginning = date_beginning;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate date_end) {
        this.dateEnd = date_end;
    }
}

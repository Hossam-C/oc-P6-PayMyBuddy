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

    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name="id_user_buddy")
    private User userBuddy;

    @Column(name="date_beginning", nullable=false)
    private LocalDate dateBeginning;

    @Column(name="date_end")
    private LocalDate dateEnd;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserBuddy() {
        return userBuddy;
    }

    public void setUserBuddy(User userBuddy) {
        this.userBuddy = userBuddy;
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

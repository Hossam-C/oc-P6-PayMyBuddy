package com.PayMyBuddy.PayMyBuddy.Entities;



import javax.persistence.*;

@Entity
@Table(name="Connexion")

public class Connexion {

    @Id
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @OneToOne
    @JoinColumn(name="id_user")
    private User user;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User id_user) {
        this.user = id_user;
    }
}

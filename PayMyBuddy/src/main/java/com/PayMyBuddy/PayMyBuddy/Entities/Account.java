package com.PayMyBuddy.PayMyBuddy.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="date_created")
    private LocalDate dateCreated;

    @Column(name="status",length = 20)
    private String status;

    @Column(name="balance")
    private double balance;

    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<ExternalMovements> externalMovements;

    @OneToMany(mappedBy = "issuer")
    private List<Movements> issuers;

    @OneToMany(mappedBy = "beneficiary")
    private List<Movements> beneficiaries;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ExternalMovements> getExternalMovements() {
        return externalMovements;
    }

    public void setExternalMovements(List<ExternalMovements> externalMovements) {
        this.externalMovements = externalMovements;
    }

    public List<Movements> getIssuers() {
        return issuers;
    }

    public void setIssuers(List<Movements> issuers) {
        this.issuers = issuers;
    }

    public List<Movements> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(List<Movements> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }
}

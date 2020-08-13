package com.PayMyBuddy.PayMyBuddy.Entities;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="External_movements")
public class ExternalMovements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="movement_type")
    private String movementType;

    @Column(name="issuer")
    private int issuer;

    @Column(name="beneficiary")
    private int beneficiary;

    @Column(name="date_movement")
    private LocalDate dateMovement;

    @Column(name="amount")
    private double amount;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name="id_account")
    private Account account;

    @ManyToOne
    @JoinColumn(name="id_external_account")
    private ExternalAccount externalAccount;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public int getIssuer() {
        return issuer;
    }

    public void setIssuer(int issuer) {
        this.issuer = issuer;
    }

    public int getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(int beneficiary) {
        this.beneficiary = beneficiary;
    }

    public LocalDate getDateMovement() {
        return dateMovement;
    }

    public void setDateMovement(LocalDate dateMovement) {
        this.dateMovement = dateMovement;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ExternalAccount getExternalAccount() {
        return externalAccount;
    }

    public void setExternalAccount(ExternalAccount externalAccount) {
        this.externalAccount = externalAccount;
    }
}

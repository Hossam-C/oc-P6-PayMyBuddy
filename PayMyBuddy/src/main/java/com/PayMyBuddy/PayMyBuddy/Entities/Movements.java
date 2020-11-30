package com.PayMyBuddy.PayMyBuddy.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Movements")
public class Movements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="movement_type",length = 15)
    private String movementType;

    @ManyToOne
    @JoinColumn(name="issuer")
    private Account issuer;

    @ManyToOne
    @JoinColumn(name="beneficiary")
    private Account beneficiary;

    @Column(name="date_movement")
    private LocalDate dateMovement;

    @Column(name="amount")
    private double amount;

    @Column(name="description",length = 100)
    private String description;

    @OneToMany(mappedBy = "movements")
    private List<Fees> feesList;

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

    public Account getIssuer() {
        return issuer;
    }

    public void setIssuer(Account issuer) {
        this.issuer = issuer;
    }

    public Account getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Account beneficiary) {
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

    public List<Fees> getFeesList() {
        return feesList;
    }

    public void setFeesList(List<Fees> feesList) {
        this.feesList = feesList;
    }
}

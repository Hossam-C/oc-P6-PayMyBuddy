package com.PayMyBuddy.PayMyBuddy.Entities;

import javax.persistence.*;

@Entity
@Table(name="Fees")
public class Fees {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="id_movement")
    private Movements movements;

    @ManyToOne
    @JoinColumn(name="id_external_movement")
    private ExternalMovements externalMovements;

    @Column(name="fees_type",length = 20)
    private String feesType;

    @Column(name="rate")
    private double rate;

    @Column(name="fees_amount")
    private double feesAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Movements getMovements() {
        return movements;
    }

    public void setMovements(Movements movements) {
        this.movements = movements;
    }

    public ExternalMovements getExternalMovements() {
        return externalMovements;
    }

    public void setExternalMovements(ExternalMovements externalMovements) {
        this.externalMovements = externalMovements;
    }

    public String getFeesType() {
        return feesType;
    }

    public void setFeesType(String feesType) {
        this.feesType = feesType;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(double feesAmount) {
        this.feesAmount = feesAmount;
    }
}

package com.PayMyBuddy.PayMyBuddy.Entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="External_account")
public class ExternalAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="IBAN")
    private String IBAN;

    @Column(name="BIC")
    private String BIC;

    @Column(name="bank_name")
    private String bankName;

    @Column(name="date_added")
    private LocalDate dateAdded;

    @ManyToOne(optional = false)
    @JoinColumn(name="id_user")
    private User user;

    @OneToMany(mappedBy = "externalAccount")
    private List<ExternalMovements> externalMovements;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
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
}

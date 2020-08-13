package com.PayMyBuddy.PayMyBuddy.Entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "adress")
    private String adress;

    @Column(name = "zip_code")
    private int zipCode;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "user")
    private List<ExternalAccount> externalAccounts;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;

    @ManyToMany
    @JoinTable(name = "Relation",
                joinColumns = {@JoinColumn(name = "id_user")},
                inverseJoinColumns = {@JoinColumn(name = "id_user_buddy")})
    private List<User> userList = new ArrayList<User>();

    @ManyToMany(mappedBy = "userList")
    private List<User> userBuddyList = new ArrayList<User>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ExternalAccount> getExternalAccounts() {
        return externalAccounts;
    }

    public void setExternalAccounts(List<ExternalAccount> externalAccounts) {
        this.externalAccounts = externalAccounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<User> getUserBuddyList() {
        return userBuddyList;
    }

    public void setUserBuddyList(List<User> userBuddyList) {
        this.userBuddyList = userBuddyList;
    }
}

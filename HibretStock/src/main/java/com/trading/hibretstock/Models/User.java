package com.trading.hibretstock.Models;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "user")
public class User
{
    public User(){    
    }

    @Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(unique = true, nullable = false)
    @Email
    @NotEmpty
    private String email ;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName ;

    @Column(nullable = false)
    private String lastName ;

    @Column
    private boolean verified ;

    @Column
    private String phoneNumber;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserBank userBankDetails;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private WatchList watchList;

    @OneToMany(mappedBy="user" , fetch = FetchType.EAGER)
    private Set<Transaction> transactions;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public UserBank getUserBankDetails() {
        return this.userBankDetails;
    }

    public void setUserBankDetails(UserBank userBankDetails) {
        this.userBankDetails = userBankDetails;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", transactions='" + getTransactions() + "'" +
            "}";
    }

    /**
     * @return the watchList
     */
    public WatchList getWatchList() {
        return watchList;
    }

    /**
     * @param watchList the watchList to set
     */
    public void setWatchList(WatchList watchList) {
        this.watchList = watchList;
    }

    /**
     * @return the verified
     */
    public boolean isVerified() {
        return verified;
    }

    /**
     * @param verified the verified to set
     */
    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}

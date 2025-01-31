package com.trading.hibretstock.Models;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "transaction")
public class Transaction
{
    public Transaction() {}
    
    @Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column
    @Temporal(TemporalType.DATE)
    private Date timestampdate;

    @Column
    private double totalPrice;

    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER, mappedBy = "transaction")
    private Set<Trade> allTrades;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestampdate() {
        return this.timestampdate;
    }

    public void setTimestampdate(Date timestampdate) {
        this.timestampdate = timestampdate;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<Trade> getAllTrades() {
        return this.allTrades;
    }

    public void setAllTrades(Set<Trade> allTrades) {
        this.allTrades = allTrades;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", timestampdate='" + getTimestampdate() + "'" +
            ", totalPrice='" + getTotalPrice() + "'" +
            "}";
    }


    
    
}

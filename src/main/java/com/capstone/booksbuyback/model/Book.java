package com.capstone.booksbuyback.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @NotNull
    @Size(min=3,max=15)
    private String name;

    @NotNull
    @Size(min=1,message="Author name cannot be empty")
    private String author;

    @Id
    @GeneratedValue
    private int id;
    public int getId() {
        return id;
    }


    @NotNull
    @DecimalMax("150.0") @DecimalMin("0.0")
    private double price;

    @ManyToOne
    private User user;


    @ManyToOne
    private Zip zip;


    // private List<Book> books = new ArrayList<>();

    public Book(){ }

    public Book(String name, String author) {
        this.name = name;
        this.author = author;
    }


    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Zip getZip() {  return zip;  }

    public void setZip(Zip zip) { this.zip = zip;
    }
}


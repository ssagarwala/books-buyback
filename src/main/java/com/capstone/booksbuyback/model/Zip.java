package com.capstone.booksbuyback.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Zip {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=5,max=5)
    private String zipNumber;

    @OneToMany
    @JoinColumn(name = "zip_id")
    private List<Book> books = new ArrayList<>();

    /* One Zip has many users
    @OneToMany
    @JoinClumn(name="zip_id")
    private List<User> users = new ArrayList<>();
     */


    public Zip(){ }

    public Zip(String zipNumber){
        this.zipNumber= zipNumber;
    }

    public int getId() {
        return id;
    }

    public String getZipNumber() {
        return zipNumber;
    }

    public void setZipNumber(String zipNumber) {
        this.zipNumber = zipNumber;
    }
    public List<Book> getBooks (){
        return books;
    }
}


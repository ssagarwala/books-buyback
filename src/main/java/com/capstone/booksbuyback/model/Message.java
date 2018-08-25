package com.capstone.booksbuyback.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Entity
public class Message {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private User user;

    private String sender;

    //@NotNull
    @Size(min=1,message="Msg cannot be empty")
    private String messageBody;

    //@NotNull
    @Size(min=1,message="title cannot be empty")
    private String title;

    public Message(){ }

    public Message(String messageBody,String title){
            this.messageBody= messageBody;
          this.title=title;
    }
    public String getSender() {
         return sender;
    }

    public void setSender(String sender) {
       this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}


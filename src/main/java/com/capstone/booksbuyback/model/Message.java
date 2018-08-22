package com.capstone.booksbuyback.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Message {

    public int getId() {
        return id;
    }

        @Id
        @GeneratedValue
        private int id;


    //Date date;
    @ManyToOne
    private User toUser;

    private String fromUser;

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
    public String getFromUser() {
         return fromUser;
    }

    public void setFromUser(String fromUser) {
       this.fromUser = fromUser;
    }


    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessage(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}


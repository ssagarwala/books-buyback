package com.capstone.booksbuyback.controller;


import com.capstone.booksbuyback.model.Book;
import com.capstone.booksbuyback.model.Message;
import com.capstone.booksbuyback.model.User;
import com.capstone.booksbuyback.model.data.BookDao;
import com.capstone.booksbuyback.model.data.MessageDao;
import com.capstone.booksbuyback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Sehar Sagarwala
 */
@Controller
@RequestMapping("message")
public class MessageController {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private UserService userService;

    @Autowired
    private BookDao bookDao;

    @RequestMapping("")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        User user  = userService.findUserByEmail(email);

        List<Message> messages = user.getMessages();
        model.addAttribute("messages", messages);
        model.addAttribute("title", "Messages For You");
        return "message/index";
    }

    @RequestMapping(value = "read/{msgId}", method = RequestMethod.GET)
    public String displayReadMsg(@PathVariable int msgId, Model model) {
        Optional<Message> msg1 = messageDao.findById(msgId);
        if (msg1.isPresent()) {
            Message message = msg1.get();
            model.addAttribute("message", message);
            model.addAttribute("title", "New Message");
            return "message/read";
        }
        return "message/index";
    }


    @RequestMapping(value = "add/{bookId}", method = RequestMethod.GET)
    public String displayAddMsgForm(@PathVariable int bookId,
                                    Model model, HttpServletRequest request) {

        Optional<Book> book1 = bookDao.findById(bookId);
        if (book1.isPresent()) {
            Book book = book1.get();

            User seller = book.getUser();

            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            User buyer = userService.findUserByEmail(email);
            session.setAttribute("sellerEmail",seller.getEmail());

            Message message = new Message();

            model.addAttribute("book",book.getName());
            model.addAttribute("seller",seller.getEmail());
            model.addAttribute("buyer", buyer.getEmail());
            model.addAttribute("message", message);
            return "message/add";
        }
        return "message/index";
    }




    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddMSgForm(@ModelAttribute @Valid Message msg,
                                      Errors error,Model model,HttpServletRequest request) {
        if (error.hasErrors()) {
            model.addAttribute("title", "Add Message");
            return "message/add";
        }

        HttpSession session = request.getSession();
        String sellerEmail =(String)session.getAttribute("sellerEmail");
        User seller = userService.findUserByEmail(sellerEmail);
        System.out.println("Seller email is "+ sellerEmail);
        msg.setUser(seller);

        String email = (String) session.getAttribute("email");
        msg.setFromUser(email);

        messageDao.save(msg);
        return "redirect:";
    }
}

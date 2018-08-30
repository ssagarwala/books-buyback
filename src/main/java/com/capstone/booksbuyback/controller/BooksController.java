package com.capstone.booksbuyback.controller;

import com.capstone.booksbuyback.model.Book;
import com.capstone.booksbuyback.model.User;
import com.capstone.booksbuyback.model.data.BookDao;
import com.capstone.booksbuyback.model.data.UserDao;
import com.capstone.booksbuyback.model.data.ZipDao;
import com.capstone.booksbuyback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Sehar Sagarwala
 */

@Controller
@RequestMapping("book")
public class BooksController {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserService userService;


    //@Autowired
    //private UserDao userDao;

    @Autowired
    private ZipDao zipDao;

    //add RequestMapping to configure the route to this
    //@ResponseBody allows text to be sent directly from the
    // controller method
    //RequestPAth: /book
    @RequestMapping("")
    public String index(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        //Integer userId = (Integer) session.getAttribute("id");
        String email = (String)session.getAttribute("email");
        User user  = userService.findUserByEmail(email);
        Iterable<User> books = user.getBooks();
        model.addAttribute("name", user.getName());
        model.addAttribute("books", books);
        model.addAttribute("title", "My Listed Books");
        return "book/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddBookForm(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        User user  = userService.findUserByEmail(email);
        model.addAttribute(new Book());
        model.addAttribute("name", user.getName());
        return "book/add";
    }

    /* public String processAddBookForm(@ModelAttribute @Valid Book book,Errors error,@RequestParam int userId, Model model){
     */

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddBookForm(@ModelAttribute @Valid Book book, Errors error,
                                     HttpServletRequest request, Model model) {
        if (error.hasErrors()) {
            model.addAttribute("title", "Add Book");
            return "book/add";
        }

        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        User user  = userService.findUserByEmail(email);
        book.setZip(user.getZip());
        book.setUser(user);
        bookDao.save(book);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveBookForm(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        User user  = userService.findUserByEmail(email);
        Iterable<User> books = user.getBooks();
        model.addAttribute("name", user.getName());
        model.addAttribute("books", books);
        model.addAttribute("title", "Remove Books");
        return "book/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveBookForm(@RequestParam int[] bookIds) {

        for (int bookId : bookIds) {
            bookDao.deleteById(bookId);
        }
        //leaving redirect empty, redirect to the same controller's index method.

        return "redirect:";
    }


    @RequestMapping(value = "edit/{bookId}", method = RequestMethod.GET)
    public String displayEditForm(@PathVariable int bookId, Model model, HttpServletRequest request) {
        Optional<Book> book1 = bookDao.findById(bookId);
        if (book1.isPresent()) {
            Book book = book1.get();
            HttpSession session = request.getSession();
            String email = (String)session.getAttribute("email");
            User user  = userService.findUserByEmail(email);
            model.addAttribute("name", user.getName());
            model.addAttribute("book", book);
            model.addAttribute("title", "Capstone Book Buyback");
            return "book/edit";
        }
        return "book/index";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String processEditForm(int bookId, String name, String author,double price) {
        Optional<Book> book1 = bookDao.findById(bookId);
        if (book1.isPresent()) {
            Book book = book1.get();
            book.setName(name);
            book.setAuthor(author);
            book.setPrice(price);
            bookDao.save(book);
            return "redirect:";
        }
        return "book/index";
    }

}
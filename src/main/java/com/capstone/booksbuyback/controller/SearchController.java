package com.capstone.booksbuyback.controller;

import com.capstone.booksbuyback.model.Book;
import com.capstone.booksbuyback.model.User;
import com.capstone.booksbuyback.model.Zip;
import com.capstone.booksbuyback.model.data.BookDao;
import com.capstone.booksbuyback.model.data.ZipDao;
import com.capstone.booksbuyback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("search")
public class SearchController {

    @Autowired
    private ZipDao zipDao;

    @Autowired
    private BookDao bookDao;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method=RequestMethod.GET)
    public String displaySearch (Model model,HttpServletRequest request){

        HttpSession session = request.getSession();
        String email = (String)session.getAttribute("email");
        User user  = userService.findUserByEmail(email);
        model.addAttribute("name", user.getName());
        model.addAttribute("books",bookDao.findAll());
        model.addAttribute("zips", zipDao.findAll());
        model.addAttribute("title", "Available Books");

        return "search/searchByZip";
    }

    @RequestMapping(value = "", method=RequestMethod.POST)
    public String processSearch(Model model,
                                    @RequestParam int zipId,
                                    String keyword,HttpServletRequest request) {
        Optional<Zip> zip1 = zipDao.findById(zipId);
        if (zip1.isPresent()) {
            Zip zip = zip1.get();

            List<Book> booksByZip = zip.getBooks();
            List<Book> booksByKeyword = new ArrayList<>();

            for (Book book : booksByZip) {
                if (caseInsensitiveSearch(book.getName(),keyword) ||
                        caseInsensitiveSearch(book.getAuthor(),keyword)) {
                    booksByKeyword.add(book);
                }
            }
            HttpSession session = request.getSession();
            String email = (String)session.getAttribute("email");
            User user  = userService.findUserByEmail(email);
            model.addAttribute("name", user.getName());
            model.addAttribute("books", booksByKeyword);
            model.addAttribute("zips", zipDao.findAll());
            model.addAttribute("title", "Books in zip   :"
                    + zip.getZipNumber() + " with Keyword :" + keyword);
            return "search/searchByZip";

        } else {
            model.addAttribute("books", bookDao.findAll());
            model.addAttribute("zips", zipDao.findAll());
            model.addAttribute("title", "All books in All zipcodes");
        }
        return "search/searchByZip";
    }
    public static boolean caseInsensitiveSearch(String findIn, String query) {

        String query_lower = query.toLowerCase();
        String[] findInArray = findIn.split(" ");
        boolean found = false;
        int i = 0;
        while (!found && findInArray.length > i) {
            String ar = findInArray[i];
            String ar_lower = ar.toLowerCase();
            if (query_lower.equals(ar_lower)) {
                found = true;
            }
            i++;
        }
        return found;
    }

}

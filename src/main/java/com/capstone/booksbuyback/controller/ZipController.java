package com.capstone.booksbuyback.controller;

import com.capstone.booksbuyback.model.Zip;
import com.capstone.booksbuyback.model.data.ZipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("zip")
public class ZipController {

    @Autowired
    private ZipDao zipDao;

 /*   @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("zips", zipDao.findAll());
        model.addAttribute("title", "zipCodes");
        return "zip/index";
    }
*/
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add ZipCode");
        model.addAttribute(new Zip());
        return "zip/add";
    }
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Zip zip, Errors errors){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add ZipCode");
            return "zip/add";
        }
        else {
            zipDao.save(zip);
            model.addAttribute("zipId", zip.getId());
            return "redirect:/user/add";
        }
    }
}

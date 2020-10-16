package com.srm.repg.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.srm.repg.form.FileParams;
import com.srm.repg.model.User;

@Controller
public class PasswordController {
	
	@GetMapping("/forgetPasswordEmail")
    public String forgetPasswordEmail(@ModelAttribute("fileParams") FileParams param,Model model) {
		System.out.println(param.getDateVal());
        return "forgetPassword";
    }
	
	 @GetMapping("/forgetPassword")
	    public String forgetPassword(Model model) {
	        model.addAttribute("userForm", new User());

	        return "forgetPassword";
	    }


}

package com.srm.repg.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.srm.repg.model.User;
import com.srm.repg.service.SecurityService;
import com.srm.repg.service.UserService;
import com.srm.repg.validator.UserValidator;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }
    

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
    	//return new ModelAndView("readAddress", "command", new FileParams());
    	return "index";
    }
    
    @GetMapping({"/excel"})
    public String excel(Model model) {
        return "readAddress";
    }
    
    @RequestMapping("/listUser")
    public String viewHomePage(Model model) {
        List<User> listUser =userService.getListOfUser();
        model.addAttribute("listUser", listUser);
         
        return "listUser";
    }
    
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductPage(@PathVariable(name = "id") long id) {
        ModelAndView mav = new ModelAndView("edit_user");
        User user = userService.getUser(id);
        mav.addObject("user", user);
        return mav;
    }
 	
    @GetMapping("/logout")
    public String logout(Model model) {
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

}

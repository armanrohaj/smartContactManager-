package com.durgesh.scm.controllers;

import com.durgesh.scm.entities.User;
import com.durgesh.scm.forms.UserForm;
import com.durgesh.scm.helpers.Message;
import com.durgesh.scm.helpers.MessageType;
import com.durgesh.scm.services.Impl.UserServiceImpl;
import com.durgesh.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {

    public final UserService userService;

    //Dependency Injection
    public PageController(UserService userService) {
        this.userService = userService;
    }

    @Value("${user.default-profile-picture-url}")
    private String defaultProfilePictureUrl;

    @GetMapping("/")
    public String index(){
        return "redirect:home";
    }
    @RequestMapping("/home")
    public String home(Model model){

        model.addAttribute("name", "Arman Rohaj");
        model.addAttribute("email", "arman@rohaj.com");
        model.addAttribute("githubrepo", "https://github.com/armoha");


        return "home";
    }

    @RequestMapping("/about")
    public String aboutPage(Model model){

        model.addAttribute("isLogin", "true");

        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(){
        return "services";
    }
    @RequestMapping("/contact")
    public String contactPage(){
        return "contact";
    }
    @RequestMapping("/login")
    public String loginPage(){
        return "login";
    }
    @RequestMapping("/register")
    public String signupPage(Model model){
        UserForm userForm = new UserForm();
        model.addAttribute("userForm",userForm);
        return "register";
    }

    @RequestMapping(value = "/do-register",method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session){
        // fetch form data
        // Validate form data
        if(rBindingResult.hasErrors()){
            return "register";
        }


        //Save to database

        //UserService
//        User user = User.builder()
//                .name(userForm.getName())
//                .email(userForm.getEmail())
//                .password(userForm.getPassword())
//                .about(userForm.getAbout())
//                .phoneNumber(userForm.getPhoneNumber())
//                .profilePic(defaultProfilePictureUrl)
//                .build();
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic(defaultProfilePictureUrl);

        User savedUser = userService.saveUser(user);
        System.out.println("user saved: " + savedUser);

        //Message= " Registration successful"
        //Add message here

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message",message);

        //redirect login page
        System.out.println(userForm.toString());
        return "redirect:/register";
    }
}

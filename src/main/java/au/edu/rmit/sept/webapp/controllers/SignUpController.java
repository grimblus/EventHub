package au.edu.rmit.sept.webapp.controllers;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpSession;


@Controller
public class SignUpController {

    private UserService userService;

    @Autowired
    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        //creates new user for details to be added to
        model.addAttribute("user", new User());

        //passes the 2 choosable permissions to the sign up page
        User.permissions[] perms = {User.permissions.ORGANISER, User.permissions.STUDENT};
        model.addAttribute("permissions", perms);

        return "signUp/index";
    }

    private boolean validate(User user, ArrayList<String> errors) {
        boolean foundError = false;
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        
        //regexes

        Pattern symbolsPattern = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]]");
        Pattern numbersPattern = Pattern.compile("[0-9]");
        Pattern emailPattern = Pattern.compile("[0-9a-zA-Z]+@[0-9a-zA-Z]+\\.+com");
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[^a-zA-Z0-9])(?=.*[A-Z]).+$");
        //firstName checks
        

        if (firstName.length() < 2) {
            
            errors.add("First name must be at least 2 characters");
            foundError = true;
        }

        if (symbolsPattern.matcher(firstName).find()) {
            errors.add("First name must not contain symbols (dashes and apostrophes allowed)");
            foundError = true;
        }

        if (numbersPattern.matcher(firstName).find()) {
            errors.add("First name must not contain numbers");
            foundError = true;
        }

        //last name checks
        if (lastName.length() < 2) {
            
            errors.add("Last name must be at least 2 characters");
            foundError = true;
        }

        if (symbolsPattern.matcher(lastName).find()) {
            errors.add("Last name must not contain symbols (dashes and apostrophes allowed)");
            foundError = true;
        }

        if (numbersPattern.matcher(lastName).find()) {
            errors.add("Last name must not contain numbers");
            foundError = true;
        }

        //email check

        if (!emailPattern.matcher(email).find()) {
            errors.add("Email must follow the format: x@x.com");
            foundError = true;
        }

        //password check

        if (password.length() < 8) {
            errors.add("Password must be at least 8 characters");
            foundError = true;
        }

        if (!passwordPattern.matcher(password).find()) {
            errors.add("Password must contain at least 1 symbol, number and uppercase letter");
            foundError = true;
        }

        return foundError;

    }

    private User inputSanitisation(User user) {
        //set firstname and last name to capitalise first letter
        user.setFirstName(Character.toUpperCase(user.getFirstName().charAt(0)) + user.getFirstName().substring(1));
        user.setLastName(Character.toUpperCase(user.getLastName().charAt(0)) + user.getLastName().substring(1));
        
       
        return user;
    }
    
    
    
    @PostMapping("/signUp") 
    public String submitForm(@ModelAttribute User user, HttpSession session, Model model) {
        //errors arraylist that is added to if error is found
        ArrayList<String> errors = new ArrayList<>();
        
        //pass errors to the validate function which will add found errors to it
        if (validate(user, errors)) {
            //add the errors if there are any
            model.addAttribute("errors", errors);
            User.permissions[] perms = {User.permissions.ORGANISER, User.permissions.STUDENT};
            model.addAttribute("permissions", perms);
            return "signUp/index";
        }
        //sanitise input
        user = inputSanitisation(user);

        //add the user to the database
        User createdUser = userService.addUser(user);

        //set the logged in user
        session.setAttribute("user", createdUser);

        return "redirect:/";
    }


}

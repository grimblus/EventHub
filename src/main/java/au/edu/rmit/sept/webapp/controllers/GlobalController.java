package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.entity.User;
import au.edu.rmit.sept.webapp.repository.UserRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalController {
    private final UserRepository userRepository;

    public GlobalController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("loggedIn")
    public boolean checkLoggedIn(Model model, HttpSession session) {

        if (session.getAttribute("user") == null) {
            return false;
        } 
        else {
            model.addAttribute("user", session.getAttribute("user"));
            return true;
        }
    }

    @ModelAttribute("isOrganiser")
    public boolean isOrganiser(HttpSession session) {
        if (session.getAttribute("user") != null) {
            User user =  (User) session.getAttribute("user");

            if (user.getPermissionLevel() == User.permissions.ORGANISER) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @ModelAttribute("isBanned")
    public boolean isBanned(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") != null) {
            User checkUser = userRepository.findUserByID(user.getId());
            if (checkUser != null) {
                boolean userBanned = checkUser.isBanned();
                user.setBanned(userBanned);
                return userBanned;
            }
        }
        return false;
    }
}
 
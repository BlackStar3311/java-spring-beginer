package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        List<User> arrUsers = this.userService.getAllUsersByEmail("11@gmal.com.vn.vn.vn");
        model.addAttribute("test", "test");
        return "hello";
    }

    // list Users
    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        model.addAttribute("listUser", this.userService.getAllUsers());
        return "admin/user/table-user";
    }

    // User detail
    @RequestMapping("/admin/user/{id}")
    public String getlUserDetaiPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("user", this.userService.getUserByID(id).orElse(null));
        return "admin/user/show";
    }

    // form create
    @RequestMapping("/admin/user/create")
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // handle create user
    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        userService.handleSaveUser(newUser);
        return "redirect:/admin/user";
    }

    // form update user
    @RequestMapping(value = "/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable Long id) {
        model.addAttribute("newUser", this.userService.getUserByID(id).orElse(null));
        return "/admin/user/update";
    }

    // handle update user
    @RequestMapping(value = "/admin/user/update", method = RequestMethod.POST)
    public String updateUserPage(Model model, @ModelAttribute("newUser") User newUser) {
        userService.handleUpdateUser(newUser);
        return "redirect:/admin/user";
    }
}

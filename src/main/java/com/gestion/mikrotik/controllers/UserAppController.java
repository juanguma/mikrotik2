package com.gestion.mikrotik.controllers;

import com.gestion.mikrotik.entities.UserApp;
import com.gestion.mikrotik.entities.UserRole;
import com.gestion.mikrotik.services.UserAppService;
import com.gestion.mikrotik.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserAppController {
    @Autowired
    UserAppService userAppService;
    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/addusers")
    public String addusers (Model model){
        UserApp newUser = new UserApp();
        List<UserRole> rolesList = this.userRoleService.getRoles();
        model.addAttribute("rolesList",rolesList);
        model.addAttribute("newUser",newUser);


        return "addusers";
    }

    @PostMapping("/adduser")
    public String adduser(UserApp newUser){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedcont = bCryptPasswordEncoder.encode(newUser.getPasswd());
        newUser.setPasswd(hashedcont);
        this.userAppService.createUser(newUser);
        return "redirect:/admin/addusers";
    }


}

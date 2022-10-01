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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserAppController {
    @Autowired
    UserAppService userAppService;
    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/admin/addusers")
    public String addusers (Model model){
        UserApp newUser = new UserApp();

        List<UserRole> rolesList = this.userRoleService.getRoles();
        model.addAttribute("rolesList",rolesList);
        model.addAttribute("newUser",newUser);


        return "addusers";
    }

    @PostMapping("/admin/adduser")
    public String adduser(UserApp newUser){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedcont = bCryptPasswordEncoder.encode(newUser.getPasswd());
        newUser.setPasswd(hashedcont);
        newUser.setStatus(true);
        this.userAppService.createUser(newUser);
        return "redirect:/admin/addusers";
    }

    @PostMapping("/admin/updateuser/")
    public String updateuser(UserApp newUser){
        UserApp currentUser = this.userAppService.findByUserId(newUser.getId());
        currentUser.setStatus(newUser.isStatus());
        currentUser.setName(newUser.getName());
        currentUser.setEmail(newUser.getEmail());
        currentUser.setRol(newUser.getRol());
        if(newUser.getPasswd().equals(""))
        {
            System.out.println("vacio");

        }else{
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashedcont = bCryptPasswordEncoder.encode(newUser.getPasswd());
            currentUser.setPasswd(hashedcont);
            System.out.println(newUser.getPasswd());
            System.out.println(currentUser.getPasswd());
            newUser.setStatus(true);
        }

        this.userAppService.createUser(currentUser);
        System.out.println(currentUser.getPasswd()+"guardadad");
        return "redirect:/admin/addusers";
    }

    @GetMapping("/admin/listusers")
    public String listusers(Model model){
        List<UserApp> userList = this.userAppService.getAppUsers();
        model.addAttribute("userList",userList);

        return "listusers";

    }

    @GetMapping("/admin/edituser/{id}")
    public String edituser(Model model, @PathVariable int id) {
        UserApp currentUser = this.userAppService.findByUserId(id);
        List<UserRole> rolesList = this.userRoleService.getRoles();
        model.addAttribute("rolesList",rolesList);
        model.addAttribute("currentUser", currentUser);

        return "updateuser";
    }

    @GetMapping("/denegado")
    public String denegado (){
        return "plantilla";
    }


}

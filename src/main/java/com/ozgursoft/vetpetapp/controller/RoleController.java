package com.ozgursoft.vetpetapp.controller;

import com.ozgursoft.vetpetapp.model.Role;
import com.ozgursoft.vetpetapp.model.User;
import com.ozgursoft.vetpetapp.services.RoleService;
import com.ozgursoft.vetpetapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    public RoleController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/roles")
    public String getStates(Model model) {
        List<Role> roleList = roleService.getRoles();
        model.addAttribute("roles", roleList);
        return "role";
    }

    @GetMapping("/security/user/Edit/{id}")
    public String roleManage(@PathVariable Integer id, Model model)
    {
        User user=userService.getUser(id);
        model.addAttribute("user",user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "userEdit";

    }

    @PostMapping("/roles/addNew")
    public String addNew(Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }

    @GetMapping("roles/findById")
    @ResponseBody
    public Role findById(int id) {
        return roleService.findById(id);
    }

    @GetMapping(value="/roles/update")
    public String update(Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }

    @GetMapping("/roles/update/{roleId}")
    public String showFormForUpdate(@PathVariable(value = "roleId") Integer roleId, Model model) {
        Role role=roleService.findById(roleId);

        model.addAttribute("role", role);
        return "updateRole";
    }

    @PostMapping("updateRole")
    public String updateRole(@ModelAttribute("role") Role role) {
        roleService.save(role);
        return "redirect:/roles";
    }


    @GetMapping(value="/roles/delete")
    public String delete(Integer id) {
        roleService.delete(id);
        return "redirect:/roles";
    }

    @GetMapping("/security/role/assign/{userId}/{roleId}")
    public String assignRole(@PathVariable Integer userId,
                             @PathVariable Integer roleId){
        roleService.assignRole(userId, roleId);
        return "redirect:/security/user/Edit/"+userId;
    }

    @GetMapping("/security/role/unis/{userId}/{roleId}")
    public String unAssignRole(@PathVariable Integer userId,
                               @PathVariable Integer roleId){
        roleService.unAssignRole(userId, roleId);
        return "redirect:/security/user/Edit/"+userId;
    }
}

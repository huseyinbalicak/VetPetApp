package com.ozgursoft.vetpetapp.services;

import com.ozgursoft.vetpetapp.repository.RoleRepository;
import com.ozgursoft.vetpetapp.repository.UserRepository;
import com.ozgursoft.vetpetapp.model.Role;
import com.ozgursoft.vetpetapp.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public void save(Role role) {

        roleRepository.save(role);
    }

    public Role findById(int id) {
        return roleRepository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }

    public void assignRole(Integer userId, Integer roleId) {
        User user = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);
        assert user != null;
        Set<Role> userRoles = user.getRoles();
        userRoles.add(role);
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    public void unAssignRole(Integer userId, Integer roleId) {
        User user = userRepository.findById(userId).orElse(null);
        assert user != null;
        Set<Role> userRoles = user.getRoles();
        userRoles.removeIf(x -> Objects.equals(x.getId(), roleId));
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    public Set<Role> getUserRoles(User user) {
        return user.getRoles();
    }

    public List<Role> getUserNotRoles(User user) {
        return roleRepository.getUserNotRoles(user.getId());
    }


}

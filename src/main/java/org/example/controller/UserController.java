
package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.annotation.PostConstruct;

@Named("userController")
@ViewScoped
public class UserController implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserService userService;

    private List<User> users;
    private User user;

    @PostConstruct
    public void init() {
        users = userService.findAll();
        user = new User();
    }

    public void prepareNew() {
        user = new User();
    }

    public void save() {

        if (user.getId() == null) {

            if (user.getRegisteredAt() == null) {
                user.setRegisteredAt(LocalDate.now());
            }

            userService.create(user);

        } else {

            userService.update(user);

        }

        users = userService.findAll();
        user = new User();
    }

    public void edit(Long id) {
        user = userService.findById(id);
    }

    public void delete(Long id) {

        userService.delete(id);

        users = userService.findAll();
        user = new User();
    }

    public void cancel() {
        user = new User();
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

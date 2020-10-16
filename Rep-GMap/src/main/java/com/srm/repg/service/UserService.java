package com.srm.repg.service;

import java.util.List;

import com.srm.repg.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
    
    List<User>getListOfUser();
    
    User getUser(Long id);
}
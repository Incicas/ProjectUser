package com.example.ProjectUser.service;

import com.example.ProjectUser.api.UserEntity;
import com.example.ProjectUser.dao.UserRepository;
import com.example.ProjectUser.exception.UserAlreadyExistException;
import com.example.ProjectUser.exception.UserNotFoundException;
import com.example.ProjectUser.exception.WeakPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity getUserById(long id) throws UserNotFoundException {
        UserEntity user = userRepository.getById(id);
        if (user == null){
            throw new UserNotFoundException("The user with id "+id+" does not exist.");
        }
        return user;
    }

    public UserEntity createUser(UserEntity userCreated) throws UserAlreadyExistException, WeakPasswordException {
        if (checkUser(userCreated)){
            throw new UserAlreadyExistException("This user already exist.");
        }
        if (!checkPassword(userCreated.getPassword())){
            throw new WeakPasswordException("The password must be at least 8 character, have digits and letters.");
        }
        return userRepository.save(userCreated);

    }

    private boolean checkUser(UserEntity userEntity){
        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users){
            if (userEntity.equals(user)){
                return true;
            }
        }
        return false;
    }

    private boolean checkPassword(String password) throws WeakPasswordException {
        if (password.length() < 8){
            return false;
        }
        boolean isLetter = false;
        boolean isDigit = false;

        for (int i=0; i<password.length(); i++){
            if (Character.isDigit(password.charAt(i))){
                isDigit = true;
            }
            if (Character.isLetter(password.charAt(i))){
                isLetter = true;
            }
        }
        return isDigit && isLetter;
    }
}

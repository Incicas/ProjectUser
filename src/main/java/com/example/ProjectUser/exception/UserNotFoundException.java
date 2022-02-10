package com.example.ProjectUser.exception;

import net.bytebuddy.asm.Advice;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String msg){
        super(msg);
    }
}

package com.example.studygroup.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)

public class Emailexception extends RuntimeException {


    public Emailexception(String email) {
        super("이미 존재하는 이메일: " + email);
    }

}

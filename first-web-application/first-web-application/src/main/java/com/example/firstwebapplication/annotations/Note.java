package com.example.firstwebapplication.annotations;

public @interface Note {
    String message() default "Leave a message here";
}

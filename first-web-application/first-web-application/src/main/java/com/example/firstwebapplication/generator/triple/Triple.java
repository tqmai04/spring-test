package com.example.firstwebapplication.generator.triple;


public class Triple {

    private String subject;
    private String predicate;
    private String object;


    public Triple(String subject, String predicate, String object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }


    public String getSubject() {
        return subject;
    }


    public String getPredicate() {
        return predicate;
    }

    public String getObject() {
        return object;
    }

    public String toString() {
        return String.format("Subject: %s | Predicate: %s | Object: %s",
                subject, predicate, object);
    }

}

package com.example.firstwebapplication.generator.constraints;

import java.util.Arrays;
import java.util.LinkedList;


public class Constraint {

    private String predicate;
    private int mutationOption;
    private LinkedList<String> mutationParameters;


    public Constraint(String predicate, int mutationOption, String... mutationParameters) {
        this.predicate = predicate;
        this.mutationOption = mutationOption;
        this.mutationParameters = new LinkedList<>(Arrays.asList(mutationParameters));
    }


    public String getPredicate() {
        return predicate;
    }


    public int getMutationOption() {
        return mutationOption;
    }


    public void appendMutationParameter(String newParameter) {
        this.mutationParameters.add(newParameter);
    }


    public String[] getMutationParameters() {
        return mutationParameters.toArray(new String[mutationParameters.size()]);
    }


    public void removeMutationParameter(String parameter) {
        this.mutationParameters.remove(parameter);
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.predicate).append(" | ");
        stringBuilder.append(this.mutationOption).append(" | ");
        for(String parameter : this.mutationParameters) {
            stringBuilder.append(parameter);

            if(!parameter.equals(this.mutationParameters.getLast())) {
                stringBuilder.append(" | ");
            }
        }

        return stringBuilder.toString();
    }

}

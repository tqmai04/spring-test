package com.example.firstwebapplication.generator.constraints;

public class ConstraintCollectionFactory {

    public static ConstraintCollection createConstraintCollection(String[][] constraintsParameters) {
        ConstraintCollection constraintCollection = new ConstraintCollection();

        for(String[] currentConstraintParameters : constraintsParameters) {
            String predicate = currentConstraintParameters[0];
            int option = Integer.parseInt(currentConstraintParameters[1]);
            String[] mutationParameters = new String[currentConstraintParameters.length - 2];
            System.arraycopy(currentConstraintParameters, 2,
                    mutationParameters, 0, mutationParameters.length);
            constraintCollection.addConstraint(new Constraint(predicate, option, mutationParameters));
        }

        return constraintCollection;
    }


}

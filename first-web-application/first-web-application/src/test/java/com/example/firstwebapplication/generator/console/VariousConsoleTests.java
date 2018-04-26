package com.example.firstwebapplication.generator.console;

import com.example.firstwebapplication.generator.constraints.Mutation;
import com.example.firstwebapplication.generator.utilities.JsonResponse;
import org.junit.jupiter.api.Disabled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


@Disabled
public class VariousConsoleTests {


    public static void main(String[] args) {
        testBuildingJsonResponse();
    }


    private static void testBuildingJsonResponse() {
        try {
            JsonResponse response = new JsonResponse();
            response.addResponse("predicates");
            response.addArrayOfDataLines(new String[]{
                    "zip",
                    "name",
                    "age"
            });
            System.out.println(response.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void printMutationOptions() {
        String[][] options = Mutation.getAllMutationOptions();

        for(String[] option : options) {
            System.out.printf("%-10s%s\n", option[0], option[1]);
        }

    }


    private static void testStartFuseki() {
        try {
            Runtime rt = Runtime.getRuntime();
            String[] commands = {"cmd.exe","dir"};
            Process proc = rt.exec(commands);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkCurrentWorkingDirectory() {
        System.out.println(System.getProperty("user.dir"));
    }





    private static void printList(Object[] list) {
        for(Object o : list) {
            System.out.println(o.toString());
        }
        System.out.printf("> Total of %d elements\n", list.length);
    }


    private static void removeDuplicatedArrayListElement() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("A");

        System.out.println("Duplicated:");
        printList(arrayList.toArray());

        arrayList.remove("A");
        System.out.println("No Duplicated:");
        printList(arrayList.toArray());

    }

}

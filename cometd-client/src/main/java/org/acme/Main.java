package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Main {

    public static void main(String ... args) {
        System.out.println("Running main method");
        Log.warn("Nothing to do");
    }
}
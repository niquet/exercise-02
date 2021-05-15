package services;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Node extends Thread {

    private boolean exit;

    public Node() {

        // TODO
        LinkedBlockingQueue<Integer> inbox = new LinkedBlockingQueue<>();
        ArrayList<String> history = new ArrayList<>();

        this.exit = false;

    }

    public void run() {

        while(!this.exit) {

            // TODO
            System.out.println("Thread ".concat(String.valueOf(this.getId())).concat(" is running."));

        }

    }

    public void interrupt() {

        this.exit = true;

    }

}

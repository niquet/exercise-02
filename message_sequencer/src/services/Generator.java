package services;

import utilities.Message;

import java.util.ArrayList;
import java.util.Random;

public class Generator{

    private int numberOfMessages = 0;

    public Generator(int numberOfMessages) {

        this.numberOfMessages = numberOfMessages;

    }

    public void generateMessages(ArrayList<Node> nodeThreads) {

        for (int i = 0; i < this.numberOfMessages; i++) {

            int chosenThread = getRandomNumberInRange(0,nodeThreads.size()-1);
            int payload = (int) (Math.random() * 100000);

            nodeThreads.get(chosenThread).inbox.add(new Message(payload));

        }

    }
    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}

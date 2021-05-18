package services;

import utilities.Message;

import java.util.ArrayList;

public class Generator{

    private int numberOfMessages = 0;

    public Generator(int numberOfMessages) {

        this.numberOfMessages = numberOfMessages;

    }

    public void generateMessages(ArrayList<Node> nodeThreads) {

        for (int i = 0; i < this.numberOfMessages; i++) {

            int chosenThread = (int) (Math.random() * nodeThreads.size());
            int payload = (int) (Math.random() * 100000);

            nodeThreads.get(chosenThread).inbox.add(new Message(payload));

        }

    }

}

package services;

import utilities.Message;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Generator{

    private int numberOfMessages = 0;
    private ReentrantLock lock;

    public Generator(int numberOfMessages, ReentrantLock lock) {

        this.numberOfMessages = numberOfMessages;
        this.lock =lock;
    }

    public void generateMessages(ArrayList<Node> nodeThreads) {

        for (int i = 0; i < this.numberOfMessages; i++) {
            lock.lock();
            try {
                int chosenThread = (int) (Math.random() * nodeThreads.size());
                int payload = (int) (Math.random() * 100000);

                nodeThreads.get(chosenThread).inbox.add(new Message(payload));
            }finally {
                lock.unlock();
            }


        }

    }

}

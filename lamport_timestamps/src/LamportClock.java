import services.Generator;
import services.Node;
import utilities.Message;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.locks.ReentrantLock;

public class LamportClock {

    private ArrayList<Node> nodeThreads;
    private Generator generator;
    private ReentrantLock lock = new ReentrantLock();

    public LamportClock(int numberOfThreads) {

        this.generator = null;
        this.nodeThreads = new ArrayList<>();

        for(int i = 0; i < numberOfThreads; i++) {
            Node node = new Node(lock);
            this.nodeThreads.add(node);
        }

    }

    public void runNodeThreads() {

        for (Node node:this.nodeThreads) {
            node.registerNodes(nodeThreads);    //registers the sequencer with the node before start
            node.start();                   //starts the thread !!!DO NOT CALL node.run()!!!!
        }

    }

    public void stopNodeThreads() {

        boolean haveFinished = false;

        while (!haveFinished) {
           haveFinished = true;

            for (Node node : this.nodeThreads) {
                if (!node.inbox.isEmpty()) {
                    haveFinished = false;
                }
            }
        }


        for (Node node:this.nodeThreads) {
            node.interrupt();               //sets boolean in threads so the terminate
        }
    }

    public static void main(String[] args) {

        int numberOfThreads = Integer.parseInt(args[0]);
        int numberOfMessages = Integer.parseInt(args[1]);

        LamportClock lamportClock = new LamportClock(numberOfThreads);

        lamportClock.generator = new Generator(numberOfMessages,lamportClock.lock);


        // starts and registers nodes, then starts thread
        lamportClock.runNodeThreads();
        lamportClock.generator.generateMessages(lamportClock.nodeThreads);
        // lets main thread sleep before messengers are terminated


        // before shutdown every msg should have been printed once to the console for ever thread
        // shutdown all running threads
        lamportClock.stopNodeThreads();

        /** for i in ./*; do diff -q "$i" 15.txt; done */

    }

}

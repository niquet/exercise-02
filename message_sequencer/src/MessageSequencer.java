import services.Generator;
import services.Node;
import services.Sequencer;
import utilities.Message;

import java.util.ArrayList;
import java.util.Timer;

public class MessageSequencer {

    private ArrayList<Node> nodeThreads;
    private Generator generator;
    private Sequencer sequencer;

    public MessageSequencer(int numberOfThreads) {

        this.generator = new Generator();
        this.sequencer = new Sequencer();
        this.nodeThreads = new ArrayList<>();

        for(int i = 0; i < numberOfThreads; i++) {
            Node node = new Node();
            this.nodeThreads.add(node);
        }

    }

    public void runNodeThreads() {

        for (Node node:this.nodeThreads) {
            node.registerSeq(sequencer);    //registers the sequencer with the node before start
            node.start();                   //starts the thread !!!DO NOT CALL node.run()!!!!
        }

    }

    public void stopNodeThreads() {

        for (Node node:this.nodeThreads) {
            node.interrupt();               //sets boolean in threads so the terminate
        }

    }

    public static void main(String[] args) {

        int numberOfThreads = Integer.parseInt(args[0]);
        MessageSequencer messageSequencer = new MessageSequencer(numberOfThreads);
        //registers nodes in sequencer
        messageSequencer.sequencer.registerNodes(messageSequencer.nodeThreads);

        for (int i=0; i<3; i++){
            messageSequencer.nodeThreads.get(i).inbox.add(new Message(i));  //creates messages inside the inbox of a node
        }
        //starts and registers sequencer, then starts thread
        messageSequencer.sequencer.start();
        messageSequencer.runNodeThreads();

        //lets main thread sleep before messangers are terminated
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //before shutdown everey msg should have been printed once to the console for ever thread
        //shutdown all running threads
        messageSequencer.stopNodeThreads();
        messageSequencer.sequencer.interrupt();

    }

}

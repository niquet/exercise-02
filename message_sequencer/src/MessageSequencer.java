import services.Generator;
import services.Node;
import services.Sequencer;

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
            node.run();
        }

    }

    public void stopNodeThreads() {

        for (Node node:this.nodeThreads) {
            node.interrupt();
        }

    }

    public static void main(String[] args) {

        int numberOfThreads = Integer.parseInt(args[0]);
        MessageSequencer messageSequencer = new MessageSequencer(numberOfThreads);

        messageSequencer.runNodeThreads();

        for(int i = 0; i < 10; i++) {
            // do nothing
        }

        messageSequencer.stopNodeThreads();

    }

}

package services;

import utilities.Logger;
import utilities.Message;
import utilities.MessageComp;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Node extends Thread {

    private boolean exit;                       //boolean to flag therminate
    public LinkedBlockingQueue<Message> inbox;  //inbox, public so other threads/methods can add to inbox without calling the thread
    private ArrayList<Node> nodeThreads;
    private int clock;
    private ReentrantLock lock;
    ArrayList<Message> history = new ArrayList<>();

    public Node(ReentrantLock lock) {

        // TODO
        this.lock=lock;
        this.inbox = new LinkedBlockingQueue<>();
        this.exit = false;
        this.clock=0;

    }
    private Message poll(){
        Message p = this.inbox.poll();
        return p;
    }
    public void registerNodes(ArrayList<Node> nodeThreads){
        this.nodeThreads=nodeThreads;
    }
    @Override
    public void run() {

        System.out.println("Thread ".concat(String.valueOf(this.getId())).concat(" is running."));

        while(!this.exit) {
            Message m = poll();
            if(m==null){
                continue;
            }
            lock.lock();
            try {
                if(m.ext){  //case recieved external message
                    clock++;
                    m.transform((int) this.getId(),clock);  //transforms ext to internal msg
                    receive(m);
                    broadcast(m);
                }else{      //case internal msg
                    receive(m);
                    clock++;

                }
            }finally {
                lock.unlock();
            }


        }

    }
    //saves message
    private void saveToHistory(Message m){
        this.history.add(m);
    }
    private void broadcast(Message m){
        for (Node node:nodeThreads) {
            if (!node.equals(this)) {
                node.inbox.add(m);
            }
        }
    }
    private void receive(Message m){
        Integer l;
        if (clock >= m.getCount()){
            l=clock;
            m.setCount(l);
        }
        if (clock < m.getCount()){
            l=m.getCount();
        }
        saveToHistory(m);                          //saves internal msg
    }
    @Override
    public void interrupt() {
        this.history.sort(new MessageComp());
        ArrayList<String> SHistory=new ArrayList<>();
        for (Message m:history) {
            SHistory.add(m.toString());
        }
        Logger logger = new Logger();
        String nodeID = String.valueOf(this.getId());
        logger.writeHistory(nodeID, SHistory);

        this.exit = true;

    }

}

package services;

import utilities.Message;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Sequencer extends Thread{

    public LinkedBlockingQueue<Message> inbox;  //inbox, public so other threads/methods can add to inbox without calling the thread
    private ArrayList<Node> nodeThreads;        //refrence to all nodes, so broadcasting can be done
    private int inc = 0;                        //internal increment to give msges their ID
    private boolean exit = false;                       //boolean to terminate run loop

    public Sequencer() {
        //initialize inbox
        this.inbox = new LinkedBlockingQueue<>();
    }

    //use Override keyword when overriding existing methods of "Thread"
    @Override
    public void run(){
        System.out.println("Sequencer_Thread ".concat(String.valueOf(this.getId())).concat(" is running."));
        while(!exit){
            Message m =poll();  //poll inbox
            //if there are no new mesges skips remaining loop
            if(m==null){
                continue;
            }
            if(m.ext){
                //status msg when extermal msg somehow gets to the sequencer
                System.out.println("Sequencer recieved external msg");
            }else{
                //adjust counter and broadcast msg
                m.setCount(inc);
                inc++;
                broadcast(m);
            }
        }
    }
    private Message poll(){ //return null when inbox is empty
        //polls head of inbox
        Message p = this.inbox.poll();
        return p;
    }
    private void broadcast(Message m){
        //adds m to the inbox of all node threads
        for (Node node:this.nodeThreads) {
            node.inbox.add(m);
        }
    }
    public void registerNodes(ArrayList<Node> nodeThreads){
        this.nodeThreads=nodeThreads;
    }
    @Override
    public void interrupt() {

        this.exit = true;

    }
}

package services;

import utilities.Message;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Node extends Thread {

    private boolean exit;                       //boolean to flag therminate
    public LinkedBlockingQueue<Message> inbox;  //inbox, public so other threads/methods can add to inbox without calling the thread
    private Sequencer seq;                      //refrence to sequencer to enable relaying of internal msg for broadcasting
    ArrayList<String> history = new ArrayList<>();

    public Node() {

        // TODO
        this.inbox = new LinkedBlockingQueue<>();
        this.exit = false;

    }
    private Message poll(){
        Message p = this.inbox.poll();
        return p;
    }
    public void registerSeq(Sequencer seq){
        this.seq=seq;
    }
    @Override
    public void run() {

        System.out.println("Thread ".concat(String.valueOf(this.getId())).concat(" is running."));

        while(!this.exit) {
            Message m = poll();
            if(m==null){
                continue;
            }
            if(m.ext){  //case recieved external message
                m.transform((int) this.getId(),0);  //transforms ext to internal msg
                seq.inbox.add(m);                          //sends transformed msg to sequencer inbox
            }else{      //case internal msg
                save(m);                                   //saves internal msg
            }

        }

    }
    //saves message
    private void save(Message m){
        //TODO
        System.out.println(m.toString());
    }
    @Override
    public void interrupt() {

        this.exit = true;

    }

}

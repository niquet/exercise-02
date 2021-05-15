package services;

import utilities.Message;

import java.util.concurrent.LinkedBlockingQueue;

public class Sequencer extends Thread{

    private LinkedBlockingQueue<Message> inbox;

    public Sequencer() {}

}

package utilities;

import java.util.Comparator;

public class MessageComp implements Comparator<Message> {
    @Override
    public int compare(Message o1, Message o2) {
        if(o1.getCount()==o2.getCount()){
            return o1.nodeID-o2.nodeID;
        }else{
            return o1.getCount()-o2.getCount();
        }
    }
}

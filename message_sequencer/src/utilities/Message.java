package utilities;

public class Message {

    private String nodeID;
    private Integer counter;

    public Message(String nodeID, Integer counter) {

        this.nodeID = nodeID;
        this.counter = counter;

    }

    @Override
    public String toString() {

        // Returns message of form <nodeID>_<counter>
        return this.nodeID.concat("_").concat(counter.toString());

    }

}

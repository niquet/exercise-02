package utilities;

public class Message {

    private Integer nodeID;    //not in use/ additional info for demosntration
    private Integer counter;   //not in use/ additional info for demosntration
    private Integer payload;   //msg content
    public boolean ext = true; //flag if msg is external

    //constructor for internal msg
    public Message(Integer nodeID, Integer counter, Integer payload) {

        this.nodeID = nodeID;
        this.counter = counter;
        this.payload = payload;
        this.ext = false;

    }
    //costructor for external msg
    public Message(Integer payload){
        this.payload = payload;
    }
    //changes external msg to internal msg
    public void transform(Integer nodeID, Integer counter){
        this.nodeID = nodeID;
        this.counter = counter;
        //IMPORTANT remember to set flag
        this.ext = false;
    }
    public void setCount(Integer inc){
        this.counter=inc;
    };
    @Override
    public String toString() {

        // Returns message of form <payload>_<nodeID>_<counter>
        return this.payload.toString().concat("_").concat(this.nodeID.toString()).concat("_").concat(counter.toString());

    }

}

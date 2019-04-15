package evoparsons.repo;

import java.io.Serializable;

public class Instructor implements Serializable {

    private static final long serialVersionUID = 1L;
    public final String iid;
    public final String isig; 
    public Instructor(String iid, String isig){
        this.iid = iid; 
        this.isig = isig;
    }
}
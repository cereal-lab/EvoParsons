package evoparsons.broker;

import java.io.Serializable;

import evoparsons.rmishared.Auth;
import evoparsons.rmishared.Stats;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    public final Auth auth;
    public final Stats stats;
    public Student(Auth auth, Stats stats) {
        this.auth = auth;
        this.stats = stats;
    }
}
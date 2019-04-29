package evoparsons.broker;

import java.io.Serializable;

import evoparsons.rmishared.Auth;
import evoparsons.rmishared.Stats;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private Auth auth;
    private Stats stats;
    public Auth getAuth() {
        return auth;
    }
    public Stats getStats() {
        return stats;
    }
    public void setAuth(Auth auth) {
        this.auth = auth;
    }
    public void setStats(Stats stats) {
        this.stats = stats;
    }    
    public Student(){}        
    public Student(Auth auth, Stats stats) {
        this.auth = auth;
        this.stats = stats;
    }
}
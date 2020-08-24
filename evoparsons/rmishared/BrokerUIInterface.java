package evoparsons.rmishared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import evoparsons.broker.Student;

public interface BrokerUIInterface extends Remote 
{
    // Methods for the ITS to use
    public ParsonsPuzzle getParsonsPuzzle(String sid) throws RemoteException;
    public Student authenticateStudent(String sid, String ssig, String skey) throws RemoteException;
    public void setParsonsEvaluation(ParsonsEvaluation data) throws RemoteException;
    //public int recordAttempt(String sid, String puzzleId) throws RemoteException;
    public Stats getStudentStats(String sid) throws RemoteException;
    public Map<String, List<Stats>> getStudentStats(String iid, String isig, List<String> ssig, Set<String> ssid) throws RemoteException;
}

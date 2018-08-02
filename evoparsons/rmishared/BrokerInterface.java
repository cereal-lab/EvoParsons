package evoparsons.rmishared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BrokerInterface extends Remote 
{
    // Methods for the ITS to use
    public ParsonsPuzzle getParsonsPuzzle(int userId) throws RemoteException;
    public int getStudentID(String login) throws RemoteException;
    public void setParsonsEvaluation(ParsonsEvaluation data) throws RemoteException;
    public Stats getStudentStats(int studentId) throws RemoteException;
}

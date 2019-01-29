package evoparsons.broker;

import evoparsons.rmishared.BrokerUIInterface;

public interface Broker {
    public String getName();
    public BrokerUIInterface getUIInterface(); 
    public BrokerEAInterface getEAInterface();
}
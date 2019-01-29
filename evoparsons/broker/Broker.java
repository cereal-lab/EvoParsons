package evoparsons.broker;

import evoparsons.rmishared.BrokerUIInterface;

public interface Broker {
    public BrokerUIInterface getUIInterface(); 
    public BrokerEAInterface getEAInterface();
}
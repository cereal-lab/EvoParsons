package evoparsons.rmishared;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

public final class BrokerClient
{
    public static final String SERVICE_NAME = "ParsonsBroker";
    public static BrokerInterface connect(String host, int port, Logger logger) {
        logger.info(String.format("[BrokerInterface] Connect to %s:%d", host, port));		
		try
		{
			Registry r = LocateRegistry.getRegistry(host,port);
			BrokerInterface brokerInterface = (BrokerInterface)r.lookup(SERVICE_NAME);
			logger.info(String.format("[BrokerInterface] service %s was found", SERVICE_NAME));
			return brokerInterface;
		}
		catch (Exception e)
		{
			logger.severe(String.format("[BrokerInterface] Connect to %s:%d failed%n%s", host, port, e.getMessage()));
		}
		return null;
	}
	public static BrokerInterface connect(String host, int port) {
		return connect(host, port, Logger.getGlobal());
	}
}

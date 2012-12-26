package ch.maybites.utils.dyndist;

/**
 * This Interface needs to implement the publisher of a dynamic publication of an object
 * 
 * @author maybites
 *
 */
public interface DynPublisher {

	/**
	 * This method is called when a subscription is connected to a published publication
	 * 
	 * @param distributor identifies the distributor
	 * @param subscription
	 */
	public void subscriptionConnected(String distributor, DynSubscription subscription);
	
	/**
	 * This method is called when a subscription is disconnected from a published publication
	 * 
	 * @param distributor identifies the distributor
	 * @param c
	 */
	public void subscriptionDisconnected(String distributor, DynSubscription c);
	
	/**
	 * This method is called if a connected subscribtion's callback() method is called
	 * 
	 * @param distributor identifies the distributor
	 * @param subscription
	 * @return
	 */
	public boolean subscriptionCallback(String distributor, DynSubscription subscription);
	
}

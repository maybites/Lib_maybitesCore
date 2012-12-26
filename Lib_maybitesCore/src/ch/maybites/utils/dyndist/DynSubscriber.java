package ch.maybites.utils.dyndist;

/**
 * This instance needs to implement the Subscriber of a dynamic subscription
 * 
 * @author maybites
 *
 */
public interface DynSubscriber {

	/**
	 * This Method is called if the provided subscription is connected to a publication
	 * @param distributor
	 * @param subscription
	 */
	public void publicationConnected(String distributor, DynSubscription subscription);
	
	/**
	 * This Method is called if the provided subscription was disconnected from its publication
	 * @param distributor
	 * @param subscription
	 */
	public void publicationDisonnected(String distributor, DynSubscription subscription);

}

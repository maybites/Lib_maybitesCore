package ch.maybites.utils.dyndist;

import java.util.ArrayList;

/**
 * The Dynamic Server for an object
 * @author maybites
 *
 * @param <ObjectType>
 */
public class DynPublication<ObjectType> {
	
	protected DynPublisher publisher;
	protected String identifier;
	protected ObjectType object;
	private DynDistributor distributor;

	private ArrayList<DynSubscription> subscribers;

	protected DynPublication(DynDistributor _distributor, DynPublisher _publisher, String _identifier, ObjectType _object){
		distributor = _distributor;
		publisher = _publisher;
		identifier = _identifier;
		object = _object;
		subscribers = new ArrayList<DynSubscription>();
	}
	
	protected void addSubscription(DynSubscription subscription){
		if(!subscribers.contains(subscription)){
			subscribers.add(subscription);
		}
	}
	
	protected boolean hasSubscriptions(){
		return (subscribers.size() > 0)? true: false;
	}
	
	protected DynSubscription removeNextSubscription(){
		if(subscribers.size() > 0)
			return subscribers.remove(0);
		return null;
	}
	
	protected void removeSubscription(DynSubscription link){
		subscribers.remove(link);
	}
		
	/**
	 * returns the pointed to object
	 * @return
	 */
	protected ObjectType getObject(){
		return object;
	}

	protected String getIdentifier(){
		return identifier;
	}
	
	protected boolean subscriptionCallback(DynSubscription subscription){
		return publisher.subscriptionCallback(distributor.getIdentifier(), subscription);
	}
	
	/**
	 * This method needs to be called in order to enable this publication
	 * @return this instance
	 * @throws DynException if the publications identifier has already been taken
	 */
	public DynPublication publish() throws DynException{
		distributor.publish(this);
		return this;
	}
	
	/**
	 * This method disables and removes this publication from the system
	 */
	public void recall(){
		distributor.recall(this);
	}

	
}

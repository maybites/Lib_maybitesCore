package ch.maybites.utils.dyndist;

import java.util.ArrayList;
import java.util.Iterator;

import ch.maybites.tools.Debugger;

/**
 * The Dynamic Distributor is a useful tool to manage dynamic instances that can appear and disappear
 * because of asynchronous events. The inital impulse to create this library was to deal with the 
 * dynamic creation of MaxMSP mxj-objects that need to be linked with other mxj-objects.
 * 
 * It follows a Publisher - Subscriber model. 
 * 
 * A Publisher publishes a publication that basically contains the objects instance to be distributed.
 * A Subscriber subscribes to a publication to get the instance of the distributed object.
 * 
 * Both the Publisher and the Subscriber get feedback from the Distribution System if one or the other 
 * got disconnected and allow a graceful handling with the instance.
 * 
 * This Framework also allows a callback from the subscription to the publisher. This way a two-way-
 * communication is possible.
 * 
 * When the subscriptions callback method is called, the publisher will get a notice and the instance
 * of the subscription. since the subscription can contain a callback object, the publisher gets thus 
 * access to the subscriber or associated objects.
 * 
 * @author maybites
 *
 * @param <ObjectType> The Type of Object this Distributor helps to create dynamic links to
 * @param <CallbackType> The Type of Callback Object that helps to manipulate the linking party 
 *  - usually the object that implements DynSubscriber.
 */
public class DynDistributor<ObjectType, CallbackType> {

	private ArrayList<DynSubscription> subscriptions;
	private ArrayList<DynPublication> publications;
	
	private String name;

	/**
	 * Constructor of a Dynamic Distributor
	 * @param _name identifier of this distributor
	 */
	public DynDistributor(String _name){
		name = _name;
		subscriptions = new ArrayList<DynSubscription>();
		publications = new ArrayList<DynPublication>();
	}
	
	/**
	 * Calling this method returns this Distributors identifier. This is usefull if you subscribe or publish
	 * to different distributors.
	 * @return
	 */
	public String getIdentifier(){
		return name;
	}
	
	/**
	 * Calling this method returns a new Subscription
	 * 
	 * Attention: it needs to be connected!
	 * In order to enable it, one must call the connect() method
	 * 
	 * @param subscriber
	 * @param objectname is the identifier of the subscribed object
	 * @param callback is a helper object to allow the publisher to callback the subscriber 
	 * @return new Subscription
	 */
	public DynSubscription create(DynSubscriber subscriber, String objectname, CallbackType callback){
		DynSubscription<ObjectType, CallbackType> newConn = new DynSubscription<ObjectType, CallbackType>(this, subscriber, objectname, callback);
		return newConn;
	}
	
	/**
	 * Unsubscribe all subscriptions subscribed by the provided subscriber
	 * @param subscriber
	 */
	public void unsubscribe(DynSubscriber subscriber){
		Iterator<DynSubscription> i = subscriptions.iterator();
		while(i.hasNext()){
			DynSubscription subscription = i.next();
			if(subscription.subscriber == subscriber){
				subscription.publication.removeSubscription(subscription);
				subscription.publication.publisher.subscriptionDisconnected(name, subscription);
				subscription.set(null);
				i.remove();
			}
		}
	}
	
	/**
	 * Unsubscribe the provided Subscription 
	 * @param subscription
	 */
	public void unsubscribe(DynSubscription subscription){
		if(subscription.isConnected()){
			subscription.publication.removeSubscription(subscription);
			subscription.publication.publisher.subscriptionDisconnected(name, subscription);
			subscription.set(null);
		}
		subscriptions.remove(subscription);
	}
	
	/**
	 * Subscribe and try to connect the provided Subscription. 
	 * 
	 * Careful: Calling this method does NOT guarantee to have a valid connection to a publication.
	 * It only means, that the request to subscribe was registered and it tries to connect if there
	 * is a matching publication.
	 * 
	 * VERY Careful: If a valid connection can be established this method calls directly the 
	 * Subscribers publicationConnected() method and returns once that method returns.
	 * 
	 * @param subscription
	 * @return true if a successful connection to a publication could be established
	 */
	public boolean subscribe(DynSubscription subscription){
		if(!subscriptions.contains(subscription)){
			subscriptions.add(subscription);
			if(!subscription.isConnected()){
				Iterator<DynPublication> i = publications.iterator();
				while(i.hasNext()){
					DynPublication publication = i.next();
					if(subscription.equalsIdentifier(publication)){
						subscription.set(publication);
						publication.addSubscription(subscription);
						subscription.subscriber.publicationConnected(name, subscription);
						publication.publisher.subscriptionConnected(name, subscription);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void checkForIteration(){
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		
		for(int i = 0; i < trace.length; i++){
			StackTraceElement element = trace[i];
			if(element.getClassName().equals(this.getClass())){
				;
			}
		}
	}

	/**
	 * Publish the provided Publication
	 * 
	 * Careful: Calling this method does NOT guarantee to have a valid connection to a subscription.
	 * It only means, that the publication is registered and it tries to connect if there
	 * is a matching subscription.
	 * 
	 * VERY Careful: If a valid connection can be established this method calls directly the 
	 * Publishers subscriptionConnected() method and returns once that method returns.
	 * 
	 * @param publication
	 * @throws DynException  if the Publications identifier has already been taken
	 */
	public void publish(DynPublication publication) throws DynException{
		//First check if this pointers name has been already taken
		Iterator<DynPublication> p = publications.iterator();
		while(p.hasNext()){
			if(p.next().identifier.equals(publication.identifier))
				throw new DynException("Registry name already taken: " + publication.identifier);
		}

		publications.add(publication);

		Iterator<DynSubscription> i = subscriptions.iterator();
		while(i.hasNext()){
			DynSubscription subscription = i.next();
			if(!subscription.isConnected() && subscription.equalsIdentifier(publication)){
				publication.addSubscription(subscription);
				subscription.set(publication);
				publication.publisher.subscriptionConnected(name, subscription);
				subscription.subscriber.publicationConnected(name, subscription);
			}
		}
	}

	/**
	 * Returns a new Publication. 
	 * 
	 * Attention: it needs to be published!
	 * In order to enable it, one must call the publish() method
	 * 
	 * @param publisher
	 * @param objectname the publications identifier
	 * @param object the published instance of the object
	 * @return new Publication
	 */
	public DynPublication create(DynPublisher publisher, String objectname, ObjectType object){
		DynPublication<ObjectType> newPublication = new DynPublication<ObjectType>(this, publisher, objectname, object);
		return newPublication;
	}
	
	/**
	 * Recall and remove the provided Publication from the System
	 * @param publication
	 */
	public void recall(DynPublication publication){
		//Debugger.debug(getClass(), "try to recall publication. has Subscriptions: " + publication.hasSubscriptions());
		while(publication.hasSubscriptions()){
			//Debugger.debug(getClass(), "Disconnect subscription...");
			DynSubscription subscription = publication.removeNextSubscription();
			subscription.subscriber.publicationDisonnected(name, subscription);
			subscription.set(null);
		}
		publications.remove(publication);
	}
	
	/**
	 * Recall and remove the Publication with the provided identifier from the System 
	 * @param objectname
	 */
	public void recall(String objectname){
		Iterator<DynPublication> i = publications.iterator();
		while(i.hasNext()){
			DynPublication publication = i.next();
			if(publication.identifier.equals(objectname)){
				while(publication.hasSubscriptions()){
					DynSubscription subscription = publication.removeNextSubscription();
					subscription.subscriber.publicationDisonnected(name, subscription);
					subscription.set(null);
				}
				publications.remove(publication);
			}
		}
	}	
}

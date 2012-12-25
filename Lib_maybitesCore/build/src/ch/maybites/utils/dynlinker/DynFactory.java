package ch.maybites.utils.dynlinker;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Dynamic Link Factory is a useful tool to manage dynamic instances that can appear and disappear
 * because of asynchronous events. The inital impulse to create this library was to deal with the 
 * dynamic creation of MaxMSP mxj-objects that need to be linked with other mxj-objects.
 *  * 
 * @author maybites
 *
 * @param <ObjectType> The Type of Object this Factory helps to create dynamic links to
 * @param <CallbackType> The Type of Callback Object that helps to manipulate the linking party 
 *  - usually the object that implements DynLinkRegistrar.
 */
public class DynFactory<ObjectType, CallbackType> {

	private ArrayList<DynLink> links;
	private ArrayList<DynPointer> pointers;
	
	/**
	 * Constructor of a Dynamic Link Factory
	 * 
	 */
	public DynFactory(){
		links = new ArrayList<DynLink>();
		pointers = new ArrayList<DynPointer>();
	}
	
	/**
	 * Calling this method returns a new Link
	 * 
	 * Attention: it needs to be connected!
	 * In order to enable it, one must call the connect() method
	 * 
	 * @param registrar
	 * @param objectname is the identifier of the dynamic object
	 * @param callback is a helper object to allow the pointer registrar to callback 
	 * @return
	 */
	public DynLink createLink(DynLinkRegistrar registrar, String objectname, CallbackType callback){
		DynLink<ObjectType, CallbackType> newConn = new DynLink<ObjectType, CallbackType>(this, registrar, objectname, callback);
		return newConn;
	}
	
	/**
	 * To disconnect all links registered by the provided registrar
	 * @param linker
	 */
	public void disconnectLinks(DynLinkRegistrar linker){
		Iterator<DynLink> i = links.iterator();
		while(i.hasNext()){
			DynLink next = i.next();
			if(next.registrar == linker){
				next.pointer.removeLink(next);
				next.pointer.registrar.pointerDisconnectedFrom(next);
				next.set(null);
				i.remove();
			}
		}
	}
	
	/**
	 * To disconnect the provided link
	 * @param link
	 */
	public void disconnectLink(DynLink link){
		if(link.isConnected()){
			link.pointer.removeLink(link);
			link.pointer.registrar.pointerDisconnectedFrom(link);
			link.set(null);
		}
		links.remove(link);
	}
	
	/**
	 * to connect the provided link.
	 * 
	 * This method is also called if the DynLink.connect() method is called
	 * 
	 * @param link
	 */
	public void connect(DynLink link){
		links.add(link);
		if(!link.isConnected()){
			Iterator<DynPointer> i = pointers.iterator();
			while(i.hasNext()){
				DynPointer pointer = i.next();
				if(link.equalsPointerName(pointer)){
					link.set(pointer);
					pointer.addLink(link);
					link.registrar.connectedToPointer(link);
					pointer.registrar.pointerConnectedTo(link);
					return;
				}
			}
		}
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
	 * To Register the provided pointer
	 * 
	 * This method is also called if the DynPointer.register() method is called
	 * 
	 * @param pointer
	 * @throws DynException  if the Pointers identifier has already been taken
	 */
	public void register(DynPointer pointer) throws DynException{
		//First check if this pointers name has been already taken
		Iterator<DynPointer> p = pointers.iterator();
		while(p.hasNext()){
			if(p.next().identifier.equals(pointer.identifier))
				throw new DynException("Registry name already taken: " + pointer.identifier);
		}

		pointers.add(pointer);

		Iterator<DynLink> i = links.iterator();
		while(i.hasNext()){
			DynLink link = i.next();
			if(!link.isConnected() && link.equalsPointerName(pointer)){
				pointer.addLink(link);
				link.set(pointer);
				pointer.registrar.pointerConnectedTo(link);
				link.registrar.connectedToPointer(link);
			}
		}
	}

	/**
	 * Returns a new Pointer to the dynamic object instance. 
	 * 
	 * Attention: it needs to be registered!
	 * In order to enable it, one must call the register() method
	 * 
	 * @param registrar
	 * @param objectname the pointers identifier
	 * @param object the instance of the pointed to object
	 * @return the newly created DynPointer
	 */
	public DynPointer createPointer(DynPointerRegistrar registrar, String objectname, ObjectType object){
		DynPointer<ObjectType> newPointer = new DynPointer<ObjectType>(this, registrar, objectname, object);
		return newPointer;
	}
	
	/**
	 * Unregister and remove the provided Pointer from the System
	 * @param pointer
	 */
	public void unregisterPointer(DynPointer pointer){
		while(pointer.hasLinks()){
			DynLink link = pointer.removeNextLink();
			link.set(null);
			link.registrar.disconnectedFromPointer(link);
		}
		pointers.remove(pointer);
	}
	
	/**
	 * Unregister and remove the Pointer with the provided identifier from the System 
	 * @param objectname
	 */
	public void unregisterPointer(String objectname){
		Iterator<DynPointer> i = pointers.iterator();
		while(i.hasNext()){
			DynPointer pointer = i.next();
			if(pointer.identifier.equals(objectname)){
				while(pointer.hasLinks()){
					DynLink link = pointer.removeNextLink();
					link.set(null);
					link.registrar.disconnectedFromPointer(link);
				}
				pointers.remove(pointer);
			}
		}
	}	
}

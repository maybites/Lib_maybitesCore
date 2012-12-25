package ch.maybites.utils.dynlinker;

import java.util.ArrayList;

/**
 * The Dynamic Pointer for an object
 * @author maybites
 *
 * @param <ObjectType>
 */
public class DynPointer<ObjectType> {
	
	protected DynPointerRegistrar registrar;
	protected String identifier;
	protected ObjectType object;
	private DynFactory factory;

	private ArrayList<DynLink> links;

	protected DynPointer(DynFactory _factory, DynPointerRegistrar _registrar, String _identifier, ObjectType _object){
		factory = _factory;
		registrar = _registrar;
		identifier = _identifier;
		object = _object;
		links = new ArrayList<DynLink>();
	}
	
	protected void addLink(DynLink link){
		if(!links.contains(link)){
			links.add(link);
		}
	}
	
	protected boolean hasLinks(){
		return (links.size() > 0)? true: false;
	}
	
	protected DynLink removeNextLink(){
		if(links.size() > 0)
			return links.get(0);
		return null;
	}
	
	protected void removeLink(DynLink link){
		links.remove(link);
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
	
	protected boolean linkRefresh(DynLink linker){
		return registrar.linkRefresh(this, linker);
	}
	
	/**
	 * This method needs to be called in order to enable this pointer
	 * @return this instance
	 * @throws DynException if the Pointers identifier has already been taken
	 */
	public DynPointer register() throws DynException{
		factory.register(this);
		return this;
	}
	
	/**
	 * This method disables and removes this pointer from the system
	 */
	public void unregister(){
		factory.unregisterPointer(this);
	}

	
}

package ch.maybites.utils.dynlinker;

/**
 * The Dynamic Link connection to a Dynamic Pointer of an object
 * @author maybites
 *
 * @param <ObjectType>
 * @param <CallbackType>
 */
public class DynLink<ObjectType, CallbackType>{

	protected DynPointer pointer;
	protected DynLinkRegistrar registrar;
	protected String identifier;
	protected CallbackType callback;
	protected DynFactory factory;
	
	protected DynLink(DynFactory _factory, DynLinkRegistrar _registrar, String _identifier, CallbackType _callback){
		factory = _factory;
		registrar = _registrar;
		identifier = _identifier;
		callback = _callback;
	}

	/**
	 * This method needs to be called in order to enable this Link
	 * @return this instance
	 */
	public DynLink connect(){
		factory.connect(this);
		return this;
	}
	
	/**
	 * This method disables and removes this link from the system
	 */
	public void disconnect(){
		factory.disconnectLink(this);
	}
	
	protected boolean equalsPointerName(DynPointer _pointer){
		return (identifier.equals(_pointer.getIdentifier()))? true: false;
	}

	protected void set(DynPointer _pointer){
		pointer = _pointer;
	}
	
	/**
	 * returns true if there is a connection
	 * @return false if there is no connection
	 */
	public boolean isConnected(){
		return(pointer != null)? true: false;
	}
	
	/**
	 * Gets the Instance of the pointer to Object
	 * @return null if no pointer was set or if there is no connection
	 */
	public ObjectType getObject(){
		if(isConnected())
			return (ObjectType)pointer.getObject();
		return null;
	}

	/**
	 * Gets the Callback Object
	 * @return null if no callback object was set
	 */
	public CallbackType getCallback(){
		return callback;
	}
	
	/**
	 * calls the PointRegistrars linkRefresh-method. This is useful if the PointerRegistrar
	 * should be forced to call the callback object to do whatever.
	 * @return true if refresh was successful;
	 */
	public boolean refresh(){
		if(isConnected())
			return pointer.linkRefresh(this);
		return false;
	}

}

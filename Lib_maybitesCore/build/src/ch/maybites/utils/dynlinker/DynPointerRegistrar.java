package ch.maybites.utils.dynlinker;

/**
 * This Interface needs to implement the registrar of a dynamic pointer to an object
 * @author maybites
 *
 */
public interface DynPointerRegistrar {

	/**
	 * This method is called when a pointer is connected to the provided link
	 * 
	 * @param c
	 */
	public void pointerConnectedTo(DynLink c);
	/**
	 * This method is called when a pointer is disconnected to the provided link
	 * 
	 * @param c
	 */
	public void pointerDisconnectedFrom(DynLink c);
	/**
	 * This method is called if a connected link' method "refresh" is called
	 * 
	 * @param pointer
	 * @param linker
	 * @return
	 */
	public boolean linkRefresh(DynPointer pointer, DynLink linker);
	
}

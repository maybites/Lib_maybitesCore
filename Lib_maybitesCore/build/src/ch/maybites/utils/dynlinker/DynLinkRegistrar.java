package ch.maybites.utils.dynlinker;

/**
 * This instance needs to implement the registrar of a dynamic link
 * 
 * @author maybites
 *
 */
public interface DynLinkRegistrar {

	/**
	 * This Method is called if the provided link is connected to a pointer
	 * @param c
	 */
	public void connectedToPointer(DynLink c);
	/**
	 * This Method is called if the provided link was disconnected from its pointer
	 * @param c
	 */
	public void disconnectedFromPointer(DynLink c);

}

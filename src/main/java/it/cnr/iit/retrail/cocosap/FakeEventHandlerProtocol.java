/*
 * CNR - IIT
 * Coded by: 2014 Enrico "KMcC;) Carniani
 */
package it.cnr.iit.retrail.cocosap;

/**
 *
 * @author oneadmin
 */
public interface FakeEventHandlerProtocol {

    String subscribe(String name, String callbackUrl) throws Exception;

    String unsubscribe(String name, String callbackUrl) throws Exception;
    
    boolean tryAccessResponse(String sapId, boolean permit);
    
    boolean startAccessResponse(String sapId, boolean permit);
    
    boolean endAccessResponse(String sapId, boolean permit);
    
    boolean revokeAccess(String sapId, boolean permit);
}

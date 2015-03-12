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
    
    Boolean tryAccessResponse(String sapId, boolean permit);
    
    Boolean startAccessResponse(String sapId, boolean permit);
    
    Boolean endAccessResponse(String sapId, boolean permit);
    
    Boolean revokeAccess(String sapId);
}

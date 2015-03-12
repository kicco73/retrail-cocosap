/*
 * CNR - IIT
 * Coded by: 2015 Enrico "KMcC;) Carniani
 */
package it.cnr.iit.retrail.cocosap;

/**
 * This interface is offered by the EH to the UCon system in order to let the
 * latter send the EH all the possible meaningful internal events.
 * @author oneadmin
 */
public interface FakeEventHandlerProtocol {

    /**
     * subscribe() is sent by the system to notify the EH that it wants to
     * track the value changes for a given EH attribute.
     * @param name the name of the attribute to be subscribed.
     * @param callbackUrl the url to be called to send updates through
     * the setAttribute() api.
     * @return the current attribute value.
     * @throws Exception if something goes wrong.
     */
    String subscribe(String name, String callbackUrl) throws Exception;

    /**
     * unsubscribe() is sent by the system to notify the EH that it does not
     * need to track anymore the value changes for a given EH attribute.
     * @param name the name of the attribute to be unsubscribed.
     * @param callbackUrl the url to be called to unregister the subscriber
     * properly.
     * @return the current attribute value itself.
     * @throws Exception if something goes wrong.
     */
    String unsubscribe(String name, String callbackUrl) throws Exception;
    
    /**
     * The system sends this event once it gets the result for a tryAccess
     * request made by the EH itself.
     * @param sapId the EH identifier for the session.
     * @param permit the result of the operation. True means that access
     * is allowed, false means that access is denied.
     * @return always true. false is a reserved value.
     */
    Boolean tryAccessResponse(String sapId, boolean permit);
    
    /**
     * The system sends this event once it gets the result for a startAccess
     * request made by the EH itself.
     * @param sapId the EH identifier for the session.
     * @param permit the result of the operation. True means that access
     * is allowed, false means that access is denied.
     * @return always true. false is a reserved value.
     */
    Boolean startAccessResponse(String sapId, boolean permit);
    
    /**
     * The system sends this event once it gets the result for an endAccess
     * request made by the EH itself.
     * @param sapId the EH identifier for the session.
     * @param permit the result of the operation. True means that access
     * is allowed, false means that access is denied.
     * @return always true. false is a reserved value.
     */
    Boolean endAccessResponse(String sapId, boolean permit);
    
    /**
     * revokeAccess() gets invoked as soon as the continuous usage control 
     * system detects the right conditions under which the access gets denied.
     * @param sapId the EH identifier for the revoked session.
     * @return always true. false is a reserved value.
     */
    Boolean revokeAccess(String sapId);
}

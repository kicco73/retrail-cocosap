package it.cnr.iit.retrail.cocosap;

/*
 * CNR - IIT
 * Coded by: 2015 Enrico "KMcC;) Carniani
 */

/**
 * This interface is responsible of asking access to the continuous access
 * control system.
 * @author oneadmin
 */
public interface PEPSessionManagerProtocol {

    /**
     * tryAccess() asks the continuous usage control system to try access
     * to an EH object.
     * @param sapId the EH own identification number for the object to be
     * kept under control. The permission may be returned synchronously if
     * it can be computed fast. In any case once the decision is made, it is
     * notified to the EH via the tryAccessResponse event.
     * @return true if access is permitted, false if not. It may also return
     * null if the permission cannot be calculated fast and no decision can
     * be made at this time. 
     * @throws Exception if something goes wrong.
     */
    Boolean tryAccess(String sapId) throws Exception;

    /**
     * startAccess() asks the continuous usage control system to start the
     * access of a previously initiated session (via a successful tryAccess).
     * This operation cannot be done until the tryAccessResponse event has
     * been received by the EH.
     * @param sapId the EH own identification number for the object to be
     * kept under control. The permission may be returned synchronously if
     * it can be computed fast. In any case once the decision is made, it is
     * notified to the EH via the startAccessResponse event.
     * @return true if access is permitted, false if not. It may also return
     * null if the permission cannot be calculated fast and no decision can
     * be made at this time. 
     * @throws Exception if something goes wrong.
     */
    Boolean startAccess(String sapId) throws Exception;

    /**
     * endAccess() asks the continuous usage control system to end the
     * access of a previously initiated session (via a successful tryAccess).
     * This operation cannot be done until the tryAccessResponse event has
     * been received by the EH. The endAccess() operation is valid even if
     * the EH has not started the access via startAccess(), in order to
     * quit the session early.
     * Once computed, the system notifies the EH via an endAccessResponse
     * event.
     * @param sapId the EH own identification number for the object to be
     * kept under control. The permission may be returned synchronously if
     * it can be computed fast. In any case once the decision is made, it is
     * notified to the EH via the endAccessResponse event.
     * @return true if access is permitted, false if not. It may also return
     * null if the permission cannot be calculated fast and no decision can
     * be made at this time. 
     * @throws Exception if something goes wrong.
     */
    Boolean endAccess(String sapId) throws Exception;

}

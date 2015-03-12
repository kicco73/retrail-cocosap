package it.cnr.iit.retrail.cocosap;

/*
 * CNR - IIT
 * Coded by: 2015 Enrico "KMcC;) Carniani
 */

/**
 * This interface is available via remote call from the EH (EventHandler)
 * to the continuous access control and is intended for updating shared
 * attributes held by the EH itself.
 * @author oneadmin
 */
public interface PIPDataCollectorProtocol {

    /**
     * setAttribute() notifies the continuous access control system that
     * an attribute has changed its value. This will make the system reevaluate
     * all sessions and, possibly, send some revocation events if applicable.
     * @param name the name of the attribute whose value has to be changed.
     * @param value the new value for the attribute.
     * @return the new value itself.
     * @throws Exception if something goes wrong.
     */
    String setAttribute(String name, String value) throws Exception;

}

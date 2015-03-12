package it.cnr.iit.retrail.cocosap;

import org.w3c.dom.Node;

/*
 * CNR - IIT
 * Coded by: 2014 Enrico "KMcC;) Carniani
 */

/**
 *
 * @author oneadmin
 */
public interface PEPSessionManagerProtocol {

    Boolean tryAccess(String sapId) throws Exception;
    Boolean startAccess(String sapId) throws Exception;
    Boolean endAccess(String sapId) throws Exception;

}

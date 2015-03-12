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

    boolean tryAccess(String sapId) throws Exception;
    boolean startAccess(String sapId) throws Exception;
    boolean endAccess(String sapId) throws Exception;

}

/*
 * CNR - IIT
 * Coded by: 2015 Enrico "KMcC;) Carniani
 */
package it.cnr.iit.retrail.cocosap;

import org.w3c.dom.Node;

/**
 *
 * @author oneadmin
 */
public class ProtocolProxy implements PIPDataCollectorProtocol, PEPSessionManagerProtocol {

    @Override
    public String setAttribute(String name, String value) throws Exception {
        return PIPDataCollector.getInstance().setAttribute(name, value);
    }

    @Override
    public Boolean tryAccess(String sapId, String subjectId, String actionId, String resourceId) throws Exception {
        return Main.pep.tryAccess(sapId, subjectId, actionId, resourceId);
    }

    @Override
    public Boolean tryAccess(String sapId, Object[] xacmlAttributes) throws Exception {
        return Main.pep.tryAccess(sapId, xacmlAttributes);
    }
    
    @Override
    public Boolean startAccess(String sapId) throws Exception {
        return Main.pep.startAccess(sapId);
    }

    @Override
    public Boolean endAccess(String sapId) throws Exception {
        return Main.pep.endAccess(sapId);
    }

}

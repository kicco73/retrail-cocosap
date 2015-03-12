/*
 * CNR - IIT
 * Coded by: 2014 Enrico "KMcC;) Carniani
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
    public boolean tryAccess(String sapId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean startAccess(String sapId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean endAccess(String sapId) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

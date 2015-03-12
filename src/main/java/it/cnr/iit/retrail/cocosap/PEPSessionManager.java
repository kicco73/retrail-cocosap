/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.iit.retrail.cocosap;

import it.cnr.iit.retrail.client.impl.PEP;
import it.cnr.iit.retrail.commons.impl.Client;
import it.cnr.iit.retrail.commons.impl.PepRequest;
import it.cnr.iit.retrail.commons.impl.PepResponse;
import it.cnr.iit.retrail.commons.impl.PepSession;
import java.net.URL;
import org.apache.xmlrpc.XmlRpcException;

/**
 *
 * @author kicco
 */
public class PEPSessionManager  extends PEP implements PEPSessionManagerProtocol {
    private URL eventHandlerUrl;
    private Client client;

    public PEPSessionManager(URL pdpUrl, URL myUrl) throws Exception {
        super(pdpUrl, myUrl);
    }
    
    private boolean sendNotification(String api, PepResponse res, String sapId) throws XmlRpcException {
        log.warn("invoking FakeEventHandler.unsubscribe()");
        boolean permit = res.getDecision() == PepResponse.DecisionEnum.Permit;
        Object []params = {sapId, permit};
        client.execute(api, params);
        return permit;
    }
    
    @Override
    public boolean tryAccess(String sapId) throws Exception {
        PepRequest req = null;// FIXME TODO
        PepResponse res = tryAccess(req, sapId);
        return sendNotification("FakeEventHandler.tryAccessResponse", res, sapId);
    }

    @Override
    public boolean startAccess(String sapId) throws Exception {
        PepResponse res = startAccess(null, sapId);
        return sendNotification("FakeEventHandler.startAccessResponse", res, sapId);
    }

    @Override
    public boolean endAccess(String sapId) throws Exception {
        PepResponse res = endAccess(null, sapId);
        return sendNotification("FakeEventHandler.endAccessResponse", res, sapId);
    }   
    
    @Override
    public synchronized void onRevokeAccess(PepSession session) throws Exception {
        sendNotification("FakeEventHandler.revokeAccess", session, session.getCustomId());
    }

    
    public URL getEventHandlerUrl() {
        return eventHandlerUrl;
    }

    public void setEventHandlerUrl(URL eventHandlerUrl) throws Exception {
        this.eventHandlerUrl = eventHandlerUrl;
        client = new Client(eventHandlerUrl);
    }
    

}

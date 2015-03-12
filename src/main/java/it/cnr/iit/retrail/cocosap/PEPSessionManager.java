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
import it.cnr.iit.retrail.server.impl.UCon;
import java.net.URL;
import org.apache.xmlrpc.XmlRpcException;

/**
 *
 * @author kicco
 */
public class PEPSessionManager  extends PEP implements PEPSessionManagerProtocol {
    private URL eventHandlerUrl;
    private Client eventHandlerClient;
    private boolean inited = false;
    
    public PEPSessionManager(URL pdpUrl, URL myUrl) throws Exception {
        super(pdpUrl, myUrl);
    }
    
    @Override
    public void init() throws Exception {
        super.init();
        synchronized(this) {
            inited = true;
            notifyAll();
        }
    }
    
    public void waitUntilInited() throws InterruptedException {
        synchronized(this) {
            while(!inited)
                wait();
        }
    }

    private boolean sendNotification(String api, PepResponse res, String sapId) throws XmlRpcException {
        log.warn("invoking {}", api);
        boolean permit = res.getDecision() == PepResponse.DecisionEnum.Permit;
        Object []params = {sapId, permit};
        eventHandlerClient.execute(api, params);
        return permit;
    }
        
    private boolean sendNotification(String api, String sapId) throws XmlRpcException {
        log.warn("invoking {}", api);
        Object []params = {sapId};
        eventHandlerClient.execute(api, params);
        return true;
    }
    
    @Override
    public Boolean tryAccess(String sapId) throws Exception {
        PepRequest req = PepRequest.newInstance(
                sapId,
                "urn:fedora:names:fedora:2.1:action:id-getDatastreamDissemination",
                " ",
                UCon.uri);        
        PepResponse res = tryAccess(req, sapId);
        return sendNotification("FakeEventHandler.tryAccessResponse", res, sapId);
    }

    @Override
    public Boolean startAccess(String sapId) throws Exception {
        PepResponse res = startAccess(null, sapId);
        return sendNotification("FakeEventHandler.startAccessResponse", res, sapId);
    }

    @Override
    public Boolean endAccess(String sapId) throws Exception {
        PepResponse res = endAccess(null, sapId);
        return sendNotification("FakeEventHandler.endAccessResponse", res, sapId);
    }   
    
    @Override
    public synchronized void onRevokeAccess(PepSession session) throws Exception {
        sendNotification("FakeEventHandler.revokeAccess", session.getCustomId());
    }

    public URL getEventHandlerUrl() {
        return eventHandlerUrl;
    }

    public void setEventHandlerUrl(URL eventHandlerUrl) throws Exception {
        this.eventHandlerUrl = eventHandlerUrl;
        eventHandlerClient = new Client(eventHandlerUrl);
    }
    
}

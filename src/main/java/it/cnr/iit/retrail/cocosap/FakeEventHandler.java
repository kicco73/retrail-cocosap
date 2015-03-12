/*
 * CNR - IIT
 * Coded by: 2015 Enrico "KMcC;) Carniani
 */
package it.cnr.iit.retrail.cocosap;

import it.cnr.iit.retrail.commons.Server;
import it.cnr.iit.retrail.commons.impl.Client;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.xmlrpc.XmlRpcException;

/**
 *
 * @author kicco
 */
public class FakeEventHandler extends Server implements FakeEventHandlerProtocol {
    protected boolean green = true;
    static final String defaultKeystoreName = "/META-INF/keystore.jks";
    static final String defaultKeystorePassword = "uconas4wc";
    static public final String myUrlString = "http://0.0.0.0:9093";
    static private final Map<String,Integer> attributes = new HashMap<>();
    static private final Map<String,Collection<Client>> clients = new HashMap<>();
    static private FakeEventHandler instance = null;
    
    public FakeEventHandler() throws Exception {
        super(new URL(myUrlString), FakeEventHandlerProxy.class, FakeEventHandler.class.getSimpleName());
        log.warn("creating fake event handler server at URL: {}, initial value: {}; namespace: {}", myUrlString, green, getClass().getSimpleName());
        // Telling server to use a self-signed certificate and
        // trust any client.
        InputStream ks = FakeEventHandler.class.getResourceAsStream(defaultKeystoreName);
        trustAllPeers(ks, defaultKeystorePassword);
        attributes.put("timer", 0);
    }

    @Override 
    public void init() throws Exception {
        instance = this;        
        super.init();
    }
    
    static public FakeEventHandler getInstance() {
        return instance;
    }
    
    @Override
    protected synchronized void watchdog() throws InterruptedException {
        log.info("called");
        String key = "timer";
        attributes.put(key, attributes.get(key) + 1);
        for(Client client: clients.getOrDefault(key, new ArrayList<Client>())) {
            Object[] params = {key, attributes.get(key).toString()};
            try {
                client.execute("PIPDataCollector.setAttribute", params);
            } catch (XmlRpcException ex) {
                log.error("while notifying {}: {}", client, ex);
            }
        }
    }
    
    @Override
    public synchronized String subscribe(String name, String callbackUrl) throws Exception {
        Client newClient = new Client(new URL(callbackUrl));
        Collection<Client> cl = clients.get(name);
        if(cl == null) {
            cl = new ArrayList<>();
            cl.add(newClient);
            clients.put(name, cl);
        }
        return attributes.get(name).toString();
    }

    @Override
    public synchronized String unsubscribe(String name, String callbackUrl) throws Exception {
        clients.remove(name);
        return null;
    }

    @Override
    public Boolean tryAccessResponse(String sapId, boolean permit) {
        log.warn("EVENT HANDLER RECEIVED TRY ACCESS RESPONSE: {}", permit);
        return true;
    }

    @Override
    public Boolean startAccessResponse(String sapId, boolean permit) {
        log.warn("EVENT HANDLER RECEIVED START ACCESS RESPONSE: {}", permit);
        return true;
    }

    @Override
    public Boolean endAccessResponse(String sapId, boolean permit) {
        log.warn("EVENT HANDLER RECEIVED END ACCESS RESPONSE: {}", permit);
        return true;
    }

    @Override
    public Boolean revokeAccess(String sapId) {
        log.warn("EVENT HANDLER REVOKE ACCESS CALLED");
        return true;
    }
    
}

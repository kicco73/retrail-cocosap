/*
 * CNR - IIT
 * Coded by: 2014 Enrico "KMcC;) Carniani
 */
package it.cnr.iit.retrail.cocosap;

import it.cnr.iit.retrail.commons.PepAttributeInterface;
import it.cnr.iit.retrail.commons.PepRequestInterface;
import it.cnr.iit.retrail.commons.PepSessionInterface;
import it.cnr.iit.retrail.commons.Server;
import it.cnr.iit.retrail.commons.impl.Client;
import it.cnr.iit.retrail.commons.impl.PepAttribute;
import it.cnr.iit.retrail.server.UConInterface;
import it.cnr.iit.retrail.server.pip.impl.StandAlonePIP;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.apache.xmlrpc.webserver.WebServer;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 *
 * @author kicco
 */
public class PIPDataCollector extends StandAlonePIP implements PIPDataCollectorProtocol {
    static final public String category = PepAttribute.CATEGORIES.SUBJECT;
    private String dataCollectorUrl = "http://0.0.0.0:9092";

    private String eventHandlerUrl = FakeEventHandler.myUrlString;
    private WebServer _webServer;
    private Client _client;
    private static PIPDataCollector instance;
    private Map<String,String> values = new HashMap<>();
    
    static public PIPDataCollector getInstance() {
        return instance;
    }
    
    public PIPDataCollector() throws Exception {
        this.log = LoggerFactory.getLogger(PIPDataCollector.class);
    }
    
    @Override
    public void init(UConInterface ucon) {
        instance = this;
        super.init(ucon);
        try {
            // start server
            getServer().start();
            log.warn("invoking FakeEventHandler.subscribe()");
            Object []params = {"timer", dataCollectorUrl};
            String v = (String) getClient().execute("FakeEventHandler.subscribe", params);
            values.put("timer", v);
            log.warn("timer = {}", v);
        } catch (Exception ex) {
            log.error("while starting webserver: {}", ex);
        }
    }

    private Client getClient() throws Exception {
        if(_client == null)
            _client = new Client(new URL(eventHandlerUrl));
        return _client;
    }

    private WebServer getServer() throws Exception {
        log.warn("listening at URL: {}, namespace: {}", dataCollectorUrl, getClass().getSimpleName());
        _webServer = Server.createWebServer(new URL(dataCollectorUrl), ProtocolProxy.class, getClass().getSimpleName());        
        return _webServer;
    }

    @Override
    public void refresh(PepRequestInterface request, PepSessionInterface session) {
        //log.warn("refreshing semaphore value");
        PepAttributeInterface test = newSharedAttribute("timer", PepAttribute.DATATYPES.BOOLEAN, values.get("timer"), category);
        request.replace(test);
    }

    @Override
    public void run() {
    }

    @Override
    public void term() {
        try {
            log.warn("invoking FakeEventHandler.unsubscribe()");
            Object []params = {"timer", dataCollectorUrl};
            getClient().execute("FakeEventHandler.unsubscribe", params);
            getServer().shutdown();
        } catch (Exception ex) {
            log.error("while terminating: {}", ex);
        }
        super.term();
    }

    @Override
    public synchronized String setAttribute(String name, String value) throws Exception {
        log.warn("setting attribute {} = {}", name, value);
        values.put(name, value);
        //notifyChanges(listManagedAttributes());
        // XXX FIXME Workaround
        PepAttributeInterface test = newSharedAttribute(name, PepAttribute.DATATYPES.STRING, value, category);
        notifyChanges(test);
        return value;
    }
    
    public String getEventHandlerUrl() {
        return eventHandlerUrl;
    }

    public void setEventHandlerUrl(String eventHandlerUrl) {
        _client = null;
        this.eventHandlerUrl = eventHandlerUrl;
    }
    
    public String getDataCollectorUrl() {
        return dataCollectorUrl;
    }

    public void setDataCollectorUrl(String dataCollectorUrl) {
        if(_webServer != null)
            throw new RuntimeException("Web server already running, cannot change URL");
        this.dataCollectorUrl = dataCollectorUrl;
        
    }
}

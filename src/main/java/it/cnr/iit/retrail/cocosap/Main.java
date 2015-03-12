/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.cnr.iit.retrail.cocosap;

import it.cnr.iit.retrail.server.impl.UCon;
import it.cnr.iit.retrail.server.impl.UConFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kicco
 */

public class Main {
    static final org.slf4j.Logger log = LoggerFactory.getLogger(Main.class);
    static final String defaultKeystoreName = "/META-INF/keystore.jks";
    static final String defaultKeystorePassword = "uconas4wc";
    static private UCon ucon = null;
    static public PEPSessionManager pep = null;
    static public FakeEventHandler semaphoreServer = null;
    
    static public void main(String[] argv) throws Exception {
            log.info("Setting up Semaphore server...");
            semaphoreServer = new FakeEventHandler();
            semaphoreServer.init();

            String pdpUrlString = argv != null && argv.length > 0? 
                    argv[0] : "http://localhost:9090";
            URL pdpUrl = new URL(pdpUrlString);
            log.info("Setting up Ucon server at {}...", pdpUrlString);
            ucon = UConFactory.getInstance(pdpUrl);
            // Telling server to use a self-signed certificate and
            // trust any client.
            InputStream ks = Main.class.getResourceAsStream(defaultKeystoreName);
            ucon.trustAllPeers(ks, defaultKeystorePassword);

            if(argv != null && argv.length > 1) {
                File f = new File(argv[1]);
                log.info("using file-system behaviour file located at: {}", f.getAbsolutePath());
                ucon.loadConfiguration(new FileInputStream(f));
            } else {
                log.warn("using builtin behaviour");
                ucon.loadConfiguration(Main.class.getResourceAsStream("/ucon-cocosap.xml"));
            }

            // start server
            ucon.init();
            log.info("Cocosap UCon server ready");
            
            pep = new PEPSessionManager(ucon.myUrl, new URL("http://0.0.0.0:9091"));
            pep.trustAllPeers();
            pep.setEventHandlerUrl(new URL(PIPDataCollector.getInstance().getEventHandlerUrl()));
            pep.init();
            log.info("Cocosap PEPSessionManager component ready");            
        }

    static public void term() throws InterruptedException {
        pep.term();
        ucon.term();
        semaphoreServer.term();
    }

}

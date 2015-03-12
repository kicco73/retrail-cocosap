/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.iit.retrail.cocosap;

import it.cnr.iit.retrail.commons.impl.Client;
import java.net.URL;
import junit.framework.TestCase;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.slf4j.LoggerFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest extends TestCase {

    static final org.slf4j.Logger log = LoggerFactory.getLogger(MainTest.class);

    public MainTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        log.warn("creating cocosap components");
        Main.main(null);
        Main.pep.waitUntilInited();
    }

    @Override
    protected void tearDown() throws Exception {
        Main.term();
        super.tearDown();
    }

    private boolean sessionManagercall(String api, String sapId) throws Exception {
        api = "PIPDataCollector."+api;
        log.warn("invoking {}", api);
        Object []params = {sapId};
        Client client = new Client(new URL(PIPDataCollector.getInstance().getDataCollectorUrl()));
        return (boolean) client.execute(api, params);
    }
    
    public void testTryEnd() throws Exception {
        log.warn("testing try-end cycle");
        String sapId = "sap-identifier";
        assert(sessionManagercall("tryAccess", sapId));
        assert(sessionManagercall("endAccess", sapId));
    }

    public void testTryStartEnd() throws Exception {
        log.warn("testing try-end cycle");
        String sapId = "sap-identifier";
        assert(sessionManagercall("tryAccess", sapId));
        assert(sessionManagercall("startAccess", sapId));
        assert(sessionManagercall("endAccess", sapId));
    }
}

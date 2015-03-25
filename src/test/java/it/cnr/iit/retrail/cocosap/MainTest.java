/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.cnr.iit.retrail.cocosap;

import it.cnr.iit.retrail.commons.impl.Client;
import it.cnr.iit.retrail.commons.impl.PepAttribute;
import org.w3c.dom.Element;
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

    private boolean sessionManagercall(String api, String sapId, Element[]xacmlAttributes) throws Exception {
        api = "PIPDataCollector."+api;
        log.warn("invoking {}", api);
        Client client = new Client(new URL(PIPDataCollector.getInstance().getDataCollectorUrl()));
        Object[] params = new Object[] {sapId, xacmlAttributes};
        return (boolean) client.execute(api, params);
    }
    
    private boolean sessionManagercall(String api, String... args) throws Exception {
        api = "PIPDataCollector."+api;
        log.warn("invoking {}", api);
        Client client = new Client(new URL(PIPDataCollector.getInstance().getDataCollectorUrl()));
        return (boolean) client.execute(api, args);
    }
    
    public void testTryEnd() throws Exception {
        log.warn("testing try-end cycle");
        String sapId = "sap-identifier";
        String subjectId = "subjectId";
        String actionId = "actionId";
        String resourceId = "resourceId";
        assert(sessionManagercall("tryAccess", sapId, subjectId, actionId, resourceId));
        assert(sessionManagercall("endAccess", sapId));
    }

    public void testTryStartEnd() throws Exception {
        log.warn("testing try-end cycle");
        String sapId = "sap-identifier";
        String subjectId = "subjectId";
        String actionId = "actionId";
        String resourceId = "resourceId";
        assert(sessionManagercall("tryAccess", sapId, subjectId, actionId, resourceId));
        assert(sessionManagercall("startAccess", sapId));
        assert(sessionManagercall("endAccess", sapId));
    }
    
    public void testTryEnd2() throws Exception {
        log.warn("testing try-end cycle (powerful api)");
        String sapId = "sap-identifier";
        Element[] xacmlAttributes = new Element[3];
        xacmlAttributes[0] = PepAttribute.newSubject("sap-identifier", "sap").toElement();
        xacmlAttributes[1] = PepAttribute.newAction("sap-action", "sap").toElement();
        xacmlAttributes[2] = PepAttribute.newResource("sap-resource", "sap").toElement();
        assert(sessionManagercall("tryAccess", sapId, xacmlAttributes));
        assert(sessionManagercall("endAccess", sapId));
    }

    public void testTryStartEnd2() throws Exception {
        log.warn("testing try-end cycle  (powerful api)");
        String sapId = "sap-identifier";
        Element[] xacmlAttributes = new Element[3];
        xacmlAttributes[0] = PepAttribute.newSubject("sap-identifier", "sap").toElement();
        xacmlAttributes[1] = PepAttribute.newAction("sap-action", "sap").toElement();
        xacmlAttributes[2] = PepAttribute.newResource("sap-resource", "sap").toElement();
        assert(sessionManagercall("tryAccess", sapId, xacmlAttributes));
        assert(sessionManagercall("startAccess", sapId));
        assert(sessionManagercall("endAccess", sapId));
    }
}

/*
 * CNR - IIT
 * Coded by: 2014 Enrico "KMcC;) Carniani
 */
package it.cnr.iit.retrail.cocosap;

/**
 *
 * @author oneadmin
 */
public class FakeEventHandlerProxy implements FakeEventHandlerProtocol {

    @Override
    public String subscribe(String attributeName, String callbackUrl) throws Exception {
        return FakeEventHandler.getInstance().subscribe(attributeName, callbackUrl);
    }

    @Override
    public String unsubscribe(String attributeName, String callbackUrl) throws Exception {
        return FakeEventHandler.getInstance().subscribe(attributeName, callbackUrl);
    }

    @Override
    public Boolean tryAccessResponse(String sapId, boolean permit) {
        return FakeEventHandler.getInstance().tryAccessResponse(sapId, permit);
    }

    @Override
    public Boolean startAccessResponse(String sapId, boolean permit) {
        return FakeEventHandler.getInstance().startAccessResponse(sapId, permit);
    }

    @Override
    public Boolean endAccessResponse(String sapId, boolean permit) {
        return FakeEventHandler.getInstance().endAccessResponse(sapId, permit);
    }

    @Override
    public Boolean revokeAccess(String sapId) {
        return FakeEventHandler.getInstance().revokeAccess(sapId);
    }

}

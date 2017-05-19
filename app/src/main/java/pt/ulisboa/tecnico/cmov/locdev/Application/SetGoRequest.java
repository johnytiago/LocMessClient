package pt.ulisboa.tecnico.cmov.locdev.Application;

import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;

/**
 * Created by Pedro Alcobia on 19/05/2017.
 */

public class SetGoRequest extends Request {
    public String deviceName;
    public SetGoRequest(String name){
        this.deviceName=name;
    }
}

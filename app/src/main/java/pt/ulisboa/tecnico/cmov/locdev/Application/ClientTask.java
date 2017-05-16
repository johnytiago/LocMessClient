package pt.ulisboa.tecnico.cmov.locdev.Application;

import android.os.AsyncTask;
import android.util.Log;

import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.ulisboa.tecnico.cmov.locdev.R;
import pt.ulisboa.tecnico.cmov.projcmu.Client;
import pt.ulisboa.tecnico.cmov.projcmu.request.Request;
import pt.ulisboa.tecnico.cmov.projcmu.response.Response;

/**
 * Created by Pedro Alcobia on 08/05/2017.
 */

public class ClientTask extends AsyncTask<Request, Integer, Response> {
    Client cli = new Client();
    public LocdevApp app = null;
    public SimWifiP2pDevice device = null;

    public ClientTask(LocdevApp app){
        this.app = app;
    }

    public void setDevice(SimWifiP2pDevice device){
        this.device=device;
    }

    protected Response doInBackground(Request... requests) {
        Log.d(this.getClass().getName(),"BackGround Start");
        if(requests.length<1){
            return null;
        }
        Request req = requests[0];
        if(device==null){
            app.resp=cli.SendRequest(req);
        }else{
            app.resp=cli.SendRequest(device.getVirtIp(), Integer.parseInt(app.getString(R.string.port)),req);
        }

        Log.d(this.getClass().getName(),"BackGround End");
        return app.resp;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(Response result) {
//            Log.d(this.getClass().getName(),"End OnpostExecute");
//            app.resp=result;
//            if(result==null){
//                Log.d(this.getClass().getName(),"Result Null");
//            }
//            if(resp==null){
//                Log.d(this.getClass().getName(),"Resp Null");
//            }
//            Log.d(this.getClass().getName(),"End OnpostExecute");
//            //Toast.makeText(,"",Toast.LENGTH_LONG);
//            //showDialog("Downloaded " + result + " bytes");


    }
}
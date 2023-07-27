package com.ph28326.lab6_mob403;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.serialization.SoapObject;

public class ConvertTemperatureTask extends AsyncTask<String, Void, String> {

    ProgressDialog pDialog;
    private MainActivity activity;
    private String soapAction;
    private String methodName;
    private String paramsName;

    public ConvertTemperatureTask(MainActivity activity, String soapAction, String methodName, String paramsName) {
        this.activity = activity;
        this.soapAction = soapAction;
        this.methodName = methodName;
        this.paramsName = paramsName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        SoapObject request = new SoapObject(Constants.NAME_SPACE, methodName);

        //add properties for soap object
        request.addProperty(paramsName, params[0]);

        //request to server and get Soap Primitive response
        return WebServiceCall.callWSThreadSoapPrimitive(Constants.URL, soapAction, request);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
        if (result == null) {
            Log.i("check", "cannot get result");
        } else {
            Log.i("check", result);
        //invoke call back method of Activity
            activity.callBackDataFromAsyncTask(result);
        }
    }


}

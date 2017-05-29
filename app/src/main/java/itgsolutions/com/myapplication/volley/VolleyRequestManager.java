package itgsolutions.com.myapplication.volley;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VolleyRequestManager {
    private String tag_json_obj = "jobj_req";
    private static VolleyRequestManager instance;
    private Context context;

    private VolleyRequestManager(Context context) {
        this.context = context;
    }

    public static VolleyRequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyRequestManager(context);
        }
        return instance;
    }

    public synchronized void pullJson(final WebServices apiName, final WebServices method, String requestType, final VolleyResponsable volleyResponsable, String urlJsonObject, final Map<String, String> param) {
        if (ConnectionDetector.getInstance().isConnectingToInternet(context)) {
            int MethodType;
            if (requestType.equalsIgnoreCase("post"))
                MethodType = Request.Method.POST;
            else
                MethodType = Request.Method.GET;

            JSONObject objectParams = null;
            if (param != null)
                objectParams = new JSONObject(param);

            //  String str = android.net.Uri.encode(String.valueOf(objectParams), "UTF-8");

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(MethodType, urlJsonObject, objectParams, new com.android.volley.Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    volleyResponsable.onJsonResponse(response, apiName, method);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    volleyResponsable.onError(error, apiName);

                }
            }) {
                /*
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = param;
                    return params;
                }
            };
            int socketTimeout = 3 * 60000;//3 minutes - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsonObjReq.setRetryPolicy(policy);

            // Adding request to request queue
            VolleyController.getInstance(context).addToRequestQueue(jsonObjReq, tag_json_obj);
        } else {
            ConnectionDetector.getInstance().showNoInternetConnectionDialog(context);
        }
    }
}

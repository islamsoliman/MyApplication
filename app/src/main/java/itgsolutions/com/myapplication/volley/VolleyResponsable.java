package itgsolutions.com.myapplication.volley;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyResponsable {
    void onJsonResponse(JSONObject response, WebServices apiName, WebServices method);

    void onError(VolleyError error, WebServices apiName);
}

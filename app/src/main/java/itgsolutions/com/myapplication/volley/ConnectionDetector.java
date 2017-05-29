package itgsolutions.com.myapplication.volley;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.itgsolutions.gpsAttendanceSystem.R;

public class ConnectionDetector {
    private static ConnectionDetector instance;

    private ConnectionDetector() {
    }

    public static ConnectionDetector getInstance() {
        if (instance == null) {
            instance = new ConnectionDetector();
        }
        return instance;
    }

    //check if there is internet connection
    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    /**
     * Function to display simple Alert Dialog if no internet connection
     */
    public void showNoInternetConnectionDialog(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(context.getString(R.string.internet_error))
                .setMessage(context.getString(R.string.check_internet))
                .setCancelable(false)
                .setIcon(R.drawable.connection_fail)
                .setNegativeButton(context.getString(R.string.close), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = alertDialog.create();
        // Showing Alert Message
        alert.show();
    }

    public void showNoInternetConnectionForSearchDialog(Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(context.getString(R.string.internet_error))
                .setMessage(context.getString(R.string.search_error_message))
                .setCancelable(false)
                .setIcon(R.drawable.connection_fail)
                .setNegativeButton(context.getString(R.string.close), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }
                );
        AlertDialog alert = alertDialog.create();
        // Showing Alert Message
        alert.show();
    }

}

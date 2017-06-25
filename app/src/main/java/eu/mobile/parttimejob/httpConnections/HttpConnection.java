package eu.mobile.parttimejob.httpConnections;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Stoycho on 6/25/2017.
 */

public class HttpConnection extends AsyncTask<URL, Void, String> {

    private Context mContext;

    public HttpConnection(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(URL... urls) {

        String dataGetChanges = "";
        URL urlForData  = urls[0];

        try {

            HttpURLConnection urlConnectionGetChanges = (HttpURLConnection) urlForData.openConnection();

            BufferedReader bufferedReaderGetChanges = new BufferedReader(new InputStreamReader(urlConnectionGetChanges.getInputStream()));
            String nextGetChanges = null;

            nextGetChanges = bufferedReaderGetChanges.readLine();

            while (nextGetChanges != null) {
                dataGetChanges += nextGetChanges;
                nextGetChanges = bufferedReaderGetChanges.readLine();
            }
            urlConnectionGetChanges.disconnect();

            return dataGetChanges;

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Toast.makeText(mContext,result,Toast.LENGTH_SHORT).show();
    }
}

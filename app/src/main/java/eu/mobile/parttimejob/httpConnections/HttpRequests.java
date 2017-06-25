package eu.mobile.parttimejob.httpConnections;

import java.net.MalformedURLException;
import java.net.URL;

import eu.mobile.parttimejob.Utils;

/**
 * Created by Stoycho on 6/25/2017.
 */

public class HttpRequests {

    public static URL getLoginUrl(String username, String password) throws MalformedURLException {
        String urlString = Utils.SERVER_URL + "/api/login?username=" + username + "&password=" + password;

        return new URL(urlString);
    }
}

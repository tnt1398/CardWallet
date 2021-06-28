/*
 * This class handles CREATE Restful operation via PUT HTTP method
 * to support AddCardActivity class.
 */
package com.example.assignm4app;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RestfulCreateTask extends AsyncTask<String, Void, String> {

    private EditText cardNameView, cardNumberView;
    private String cardName, cardNumber;

    /**
     * Initialising the constructor
     */
    public RestfulCreateTask(EditText cardNameView, EditText cardNumberView, String cardName, String cardNumber) {
        this.cardNameView = cardNameView;
        this.cardNumberView = cardNumberView;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length == 0)
            return "No URL provided";
        try {
            URL bookingUrl = new URL(params[0]);
            HttpURLConnection conn
                    = (HttpURLConnection) bookingUrl.openConnection();
            conn.setReadTimeout(3000); //3 seconds
            conn.setConnectTimeout(3000); //3 seconds
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "text/plain");
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT)
                return "Successful insert";
            else
                return "HTTP Response code " + responseCode;
        } catch (ProtocolException protocolException) {
            protocolException.printStackTrace();
        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return "Error during HTTP PUT request to url " + params[0];
    }
}

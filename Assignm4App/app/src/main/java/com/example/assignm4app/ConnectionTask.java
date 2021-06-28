/*
 * This class handles READ Restful operation via GET HTTP method
 * to display current data from the web server into the application feed.
 */
package com.example.assignm4app;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionTask extends AsyncTask<String, Void, String> {

    private TextView cardsView;

    /** Initialising the constructor */
    public ConnectionTask(TextView cardsView)
    {
        this.cardsView = cardsView;
    }

    protected String doInBackground(String... params) {

        if (params.length == 0)
        {
            return "No URL provided";
        }
        try
        {
            URL urlConnection = new URL(params[0]);
            HttpURLConnection conn
                    = (HttpURLConnection) urlConnection.openConnection();
            conn.setReadTimeout(3000); // 3 seconds
            conn.setConnectTimeout(3000); // 3 seconds
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                BufferedReader br = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder xmlResponse = new StringBuilder();
                String line = br.readLine();
                while (line != null)
                {
                    xmlResponse.append(line);
                    line = br.readLine();
                }
                br.close();
                conn.disconnect();

                if (xmlResponse.length()==0)
                    return "Empty response";

                /** Gathers all data from the web server */
                StringBuilder cardList = new StringBuilder();
                int cardIndex = xmlResponse.indexOf("<NFCCard>");
                while (cardIndex >= 0)
                {
                    int cNameStartIndex
                            = xmlResponse.indexOf("<cardName>", cardIndex) + 10;
                    int cNameEndIndex
                            = xmlResponse.indexOf("</", cNameStartIndex);
                    String cardName = (cNameEndIndex > cNameStartIndex) ?
                            xmlResponse.substring(cNameStartIndex,
                                    cNameEndIndex) : "No card name";
                    int cNumberStartIndex = xmlResponse.indexOf
                            ("<cardNumber>", cNameEndIndex) + 12;
                    int cNumberEndIndex = xmlResponse.indexOf
                            ("</", cNumberStartIndex);
                    String cardNumber
                            = (cNumberEndIndex > cNumberStartIndex) ?
                            xmlResponse.substring(cNumberStartIndex,
                                    cNumberEndIndex) : "No card number";
                    cardList.append("card Name: ").append(cardName)
                            .append(", card Number: ").append(cardNumber)
                            .append("\n");
                    cardIndex = xmlResponse.indexOf("<NFCCard>", cardIndex+1);
                }
                return cardList.toString();
            }
            else
                return "HTTP Response code " + responseCode;
        }
        catch (MalformedURLException e)
        {
            Log.e("ConnectionTask", "Malformed URL: " + e);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            Log.e("ConnectionTask", "IOException: " + e);
            e.printStackTrace();
        }
        return "Error during HTTP request to url " + params[0];
    }

    protected void onPostExecute(String workerResult)
    {
        cardsView.setText(workerResult);
    }
}

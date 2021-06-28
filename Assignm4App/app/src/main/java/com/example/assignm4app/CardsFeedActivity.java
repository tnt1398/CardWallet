/*
 * This class establishes the web server connection and
 * redirection to various app activities, such as delete and add.
 */

package com.example.assignm4app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CardsFeedActivity extends AppCompatActivity implements OnClickListener {

    private TextView cardsView;
    public FloatingActionButton addCard;
    public Button deleteCard;
    private String urlConnection;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardsfeed_activity);

        addCard = findViewById(R.id.addCard);
        deleteCard = findViewById(R.id.deleteCard);
        addCard.setOnClickListener(this);
        deleteCard.setOnClickListener(this);

        /** Establishing connection with the web server and collecting the existing data */
        cardsView = (TextView) findViewById(R.id.cards_view);
        urlConnection = "http://192.168.1.62:8080/Assignm4Web/NFCCardServer/NFCCards/";
        ConnectionTask task = new ConnectionTask(cardsView);
        task.execute(urlConnection);
    }

    /**
     * Action listener on both add and delete button for activity navigation
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addCard:
                Intent addIntent = new Intent(CardsFeedActivity.this, AddCardActivity.class);
                startActivity(addIntent);
            case R.id.deleteCard:
                Intent deleteIntent = new Intent(CardsFeedActivity.this, DeleteCardActivity.class);
                startActivity(deleteIntent);
                break;
        }
    }
}

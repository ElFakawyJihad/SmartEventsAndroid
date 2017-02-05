package com.example.jihad.smartevents;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jihad.smartevents.model.Message;
import com.example.jihad.smartevents.rest.UserRest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends Activity {
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;

    static final String SHARED_PREFERENCES_NAME = "shared_prefs";

    LinearLayout chatLayout;
    LinearLayout.LayoutParams lParams;

    EditText etMessage;
    Button btSend;

    ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatLayout = (LinearLayout)findViewById(R.id.chat_view);
        lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        etMessage = (EditText) findViewById(R.id.etMessage);
        btSend = (Button) findViewById(R.id.btSend);

        messages = new ArrayList<>();

        /* remplissage rapide */
        /* Normalement se fait avec la Bdd, donc à récupérer */
        String result = UserRest.getEventMessages(1 + "");
        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");

            if(message.equals("OK")) {
                JSONArray comments = jsonObject.getJSONArray("result");

                for(int e = 0; e < comments.length(); e++) {
                    JSONObject comment = comments.getJSONObject(e);
                    Message m = new Message();
                    m.setCreatedAt(Calendar.getInstance().getTime());
                    m.setText(comment.getString("text"));

                    messages.add(m);
                }
                Toast.makeText(this, "ACCES aux commentaires de l'event", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "ECHEC => ACCES aux commentaires de l'event", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        for(int i = 0; i < 2; i++) {
            Message m = new Message();
            m.setCreatedAt(Calendar.getInstance().getTime());
            m.setText("remplissage rapide " + i);
            messages.add(m);
        }
        */

        try {
            initChat();
        } catch (Exception e) {
            e.printStackTrace();
        }


        setBoutonSend();
    }

    private void initChat() throws IOException{
        chatLayout.removeAllViews();

        for(int i = 0; i < messages.size(); i++) {
            LinearLayout messageLayout = new LinearLayout(this);

            TextView tx = new TextView(this);
            tx.setText(messages.get(i).getText());

            URL url = new URL("http://www.gravatar.com/avatar/" + 1 + "?d=identicon");
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            ImageView image = new ImageView(this);
            image.setImageBitmap(bmp);


            if(MainActivity.USER_EMAIL.equals(messages.get(i).getUserEmail())) {
                messageLayout.addView(tx);
                messageLayout.addView(image);
            } else {
                messageLayout.addView(image);
                messageLayout.addView(tx);
            }




            chatLayout.addView(messageLayout, lParams);
        }
    }


    void setBoutonSend() {

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text = etMessage.getText().toString();
                if(text.length() <= 0) return;

                Message message = new Message();
                message.setCreatedAt(Calendar.getInstance().getTime());
                message.setText(text);
                message.setUserEmail(MainActivity.USER_EMAIL);

                messages.add(message);

                //Ajouter la base de données


                etMessage.setText(null);

                try {
                    initChat();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

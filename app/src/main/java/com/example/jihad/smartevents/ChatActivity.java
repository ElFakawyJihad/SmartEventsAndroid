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

import com.example.jihad.smartevents.Constantes.ConstantesRest;
import com.example.jihad.smartevents.model.Message;
import com.example.jihad.smartevents.rest.UserRest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

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

        /* remplissage */
        String result = UserRest.getEventMessages(1 + "");
        try {
            JSONObject jsonObject = new JSONObject(result);
            String message = jsonObject.getString("message");

            if(message.equals("OK")) {
                JSONArray comments = jsonObject.getJSONArray("result");

                for(int e = 0; e < comments.length(); e++) {
                    JSONObject comment = comments.getJSONObject(e);
                    Message m = new Message();
                    //m.setCreatedAt(new Date(comment.getString(ConstantesRest.MESSAGE_DATE)));
                    m.setText(comment.getString(ConstantesRest.MESSAGE_CONTENT));
                    m.setUserEmail(comment.getString(ConstantesRest.MESSAGE_USER_EMAIL));


                    messages.add(m);
                }
                Toast.makeText(this, "ACCES aux commentaires de l'event", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "ECHEC => ACCES aux commentaires de l'event", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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


            Toast.makeText(this, messages.get(i).getUserEmail(), Toast.LENGTH_SHORT);
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
                Date now = Calendar.getInstance().getTime();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                String text = etMessage.getText().toString();
                if(text.length() <= 0) return;

                Message message = new Message();
                message.setCreatedAt(now);
                message.setText(text);
                message.setUserEmail(MainActivity.USER_EMAIL);

                messages.add(message);

                //Ajouter la base de données
                String result = UserRest.addEventMessage("1", message.getText(), message.getUserEmail(), timeStamp);
                Toast.makeText(ChatActivity.this, Calendar.getInstance().getTime().toString()+"", Toast.LENGTH_LONG).show();
                Toast.makeText(ChatActivity.this, result, Toast.LENGTH_LONG).show();

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

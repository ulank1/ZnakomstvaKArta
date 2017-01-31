package com.example.ulan.znakomstvakarta;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignInActivity extends AppCompatActivity {
    EditText username,password;
    String TAG="TAG";
    DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        dataHelper=new DataHelper(this);
        username=(EditText) findViewById(R.id.edit_sign_in_username);
        password=(EditText) findViewById(R.id.edit_sign_in_password);


    }

    public void onClickSignIn(View view) {

        new ParseTask().execute();


    }


    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";

        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = new URL("https://looking-bishkek.herokuapp.com/api/v1/user1/?format=json&username="+username.getText().toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                jsonResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            JSONObject dataJsonObject;
            int total_count;
            User user;
            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");

                JSONObject meta = dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");
                Log.e(TAG+"Total",total_count+"");
                JSONObject menu;
                String id;
                String passwordJson;
                if (total_count==1) {
                    user = new User();
                    dataHelper.deleteUser();
                    menu = menus.getJSONObject(0);

                    user.setId(menu.getInt("id"));
                    user.setDescription(menu.getString("description"));
                    user.setEmail(menu.getString("email"));
                    user.setName(menu.getString("name"));
                    user.setPassword(menu.getString("password"));
                    user.setPhone(menu.getString("phone_number"));
                    user.setSurname(menu.getString("surname"));
                    user.setUsername(menu.getString("username"));
                    dataHelper.addUser(user);

                    finish();

                }else {
                    username.setError("ТАКОГО ЛОГИНА НЕ СУЩЕСТВУЕТ");
                }




            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG", "JSON_PIZDEC");
            }

        }
    }
}



package com.example.ulan.znakomstvakarta;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {
    EditText name,surname,username,password,description,number,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=(EditText) findViewById(R.id.edit_reg_name);
        surname=(EditText) findViewById(R.id.edit_reg_surname);
        username=(EditText) findViewById(R.id.edit_reg_username);
        password=(EditText) findViewById(R.id.edit_reg_password);
        description=(EditText) findViewById(R.id.edit_reg_description);
        number=(EditText) findViewById(R.id.edit_reg_phone_number);
        email=(EditText) findViewById(R.id.edit_reg_email);

    }

    public void onSignUp(View view) {

    boolean bool=true;

        final String email1 = email.getText().toString();
        if (!isValidEmail(email1)) {
            email.setError("Неверный адрес электронной почты");
            bool = false;

        }



        if (bool == true) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("name", name.getText().toString());
                obj.put("surname", surname.getText().toString());
                obj.put("phone_number", number.getText().toString());
                obj.put("email", email1);
                obj.put("description", description.getText().toString());
                obj.put("username", username.getText().toString());
                obj.put("password", password.getText().toString());

                Log.e("TAG", "asd");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            new SendJsonDataToServer().execute(String.valueOf(obj));

        }


    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    class SendJsonDataToServer extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String JsonResponse = null;
            String JsonDATA = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL("https://looking-bishkek.herokuapp.com/api/v1/user1/");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");

                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                try {
                    writer.write(JsonDATA);
                } catch (Exception e) {
                    Log.e("TAG", "Error");
                }
                writer.close();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                Log.e("TAG", "asdfasd");
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    return null;
                }
                JsonResponse = buffer.toString();
                Log.e("TAG", JsonResponse);
                return JsonResponse;


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

            }
            return "";

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            finish();
        }
    }


}

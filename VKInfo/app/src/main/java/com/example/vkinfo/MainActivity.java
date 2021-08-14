package com.example.vkinfo;

import static com.example.vkinfo.utils.NetworkUtils.generateURL;
import static com.example.vkinfo.utils.NetworkUtils.getResponseFromURL;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText searchField;
    private TextView textViewResult;
    private TextView errorMessage;
    private ProgressBar loadingIndicator;

    class VKQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            String firstname = null;
            String lastName = null;

            if (response != null && !"".equals(response)) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("response");
                    JSONObject jsonUserInfo = jsonArray.getJSONObject(0);

                    firstname = jsonUserInfo.getString("first_name");
                    lastName = jsonUserInfo.getString("last_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String resultingString = "Name: " + firstname + "\n" + "Last name: " + lastName;
                textViewResult.setText(resultingString);

                showResultTextView();
            } else {
                showErrorTextView();
            }

            loadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.editTextSearchField);
        textViewResult = findViewById(R.id.textViewResult);
        errorMessage = findViewById(R.id.textViewError);
        loadingIndicator = findViewById(R.id.pbLoadingIndicator);
    }

    public void onClickSearchVk(View view) {
        URL generatedUrl = generateURL(searchField.getText().toString());

        new VKQueryTask().execute(generatedUrl);
    }

    private void showResultTextView() {
        textViewResult.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorTextView() {
        textViewResult.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }
}
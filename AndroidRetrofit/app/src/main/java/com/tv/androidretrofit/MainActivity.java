package com.tv.androidretrofit;

import android.app.Activity;
import android.app.ProgressDialog;
import retrofit.RestAdapter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String BASE_URL = "http://bebetrack.com/";
    private Button callBtn;
    private EditText phoneET;
    private EditText codeET;
    private ResponseBean data;
    private TextView responseTV;
    private ProgressDialog loading;
    private SharedPreferences _spref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callBtn=(Button)findViewById(R.id.callBtn);
        _spref = getSharedPreferences("my_pref", MODE_PRIVATE);
        callBtn.setOnClickListener(this);
        phoneET=(EditText)findViewById(R.id.phoneET);
        codeET=(EditText)findViewById(R.id.codeET);
        responseTV=(TextView)findViewById(R.id.responseTV);



    }
    private void create(){

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .build();

        //Creating an object of our api interface
        AuthenticationAPI api = adapter.create(AuthenticationAPI.class);

        //Defining the method
          data= api.create(new RequestBean(Integer.parseInt(phoneET.getText().toString().trim()),Integer.parseInt(codeET.getText().toString())));
        System.out.println("REPSONSE"+data.getToken()+data.getPin());
        SharedPreferences.Editor prefEditor = _spref.edit();
        prefEditor.putString("Token", data.getToken());
        prefEditor.commit();
        runOnUiThread(new Runnable() {
            public void run() {
                // runs on UI thread
                responseTV.setText("PIN :" + data.getPin());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.callBtn:
                hideSoftKeyboard();
                if(!(codeET.getText().toString().trim().equalsIgnoreCase("")) && (!phoneET.getText().toString().trim().equalsIgnoreCase("")) && codeET.getText().toString().trim().length()==3)
                {
                    if (NetworkAvailablity.checkNetworkStatus(MainActivity.this)) {
                        new TestAsync().execute();

                    } else {

                        Toast.makeText(MainActivity.this,"Please check your network connection.",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    responseTV.setText("PIN :");
                    Toast.makeText(MainActivity.this,"Please enter valid details.",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
    class TestAsync extends AsyncTask<Void, Integer, String>
    {
        protected void onPreExecute (){
             loading = ProgressDialog.show(MainActivity.this,"Fetching Data","Please wait...",false,false);
        }

        protected String doInBackground(Void...arg0) {

            create();
            return "";
        }

        protected void onProgressUpdate(Integer...a){

        }

        protected void onPostExecute(String result) {
            loading.cancel();

        }
    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0); } }
}

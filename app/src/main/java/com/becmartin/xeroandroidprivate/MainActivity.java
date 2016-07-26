package com.becmartin.xeroandroidprivate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.SignatureType;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuthService;

import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private XeroAPI api;
    private OAuth10aService service;
    private OAuth1AccessToken accessToken;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //call the async task
                new getOrganisation().execute();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mContent = (TextView) findViewById(R.id.contentText);

        //Xero Api initialisation
        api = new XeroAPI(getApplicationContext());
        service = new ServiceBuilder()
                .apiKey(getString(R.string.api_key))
                .build(api);

        accessToken = new OAuth1AccessToken(getString(R.string.api_key), getString(R.string.api_secret));



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Android requires this to be on a separate thread
    private class getOrganisation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String...params) {
            OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.xero.com/api.xro/2.0/Organisation", service);
            request.addHeader("Accept", "application/json");
            service.signRequest(accessToken, request);

            Log.d("Status", "Requesting.......");

            String responseString = "";

            try {
                Response response = request.send();
                responseString = response.getBody().toString();
                return responseString;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Exception", "Xero " + e.getLocalizedMessage());
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result){
            Log.d("Xero Result", "Xero Result body successful" + result);
            mContent.setText(result);
//            try {
//                JSONObject myJson = new JSONObject(result);
//                String name = myJson.optString("screen_name");
//                Log.v("name", name);
//                // set the text to show my name
//                TextView text = (TextView) findViewById(R.id.text1);
//
//                // set info
//                SharedPreferences settings = getSharedPreferences(Constants.PREFS_NAME, 0);
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putString("screen_name", name);
//                editor.commit();
//
//
//                text.setText("Howdy @" + name);
//                // hide the login button
//                ImageButton loginButton = (ImageButton) findViewById(R.id.button);
//                loginButton.setVisibility(View.GONE);
//
//            } catch (JSONException e) {
//                Log.e("JSONObject", e.getMessage());
//            }
        }
    }
}

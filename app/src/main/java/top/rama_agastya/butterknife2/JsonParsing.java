package top.rama_agastya.butterknife2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JsonParsing extends AppCompatActivity {

    // URL to get contacts JSON
    private static String url = "http://192.168.137.1/prov.json";
    ArrayList<HashMap<String, String>> contactList;
    @BindView(R.id.wilayah)
    TextView tvWilayah;
    private String TAG = JsonParsing.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_parsing);
        ButterKnife.bind(this);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(JsonParsing.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray prov = jsonObj.getJSONArray("data");

                    // looping through All Contacts
                    for (int i = 0; i < prov.length(); i++) {
                        JSONObject p = prov.getJSONObject(i);

                        String id = p.getString("wilayah_id");
                        String nama = p.getString("nama");
                        String singkatan = p.getString("singkatan");
                        String tingkat = p.getString("tingkat");
                        //String address = c.getString("address");
                        //String gender = c.getString("gender");

                        // Phone node is JSON Object
                        //JSONObject phone = c.getJSONObject("phone");
                        // String mobile = p.getString("wilayah_id");
                        //String home = phone.getString("home");
                        //String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", "Wil. ID : " + id);
                        contact.put("nama", nama);
                        contact.put("singkatan", "     Singkatan   : " + singkatan);
                        if (tingkat.equals("1"))
                            contact.put("tingkat", "     Wil. Tingkat : Provinsi");
                        else
                            contact.put("tingkat", "     Wil. Tingkat : NULL");
                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    JsonParsing.this,
                    contactList,
                    R.layout.list_layout,
                    new String[]{"nama", "id", "singkatan", "tingkat"},
                    new int[]{R.id.nama, R.id.id, R.id.singkatan, R.id.tingkat});
            tvWilayah.setText("Seluruh Provinsi Indonesia");
            lv.setAdapter(adapter);

        }

    }
}

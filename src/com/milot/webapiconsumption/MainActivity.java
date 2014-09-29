package com.milot.webapiconsumption;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends ActionBarActivity {

	private static TextView txt;
	private static EditText edtQyteti;
	private static Button btnKerko;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			txt = (TextView) rootView.findViewById(R.id.txt);
			edtQyteti = (EditText) rootView.findViewById(R.id.edtQyteti);
			btnKerko = (Button) rootView.findViewById(R.id.btnKerko);
			
			btnKerko.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					RequestQueue queue = Volley.newRequestQueue(getActivity());
					String url = "http://api.openweathermap.org/data/2.5/weather?q=" + edtQyteti.getText().toString() + "&units=metric";
					
					JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							
							try {
								Log.e("RESPONSE", response.toString());
								String temperatura = response.getJSONObject("main").getString("temp").toString();
								String qyteti = response.getString("name").toString();
								String gjendja = response.getJSONArray("weather").getJSONObject(0).getString("main");
								txt.setText("Temperatura " + temperatura + "\nQyteti: " + qyteti + "\nGjendja: " + gjendja);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub

						}
					});

					queue.add(jsObjRequest);
					
				}
			});
			return rootView;
		}
	}

}

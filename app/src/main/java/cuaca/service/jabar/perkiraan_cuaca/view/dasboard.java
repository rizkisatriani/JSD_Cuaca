package cuaca.service.jabar.perkiraan_cuaca.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cuaca.service.jabar.perkiraan_cuaca.adapter.Adapter_Rc_cuaca;
import cuaca.service.jabar.perkiraan_cuaca.config.helper;
import cuaca.service.jabar.perkiraan_cuaca.config.router_api;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import cuaca.service.jabar.perkiraan_cuaca.R;
import cuaca.service.jabar.perkiraan_cuaca.config.RequestHandler;
import cuaca.service.jabar.perkiraan_cuaca.model.all_data;
import cuaca.service.jabar.perkiraan_cuaca.model.attr_cuaca;
import cuaca.service.jabar.perkiraan_cuaca.model.detil_cuaca;
import cuaca.service.jabar.perkiraan_cuaca.model.temperatur;

public class dasboard extends AppCompatActivity {
    String zip;
    helper helper;
    ArrayList<all_data> data_cuaca;
    TextView edkota, ed_derajat, ed_angin, ed_tekanan, ed_kelembaban, ed_feel, ed_cuaca, tv_sapaan;
    RecyclerView recyclerView;
    Adapter_Rc_cuaca adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);
        edkota = findViewById(R.id.tv_kota);
        ed_derajat = findViewById(R.id.tv_derajat);
        tv_sapaan = findViewById(R.id.tv_sapaan);
        ed_cuaca = findViewById(R.id.tv_cuaca);
        ed_angin = findViewById(R.id.ed_angin);
        ed_tekanan = findViewById(R.id.ed_tekanan);
        ed_kelembaban = findViewById(R.id.ed_kelembaban);
        ed_feel = findViewById(R.id.ed_fell);
        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sapaan();
        helper = new helper(this);
        helper.loading_show();
        zip = getIntent().getStringExtra("zip");
        getdata_json();


    }

    public void getdata_json() {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("url", "hasli :" + s);
                generate_Json(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("appid", getResources().getString(R.string.Key_api));
                param.put("zip", zip);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(router_api.API_URL, param);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void generate_Json(String Json) {
        JSONObject jsonObject;
        DecimalFormat decimalFormat = new DecimalFormat("##.#");
        try {
            data_cuaca = new ArrayList<>();
            jsonObject = new JSONObject(Json);
            JSONObject city = jsonObject.getJSONObject("city");
            edkota.setText("" + city.getString("name"));
            JSONArray list = jsonObject.getJSONArray("list");
            double celcius, kelvin, celciusfell, kelvinfell;
            kelvin = Double.parseDouble(list.getJSONObject(0).getJSONObject("main").getString("temp"));
            celcius = kelvin - 273.15;
            kelvinfell = Double.parseDouble(list.getJSONObject(0).getJSONObject("main").getString("feels_like"));
            celciusfell = kelvinfell - 273.15;
            ed_derajat.setText("" + decimalFormat.format(celcius) + " \u2103");
            ed_cuaca.setText("" + list.getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main"));
            ed_angin.setText("" + list.getJSONObject(0).getJSONObject("wind").getString("speed"));
            ed_kelembaban.setText("" + list.getJSONObject(0).getJSONObject("main").getString("humidity") + "%");
            ed_tekanan.setText("" + list.getJSONObject(0).getJSONObject("main").getString("pressure"));
            ed_feel.setText("" + decimalFormat.format(celciusfell) + " \u2103");
            JSONObject rain = new JSONObject();

            for (int i = 0; i < list.length(); i++) {
                JSONObject jo = list.getJSONObject(i);
                JSONObject main = jo.getJSONObject("main");
                kelvin = Double.parseDouble(list.getJSONObject(i).getJSONObject("main").getString("temp"));
                kelvinfell = Double.parseDouble(list.getJSONObject(i).getJSONObject("main").getString("feels_like"));
                celciusfell = kelvinfell - 273.15;
                celcius = kelvin - 273.15;
                JSONArray weather = jo.getJSONArray("weather");
                JSONObject clouds = jo.getJSONObject("clouds");
                JSONObject wind = jo.getJSONObject("wind");
                if (!jo.isNull("rain")) rain = jo.getJSONObject("rain");
                String dt_txt = jo.getString("dt_txt");

                data_cuaca.add(new all_data(
                        weather.getJSONObject(0).getString("id"),
                        weather.getJSONObject(0).getString("main"),
                        weather.getJSONObject(0).getString("description"),
                        weather.getJSONObject(0).getString("icon"),
                        clouds.getString("all"),
                        wind.getString("speed"),
                        wind.getString("deg"),
                        (!jo.isNull("rain")) ? rain.getString("3h") : "",
                        dt_txt,
                        "" + decimalFormat.format(celcius) + " \u2103",
                        "" + decimalFormat.format(celciusfell) + " \u2103",
                        main.getString("temp_min"),
                        main.getString("temp_max"),
                        main.getString("pressure"),
                        main.getString("sea_level"),
                        main.getString("grnd_level"),
                        main.getString("humidity"),
                        main.getString("temp_kf")
                ));

            }
            Collections.sort(data_cuaca, new Short());
            adapter = new Adapter_Rc_cuaca(data_cuaca, dasboard.this);
            recyclerView.setAdapter(adapter);
            helper.loading_dismiss();
        } catch (JSONException e) {
            helper.loading_dismiss();
            e.printStackTrace();
        }

    }

    void sapaan() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            tv_sapaan.setText(Html.fromHtml("Selamat Pagi, <b>" + getIntent().getStringExtra("nama") + "</b>"));
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            tv_sapaan.setText(Html.fromHtml("Selamat Siang, <b>" + getIntent().getStringExtra("nama") + "</b>"));
        } else if (timeOfDay >= 16 && timeOfDay < 18) {
            tv_sapaan.setText(Html.fromHtml("Selamat Sore, <b>" + getIntent().getStringExtra("nama") + "</b>"));
        } else if (timeOfDay >= 18 && timeOfDay < 24) {
            tv_sapaan.setText(Html.fromHtml("Selamat Malam, <b>" + getIntent().getStringExtra("nama") + "</b>"));
        }
    }

    class Short implements Comparator<all_data> {
        @Override
        public int compare(all_data o1, all_data o2) {
            String v1 = o1.getDt_txt();
            String v2 = o2.getDt_txt();
            return v1.compareTo(v2);

        }
    }


}

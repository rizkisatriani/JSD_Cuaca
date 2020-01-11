package cuaca.service.jabar.perkiraan_cuaca.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cuaca.service.jabar.perkiraan_cuaca.R;
import cuaca.service.jabar.perkiraan_cuaca.model.all_data;
import cuaca.service.jabar.perkiraan_cuaca.config.router_api;
import cuaca.service.jabar.perkiraan_cuaca.model.detil_cuaca;
import cuaca.service.jabar.perkiraan_cuaca.model.temperatur;


public class Adapter_Rc_cuaca extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<all_data> data_cuaca;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat dateFormatter2;
    private SimpleDateFormat postFormatter;
    private SimpleDateFormat timeFormatter;
    public Context mcontex;
    TextView tv_hari, tv_tgl1, tv_tgl2, tvdeg, tv_angin, tvlembab, tv_jam;
    ImageView img_icon;

    public Adapter_Rc_cuaca(ArrayList<all_data> data_cuaca, Context mcontex) {
        Locale locale = new Locale("ID");
        this.data_cuaca = data_cuaca;
        this.mcontex = mcontex;
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", locale);
        dateFormatter2 = new SimpleDateFormat("dd-MMMM", locale);
        timeFormatter = new SimpleDateFormat("hh:mm:ss", locale);
        postFormatter = new SimpleDateFormat("EEEE", locale);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontex).inflate(R.layout.item_cuaca, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Date tgl = null;
        try {
            tgl = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(data_cuaca.get(position).getDt_txt());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tv_hari.setText("" + postFormatter.format(tgl));
        tv_tgl1.setText("" + dateFormatter.format(tgl));
        tvdeg.setText(data_cuaca.get(position).getTemp());
        tv_angin.setText(data_cuaca.get(position).getWind_speed() + "m/d");
        tvlembab.setText(data_cuaca.get(position).getHumidity() + "%");
        tv_jam.setText("" + timeFormatter.format(tgl));
        tv_tgl2.setText("" + dateFormatter2.format(tgl));
        Glide.with(mcontex).load(router_api.URL_ICON + data_cuaca.get(position).getIcon() + ".png")
                .placeholder(mcontex.getResources().getDrawable(R.drawable.jsd_icon)).into(img_icon);
    }

    @Override
    public int getItemCount() {
        return data_cuaca.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_hari = itemView.findViewById(R.id.tv_hari);
            tv_tgl1 = itemView.findViewById(R.id.tv_tgl_head);
            tv_tgl2 = itemView.findViewById(R.id.tv_tgl_sub);
            tvdeg = itemView.findViewById(R.id.tv_deg);
            tv_angin = itemView.findViewById(R.id.tv_angin);
            tvlembab = itemView.findViewById(R.id.tv_lembab);
            tv_jam = itemView.findViewById(R.id.tv_jam);
            img_icon = itemView.findViewById(R.id.img_icon);
        }
    }
}

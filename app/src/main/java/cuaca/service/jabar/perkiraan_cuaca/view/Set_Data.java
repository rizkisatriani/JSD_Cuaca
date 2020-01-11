package cuaca.service.jabar.perkiraan_cuaca.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import cuaca.service.jabar.perkiraan_cuaca.R;

public class Set_Data extends AppCompatActivity {
EditText ed_zip,ed_nama;
Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__data);
        ed_nama=findViewById(R.id.ed_nama);
        ed_zip=findViewById(R.id.ed_kode);
        btn_submit=findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Set_Data.this,dasboard.class);
                i.putExtra("nama",ed_nama.getText().toString());
                i.putExtra("zip",ed_zip.getText().toString());
                startActivity(i);
            }
        });
    }

}

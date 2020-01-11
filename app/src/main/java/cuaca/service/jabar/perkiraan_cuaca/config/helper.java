package cuaca.service.jabar.perkiraan_cuaca.config;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class helper {
    Context context;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat postFormatter;
    ProgressDialog loading;

    public helper(Context context) {
        this.context = context;
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        postFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    }

    public String DateSqltoDisplay(String date) {
        Date tgl = null;
        try {
            tgl = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = "" + dateFormatter.format(tgl);
        return result;
    }

    public String DateDisplayToSql(String date) {
        Date tgl = null;
        try {
            tgl = new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = "" + postFormatter.format(tgl);
        return result;
    }

    public void alert_dialog(String Judul, String Pesan) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(Html.fromHtml("<b>" + Judul + "</b>"));
        alertDialogBuilder
                .setMessage(Html.fromHtml(Pesan))
                .setCancelable(false)
                .setNegativeButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void loading_show() {
        loading = new ProgressDialog(context);
        loading.setMessage("Silahkan tunggu...");
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.show();

    }

    public void loading_dismiss() {
        if (loading.isShowing()) loading.dismiss();
    }
}

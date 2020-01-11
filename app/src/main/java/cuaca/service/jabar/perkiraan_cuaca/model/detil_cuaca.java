package cuaca.service.jabar.perkiraan_cuaca.model;

public class detil_cuaca {
    String clouds_all, wind_speed, wind_deg,  rain,  dt_txt;

    public detil_cuaca(String clouds_all, String wind_speed, String wind_deg, String rain, String dt_txt) {
        this.clouds_all = clouds_all;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.rain = rain;
        this.dt_txt = dt_txt;
    }

    public String getClouds_all() {
        return clouds_all;
    }

    public void setClouds_all(String clouds_all) {
        this.clouds_all = clouds_all;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}

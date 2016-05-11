package it.gdaloiso.bikeshare;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by giuseppe on 10/05/16.
 */
public class Bici {
    private String nome;
    private Double latitudine;
    private Double longitudine;
    private String numero;
    private LatLng posizone;
    public Bici(String nome, Double lat, Double lon,String numero) {
        super();
        this.nome = nome;
        this.latitudine =lat;
        this.longitudine =lon;
        this.numero = numero;
    }
    public String getNome() {
        return nome;
    }
    public String getNumero() {
        return numero;
    }
    public double getLatitude(){
        return latitudine;
    }
    public double getLongitudine(){
        return longitudine;
    }
    public LatLng getPosition(){
        LatLng pos = new LatLng(latitudine,longitudine);
        return pos;
    }
}

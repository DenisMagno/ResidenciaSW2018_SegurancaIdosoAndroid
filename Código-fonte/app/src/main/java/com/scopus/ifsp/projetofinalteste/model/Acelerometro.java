package com.scopus.ifsp.projetofinalteste.model;

/**
 * @author Denis Magno
 */
public class Acelerometro {
    private Float xAxis;
    private Float yAxis;
    private Float zAxis;
    private Long tempo;
    private Double magnitudeAceleracao;

    public Acelerometro() {
        this.xAxis = Float.valueOf(0);
        this.yAxis = Float.valueOf(0);
        this.zAxis = Float.valueOf(0);
    }

    public Acelerometro(Float xAxis, Float yAxis, Float zAxis, Long tempo) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.zAxis = zAxis;
        this.tempo = tempo;
        this.magnitudeAceleracao = this.calculaMagnitudeAceleracao();
    }

    /**
     * Calcula a magnitude da aceleração da possível queda utilizando dados do acelerômetro.
     *
     * @return Magnitude da aceleração da possível queda.
     * @author Denis Magno.
     */
    private Double calculaMagnitudeAceleracao() {
        //Calcula magnitude da queda.
        Double magnitude = Math.sqrt(Math.pow(this.xAxis, 2) +
                Math.pow(this.yAxis, 2) +
                Math.pow(this.zAxis, 2));

        //Calcula aceleração da magnitude da queda.
        Double aceleracao = magnitude / 9.80665;

        return aceleracao;
    }

    public Double getMagnitudeAceleracao() {
        return magnitudeAceleracao;
    }

    public Float getxAxis() {
        return xAxis;
    }

    public void setxAxis(Float xAxis) {
        this.xAxis = xAxis;
    }

    public Float getyAxis() {
        return yAxis;
    }

    public void setyAxis(Float yAxis) {
        this.yAxis = yAxis;
    }

    public Float getzAxis() {
        return zAxis;
    }

    public void setzAxis(Float zAxis) {
        this.zAxis = zAxis;
    }

    public Long getTempo() {
        return tempo;
    }

    public void setTempo(Long tempo) {
        this.tempo = tempo;
    }
}
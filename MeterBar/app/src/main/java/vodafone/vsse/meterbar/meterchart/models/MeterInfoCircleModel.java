package vodafone.vsse.meterbar.meterchart.models;

import android.graphics.drawable.Drawable;

import vodafone.vsse.meterbar.meterchart.views.MeterBar;

/**
 * Created by AboElEla on 3/27/2016.
 *
 * This is the model for circle view in Meter view
 */
public class MeterInfoCircleModel {

    public MeterInfoCircleModel(){}

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public float getInfoCircleMargin() {
        return infoCircleMargin;
    }

    public void setInfoCircleMargin(float infoCircleMargin) {
        this.infoCircleMargin = infoCircleMargin;
    }

    public float getInfoCircleDetailsRadius() {
        return infoCircleDetailsRadius;
    }

    public float getInfoCircleRadius() {
        return infoCircleRadius;
    }

    public void setInfoCircleDetailsRadius(float infoCircleDetailsRadius) {
        this.infoCircleDetailsRadius = infoCircleDetailsRadius;
    }

    public void setInfoCircleRadius(float infoCircleRadius) {
        this.infoCircleRadius = infoCircleRadius;
    }

    public void setCaptionText(MeterText captionText) {
        this.captionText = captionText;
    }

    public MeterText getCaptionText() {
        return captionText;
    }

    public void setDetailsText(MeterText detailsText) {
        this.detailsText = detailsText;
    }

    public MeterText getDetailsText() {
        return detailsText;
    }

    private MeterText captionText;
    private MeterText detailsText;

    private float infoCircleRadius = 10f;
    private float infoCircleDetailsRadius = 20f;

    private int backgroundColor = 0xFFFFFFFF;

    private float infoCircleMargin = 10f;

}

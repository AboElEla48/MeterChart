package vodafone.vsse.meterbar.meterchart.models;

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

    public int getInfoCircleCurrentUnits() {
        return infoCircleCurrentUnits;
    }

    public void setInfoCircleCurrentUnits(int infoCircleCurrentUnits) {
        this.infoCircleCurrentUnits = infoCircleCurrentUnits;
    }

    public int getInfoCircleMaxUnits() {
        return infoCircleMaxUnits;
    }

    public void setInfoCircleMaxUnits(int infoCircleMaxUnits) {
        this.infoCircleMaxUnits = infoCircleMaxUnits;
    }

    public void setValueText(MeterValueText valueText) {
        this.valueText = valueText;
    }

    public MeterValueText getValueText() {
        return valueText;
    }

    public void setDetailsText(MeterText detailsText) {
        this.detailsText = detailsText;
    }

    public MeterText getDetailsText() {
        return detailsText;
    }

    private MeterText detailsText;

    private float infoCircleRadius = 10f;
    private float infoCircleDetailsRadius = 20f;

    private int backgroundColor = 0xFFFFFFFF;

    private float infoCircleMargin = 10f;
    private int infoCircleMaxUnits = 28;
    private int infoCircleCurrentUnits = 16;
    private MeterValueText valueText;

}

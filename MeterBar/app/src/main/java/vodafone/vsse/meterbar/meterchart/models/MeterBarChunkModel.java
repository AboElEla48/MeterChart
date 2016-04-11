package vodafone.vsse.meterbar.meterchart.models;

import android.graphics.Bitmap;


/**
 * Created by AboElEla on 3/27/2016.
 */
public class MeterBarChunkModel {

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUpText(MeterText upText) {
        this.upText = upText;
    }

    public MeterText getUpText() {
        return upText;
    }

    public void setDownText(MeterText downText) {
        this.downText = downText;
    }

    public MeterText getDownText() {
        return downText;
    }

    public void setMiddleText(MeterText middleText) {
        this.middleText = middleText;
    }

    public MeterText getMiddleText() {
        return middleText;
    }

    public void setMeterValueText(MeterValueText meterValueText) {
        this.meterValueText = meterValueText;
    }

    public MeterValueText getMeterValueText() {
        return meterValueText;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public boolean isGradientColor() {
        return isGradientColor;
    }

    public void setIsGradientColor(boolean isGradientColor) {
        this.isGradientColor = isGradientColor;
    }

    public int getColorStartGradient() {
        return colorStartGradient;
    }

    public int getColorEndGradient() {
        return colorEndGradient;
    }

    public void setColorStartGradient(int colorStartGradient) {
        this.colorStartGradient = colorStartGradient;
    }

    public void setColorEndGradient(int colorEndGradient) {
        this.colorEndGradient = colorEndGradient;
    }

    public void setMeterBarChunkHelper(MeterBarChunkHelper meterBarChunkHelper) {
        this.meterBarChunkHelper = meterBarChunkHelper;
    }

    public MeterBarChunkHelper getMeterBarChunkHelper() {
        return meterBarChunkHelper;
    }

    public boolean isHelperVisible() {
        return isHelperVisible;
    }

    public void setIsHelperVisible(boolean isHelperVisible) {
        this.isHelperVisible = isHelperVisible;
    }


    // Unique ID for chunk
    private int id;
    private MeterValueText meterValueText = null;
    private MeterText upText = new MeterText();
    private MeterText downText = new MeterText();
    private MeterText middleText = new MeterText();

    private MeterBarChunkHelper meterBarChunkHelper = new MeterBarChunkHelper();
    private boolean isHelperVisible;

    private double value;

    private int backgroundColor;
    private boolean isGradientColor;

    private int colorStartGradient;
    private int colorEndGradient;

}

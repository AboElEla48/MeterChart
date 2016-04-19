package vodafone.vsse.meterbar.meterchart.models;

/**
 * Created by AboElEla on 4/6/2016.
 *
 * This class represents the text criteria used for drawing Value
 */
public class MeterValueText {

    public MeterValueText()
    {}

    public MeterValueText(int fontSize, int color, int zeroTextColor,
                          boolean isBold, boolean isItalic)
    {
        setFontSize(fontSize);
        setTextColor(color);
        setZeroTextColor(zeroTextColor);
        setIsBold(isBold);
        setIsItalic(isItalic);
    }


    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setZeroTextColor(int zeroTextColor) {
        this.zeroTextColor = zeroTextColor;
    }

    public int getZeroTextColor() {
        return zeroTextColor;
    }

    public boolean isBold() {
        return isBold;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setIsBold(boolean isBold) {
        this.isBold = isBold;
    }

    public void setIsItalic(boolean isItalic) {
        this.isItalic = isItalic;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public float getMargin() {
        return margin;
    }

    protected int fontSize = 0;
    protected int textColor = 0xff000000;
    protected int zeroTextColor = 0xffff0000;
    protected boolean isBold = false;
    protected boolean isItalic = false;
    protected float margin;
}



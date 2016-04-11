package vodafone.vsse.meterbar.meterchart.models;

/**
 * Created by AboElEla on 4/6/2016.
 *
 * This class represents the text criteria used for drawing Value
 */
public class MeterValueText {

    public MeterValueText()
    {}

    public MeterValueText(String font, int fontSize, int color, float margin,
                          boolean isBold, boolean isItalic)
    {
        setFontName(font);
        setFontSize(fontSize);
        setTextColor(color);
        setIsBold(isBold);
        setIsItalic(isItalic);
        setMargin(margin);
    }


    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
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
    protected String fontName = "";
    protected boolean isBold = false;
    protected boolean isItalic = false;
    protected float margin;
}



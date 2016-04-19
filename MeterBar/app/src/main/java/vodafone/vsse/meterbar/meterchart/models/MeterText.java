package vodafone.vsse.meterbar.meterchart.models;

import android.graphics.Color;

/**
 * Created by AboElEla on 3/27/2016.
 *
 * This object represent item text with its font and properties
 *
 */
public class MeterText extends MeterValueText{

    public MeterText(){
        super();
    }

    public MeterText(String text, int fontSize, int color, int zeroTextColor, float margin,
                     boolean isBold, boolean isItalic)
    {
        super(fontSize, color, zeroTextColor, isBold, isItalic);
        setMargin(margin);
        setText(text);
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    private String text = "";


}

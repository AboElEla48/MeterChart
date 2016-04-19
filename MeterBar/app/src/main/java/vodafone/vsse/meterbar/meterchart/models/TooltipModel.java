package vodafone.vsse.meterbar.meterchart.models;


/**
 * Created by AboElEla on 3/27/2016.
 *
 * This model represents the data of the tooltip
 */
public class TooltipModel
{

    public TooltipModel()
    {}

    public TooltipModel(MeterText tooltipText, int tipColor)
    {
        setTooltipText(tooltipText);
        setTipColor(tipColor);
    }

    public void setTooltipText(MeterText tooltipText) {
        this.tooltipText = tooltipText;
    }

    public MeterText getTooltipText() {
        return tooltipText;
    }

    public void setTipColor(int tipColor) {
        this.tipColor = tipColor;
    }

    public int getTipColor() {
        return tipColor;
    }

    private MeterText tooltipText;
    private int tipColor;
}

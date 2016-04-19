package vodafone.vsse.meterbar.meterchart.models;

import java.util.List;

/**
 * Created by AboElEla on 3/27/2016.
 *
 * This is the model representing bar in meter chart
 *
 */
public class MeterBarModel {

    /**
     * get Bar Id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * set Bar Id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get accumlated values of chunks in bar
     *
     * @return
     */
    public double getChunksTotalValue() {

        double chunksTotalValue = 0;

        // Accumulate the total value of chunks in bar
        for (MeterBarChunkModel chunkModel : barChunks)
        {
            chunksTotalValue += chunkModel.getValue();
        }

        return chunksTotalValue;
    }

    /**
     * Get chunks list of bar
     * @return
     */
    public List<MeterBarChunkModel> getBarChunks() {
        return barChunks;
    }

    /**
     * Set chunks of bar
     *
     * @param barChunks
     */
    public void setBarChunks(List<MeterBarChunkModel> barChunks) {
        this.barChunks = barChunks;
    }

    /**
     * Set the max value of bar
     * @param barMaxValue
     */
    public void setBarMaxValue(double barMaxValue) {
        this.barMaxValue = barMaxValue;
    }

    /**
     * Get the max value of bar
     * @return
     */
    public double getBarMaxValue() {
        return barMaxValue;
    }

    /**
     * set Tool tip flag appears in zero level
     * @param isTooltipVisible
     */
    public void setIsTooltipVisible(boolean isTooltipVisible) {
        this.isTooltipVisible = isTooltipVisible;
    }

    /**
     * flag to know if tip visible or not
     * @return
     */
    public boolean isTooltipVisible() {
        return isTooltipVisible;
    }

    /**
     * set tool tip appears in zero level
     * @param barTooltipModel
     */
    public void setTooltipModel(TooltipModel barTooltipModel) {
        this.tooltipModel = barTooltipModel;
    }

    /**
     * get tool tip appears in zero level
     * @return
     */
    public TooltipModel getTooltipModel() {
        return tooltipModel;
    }


    //unique ID for bar
    private int id;

    // Hold list of chunks inside bar
    private List<MeterBarChunkModel> barChunks;

    // Hold the max value of bar
    private double barMaxValue = 0;

    // Hold tip appears on bar
    private TooltipModel tooltipModel;

    // On/Off flag for showing tip
    private boolean isTooltipVisible = false;
}

package vodafone.vsse.meterbar.meterchart.listeners;

/**
 * Created by AboElEla on 4/4/2016.
 *
 * This listener need to be implemented of listener would like to receive events on Chunk in bar
 */
public interface MeterChartListener {

    /**
     * Expose event that helper option in chunk is clicked
     * @param chunkID
     */
    void chunkIconClicked(int chunkID);

    /**
     * Expose event that chunk bar is clicked
     * @param chunkID
     */
    void chunkBarClicked(int chunkID);
}

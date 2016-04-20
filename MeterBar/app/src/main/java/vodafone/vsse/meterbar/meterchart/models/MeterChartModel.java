package vodafone.vsse.meterbar.meterchart.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.List;

import vodafone.vsse.meterbar.meterchart.listeners.MeterChartListener;


/**
 * Created by AboElEla on 3/27/2016.
 *
 * This is the model used to provide data for Meter View
 *
 */
public class MeterChartModel {
    public void setInfoCircleModel(MeterInfoCircleModel infoCircleModel) {
        this.infoCircleModel = infoCircleModel;
    }

    public MeterInfoCircleModel getInfoCircleModel() {
        return infoCircleModel;
    }

    public void setMeterBars(List<MeterBarModel> meterBars) {
        this.meterBars = meterBars;
    }

    public int getBarsMargin() {
        return barsMargin;
    }

    public void setBarsMargin(int barsMargin) {
        this.barsMargin = barsMargin;
    }

    public boolean isCircleVisible() {
        return isCircleVisible;
    }

    public void setIsCircleVisible(boolean isCircleVisible) {
        this.isCircleVisible = isCircleVisible;
    }

    public List<MeterBarModel> getMeterBars() {
        return meterBars;
    }

    public MeterChartListener getMeterChartListener() {
        return meterChartListener;
    }

    public void setMeterChartListener(MeterChartListener meterChartListener) {
        this.meterChartListener = meterChartListener;
    }

    public void setChartHandle(Bitmap handleImage, int chartHandleMargin, boolean isVisible)
    {
        this.chartHandle = handleImage;
        this.chartHandleMargin = chartHandleMargin;

        setChartHandleVisible(isVisible);
    }

    public int getChartHandleMargin()
    {
        return chartHandleMargin;
    }

    public Bitmap getChartHandle()
    {
        return chartHandle;
    }

    public void setChartHandleVisible(boolean isVisible)
    {
        isChartHandleVisible = isVisible;
    }

    public boolean isChartHandleVisible()
    {
        return isChartHandleVisible;
    }

    // Hold model for info circle
    MeterInfoCircleModel infoCircleModel = new MeterInfoCircleModel();

    // Define if the circle info is visible or not
    private boolean isCircleVisible;

    // Hold list of chart bars
    List<MeterBarModel> meterBars;

    // Define the margin between bars
    private int barsMargin;

    // Hold bitmap of the handler
    private Bitmap chartHandle;

    // Define the down margin of the handle
    private int chartHandleMargin = 10;

    // Define flag to show/hide chart handle image
    private boolean isChartHandleVisible = false;

    // Hold listener to chart
    private MeterChartListener meterChartListener;

}

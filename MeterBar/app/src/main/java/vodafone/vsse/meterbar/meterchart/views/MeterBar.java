package vodafone.vsse.meterbar.meterchart.views;

import android.graphics.Canvas;
import java.util.ArrayList;
import vodafone.vsse.meterbar.meterchart.listeners.MeterChartListener;
import vodafone.vsse.meterbar.meterchart.models.MeterBarChunkModel;
import vodafone.vsse.meterbar.meterchart.models.MeterBarModel;
import vodafone.vsse.meterbar.meterchart.utils.LogUtil;
import vodafone.vsse.meterbar.meterchart.utils.PaintUtil;


/**
 * Created by AboElEla on 3/27/2016.
 *
 * This view represents one bar in Meter Chart component
 */
public class MeterBar {

    /**
     * init bar with bar model
     *
     * @param meterBarModel
     */
    public MeterBar(MeterBarModel meterBarModel, MeterChartListener listener)
    {
        // Hold model
        this.meterBarModel = meterBarModel;
        this.meterChartListener = listener;
    }

    /**
     * Draw Bar
     *
     * @param canvas
     * @param x
     * @param barWidth
     */
    public void drawBar(Canvas canvas, final float x, final float barWidth)
    {
        this.canvas = canvas;
        startX = x;
        this.barWidth = barWidth;

        // draw chunks
        drawChunks();

    }

    /**
     * Draw bar chunks
     *
     */
    private void drawChunks()
    {
        LogUtil.logString("Draw Chunks");

        double heightUnitPerPixel = (double)canvas.getHeight() / meterBarModel.getBarMaxValue();
        float barHeight = (float) (meterBarModel.getChunksTotalValue() * heightUnitPerPixel);
        float y = canvas.getHeight() - barHeight;

        barY = y;

        LogUtil.logBar(meterBarModel);
        LogUtil.logString("Canvas Height = " + canvas.getHeight());
        LogUtil.logString("Units = " + heightUnitPerPixel);
        LogUtil.logString("Bar Height = " + barHeight);

        chunksList = new ArrayList<>();
        for(MeterBarChunkModel chunkModel : meterBarModel.getBarChunks())
        {
            MeterBarChunk chunk = new MeterBarChunk(chunkModel, meterChartListener);
            float chunkHeight = (float) (chunkModel.getValue() * heightUnitPerPixel);

            chunk.drawChunk(canvas, startX, barWidth, y, chunkHeight);
            y += chunkHeight;

            chunksList.add(chunk);
        }

        barBottom = y;
    }



    /**
     * Check if point inside bar
     * @param x
     * @param y
     * @return
     */
    public boolean isTouchEventInBar(float x, float y)
    {
        LogUtil.logString("Touch: Bar ID: " + meterBarModel.getId());
        LogUtil.logString("Touch: Touch event at : " + x + ", " + y);
        LogUtil.logString("Touch: Bar Bounds : ");
        LogUtil.logString("Touch: StartX = " + startX);
        LogUtil.logString("Touch: StartX + width = " + (startX + barWidth));
        LogUtil.logString("Touch: barY = " + barY);
        LogUtil.logString("Touch: barBottom = " + barBottom);
        return PaintUtil.isPointInsideRect(x, y, startX, barY, startX + barWidth, barBottom);
    }

    /**
     * Handle touch event on bar
     * @param x
     * @param y
     */
    public void onTouchEvent(float x, float y)
    {
        LogUtil.logString("Touch: Handle Touch in Bar");

        // ask chunks
        for(MeterBarChunk chunk : chunksList)
        {
            if(chunk.isTouchEventInChunk(x, y))
            {
                LogUtil.logString("Touch: Chunk in bar found");

                // Handle click on chunk
                chunk.onTouchEvent(x, y);
                break;
            }
        }

        LogUtil.logString("Touch: Chunks loop in bar finished");
    }


    private MeterChartListener meterChartListener;

    // Hold the model of bar
    private MeterBarModel meterBarModel;
    private Canvas canvas;
    private float startX;
    private float barWidth;

    private float barY, barBottom;

    private ArrayList<MeterBarChunk> chunksList;

}

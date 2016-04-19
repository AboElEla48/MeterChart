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
        startX = x;
        this.barWidth = barWidth;

        // draw chunks
        drawChunks(canvas);

    }

    /**
     * Draw bar chunks
     *
     */
    private void drawChunks(Canvas canvas)
    {
        LogUtil.logString("Draw Chunks");

        double heightUnitPerPixel = (double)canvas.getHeight() / meterBarModel.getBarMaxValue();

        // Adjust Chunks height according to min values
        float barHeight = 0f;
        chunksList = new ArrayList<>();
        for(MeterBarChunkModel chunkModel : meterBarModel.getBarChunks())
        {
            MeterBarChunk chunk = new MeterBarChunk(chunkModel, meterChartListener);
            float chunkHeight = (float) (chunkModel.getValue() * heightUnitPerPixel);

            float chunkMinHeight = chunk.calculateMinHeight();
            if(chunkHeight < chunkMinHeight)
            {
                if (chunkModel.getValue() == 0)
                {
                    chunk.setChunkDrawingMode(MeterBarChunk.Chunk_Mode_Zero);
                }
                else
                {
                    chunk.setChunkDrawingMode(MeterBarChunk.Chunk_Mode_Min);
                }

                // Add the min total value
                barHeight += chunkMinHeight;
            }
            else
            {
                barHeight += chunkHeight;
            }

            chunksList.add(chunk);
        }

        // Calculate the start drawing y
        float y = canvas.getHeight() - barHeight;

        barY = y;

        // Draw tooltip
        if(meterBarModel.isTooltipVisible())
        {
            MeterBarTooltip meterBarTooltip = new MeterBarTooltip(meterBarModel.getTooltipModel());
            meterBarTooltip.drawTooltip(canvas, (int)startX, (int)y, (int)barWidth);
        }

        LogUtil.logBar(meterBarModel);
        LogUtil.logString("Canvas Height = " + canvas.getHeight());
        LogUtil.logString("Units = " + heightUnitPerPixel);
        LogUtil.logString("Bar Height = " + barHeight);

        for(MeterBarChunk chunk : chunksList)
        {
            MeterBarChunkModel chunkModel = chunk.getMeterBarChunkModel();
            float chunkHeight = 0;
            switch (chunk.getChunkDrawingMode())
            {
                case MeterBarChunk.Chunk_Mode_Normal:
                {
                    chunkHeight = (float) (chunkModel.getValue() * heightUnitPerPixel);
                    break;
                }

                case MeterBarChunk.Chunk_Mode_Min:
                case MeterBarChunk.Chunk_Mode_Zero:
                {
                    float chunkMinHeight = chunk.calculateMinHeight();
                    chunkHeight = chunkMinHeight;
                    break;
                }
            }

            chunk.drawChunk(canvas, startX, barWidth, y, chunkHeight);
            y += chunkHeight;
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
    private float startX;
    private float barWidth;

    private float barY, barBottom;

    private ArrayList<MeterBarChunk> chunksList;

}

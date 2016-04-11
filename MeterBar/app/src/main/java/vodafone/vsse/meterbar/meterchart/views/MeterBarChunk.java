package vodafone.vsse.meterbar.meterchart.views;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

import vodafone.vsse.meterbar.meterchart.listeners.MeterChartListener;
import vodafone.vsse.meterbar.meterchart.models.MeterBarChunkModel;
import vodafone.vsse.meterbar.meterchart.utils.LogUtil;
import vodafone.vsse.meterbar.meterchart.utils.PaintUtil;

/**
 * Created by AboElEla on 3/28/2016.
 *
 * This layout is added on bar
 */
public class MeterBarChunk {

    /**
     * init chunk bar model
     * @param chunkModel
     */
    public MeterBarChunk(MeterBarChunkModel chunkModel, MeterChartListener listener)
    {
        this.meterBarChunkModel = chunkModel;
        this.meterChartListener = listener;

        this.barPaintDrawer = new Paint();
        this.barPaintDrawer.setStyle(Paint.Style.FILL);
        if(!meterBarChunkModel.isGradientColor()) {
            this.barPaintDrawer.setColor(meterBarChunkModel.getBackgroundColor());
        }
        this.upTextPaintDrawer = PaintUtil.createTextPaint(chunkModel.getUpText());
        this.downTextPaintDrawer = PaintUtil.createTextPaint(chunkModel.getDownText());
        this.middleTextPaintDrawer = PaintUtil.createTextPaint(chunkModel.getMiddleText());
        if(chunkModel.getMeterValueText() != null) {
            this.valueTextPaintDrawer = PaintUtil.createTextPaint(chunkModel.getMeterValueText());
        }

        if(meterBarChunkModel.isHelperVisible())
        {
            barHelperPaintDrawer = new Paint();
            barHelperPaintDrawer.setStyle(Paint.Style.FILL);
            barHelperPaintDrawer.setColor(meterBarChunkModel.getMeterBarChunkHelper().getHelperColor());
        }
    }

    /**
     * Check if even touch inside chunk
     * @param x
     * @param y
     * @return
     */
    public boolean isTouchEventInChunk(float x, float y)
    {
        if(!isTouchEventInHelper(x, y)) {
            return PaintUtil.isPointInsideRect(x, y, chunkLeft, chunkTop, chunkRight, chunkBottom);
        }
        else
        {
            return true;
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchEventInHelper(float x, float y)
    {
        // check helper option circle too
        return PaintUtil.isPointInsideRect(x, y, bitmapLeft, bitmapTop, bitmapRight, bitmapBottom);
    }

    /**
     * Handle touch event on chunk
     * @param x
     * @param y
     */
    public void onTouchEvent(float x, float y)
    {
        if(isTouchEventInHelper(x, y))
        {
            if(meterChartListener != null)
            {
                meterChartListener.chunkHelperClicked(meterBarChunkModel.getId());
            }
        }
        else
        {
            meterChartListener.chunkBarClicked(meterBarChunkModel.getId());
        }
    }


    /**
     * draw Chunk
     *
     * @param canvas
     * @param x
     * @param chunkWidth
     * @param y
     * @param chunkHeight
     */
    public void drawChunk(Canvas canvas, final float x, final float chunkWidth, final float y, final float chunkHeight)
    {
        // Draw chunk rectangle


        float height = y + chunkHeight;
        if(height > canvas.getHeight())
        {
            height = canvas.getHeight();
        }

        chunkLeft = x;
        chunkRight = x + chunkWidth;
        chunkTop = y;
        chunkBottom = height;

        LogUtil.logString("Start Drawing chunk **");
        LogUtil.logString("x = " + x);
        LogUtil.logString("Chunk Width = " + chunkWidth);
        LogUtil.logString("y = " + y);
        LogUtil.logString("Chunk Height = " + chunkHeight);


        // Create shader for gradient
        if(meterBarChunkModel.isGradientColor()) {
            Shader shader = new LinearGradient(chunkLeft, chunkTop, chunkRight, chunkBottom, meterBarChunkModel.getColorStartGradient(), meterBarChunkModel.getColorEndGradient(), Shader.TileMode.CLAMP);
            barPaintDrawer.setShader(shader);
        }

        canvas.drawRect(x, y, x + chunkWidth, height, barPaintDrawer);

        int textHeight, textWidth;
        Rect bounds = new Rect();
        String str;

        str = meterBarChunkModel.getUpText().getText();
        upTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
        textHeight = bounds.height();
        textWidth = bounds.width();
        float margin = meterBarChunkModel.getUpText().getMargin();
        canvas.drawText(str, x + (chunkWidth / 2) - (textWidth / 2), y + textHeight + margin, upTextPaintDrawer);

        // Draw Value text
        int valueTextHeight = 0;
        if(meterBarChunkModel.getMeterValueText() != null)
        {
            str = "" + (int)meterBarChunkModel.getValue();
            valueTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            valueTextHeight = bounds.height();
            textWidth = bounds.width();

            canvas.drawText(str, x + (chunkWidth / 2) - (textWidth / 2), y + (chunkHeight / 2)- (textHeight/2), valueTextPaintDrawer);
        }

        str = meterBarChunkModel.getMiddleText().getText();
        middleTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
        textHeight = bounds.height();
        textWidth = bounds.width();
        canvas.drawText(str, x + (chunkWidth / 2) - (textWidth / 2), y + (chunkHeight / 2)- (textHeight/2) + valueTextHeight, middleTextPaintDrawer);

        str = meterBarChunkModel.getDownText().getText();
        downTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
        textHeight = bounds.height();
        textWidth = bounds.width();
        margin = meterBarChunkModel.getDownText().getMargin();
        canvas.drawText(str, x + (chunkWidth / 2) - (textWidth / 2), y + chunkHeight - textHeight - margin, downTextPaintDrawer);

        // Draw helper
        if(meterBarChunkModel.isHelperVisible() && meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap() != null)
        {
            bitmapLeft = x + (chunkWidth/2) - (meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getWidth()/2);
            bitmapTop = y - (meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getHeight()/2);
            bitmapRight = bitmapLeft + meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getWidth();
            bitmapBottom = bitmapTop + meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getHeight();

            // Fill color that appears as background for helper option
            canvas.drawCircle(bitmapLeft + ( (bitmapRight - bitmapLeft) / 2),
                    bitmapTop + ( (bitmapBottom - bitmapTop) / 2), meterBarChunkModel.getMeterBarChunkHelper().getHotAreaRadius(), barHelperPaintDrawer);

            // draw image of helper option
            canvas.drawBitmap(meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap(), bitmapLeft, bitmapTop, null);
        }


        LogUtil.logString("Chunk Drawn");
    }


    private MeterBarChunkModel meterBarChunkModel;

    // Hold the paint object of bar
    private Paint barPaintDrawer;
    private Paint upTextPaintDrawer;
    private Paint downTextPaintDrawer;
    private Paint middleTextPaintDrawer;
    private Paint valueTextPaintDrawer;

    private Paint barHelperPaintDrawer;

    private float chunkRight, chunkTop, chunkLeft, chunkBottom;
    private float bitmapRight, bitmapTop, bitmapLeft, bitmapBottom;

    private MeterChartListener meterChartListener;


}

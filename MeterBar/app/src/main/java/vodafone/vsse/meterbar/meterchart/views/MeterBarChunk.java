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

    public MeterBarChunkModel getMeterBarChunkModel() {
        return meterBarChunkModel;
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
                meterChartListener.chunkIconClicked(meterBarChunkModel.getId());
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
    public void drawChunk(Canvas canvas, float x, float chunkWidth, float y, float chunkHeight)
    {

        /**
         * Chunk is drawn in 3 modes
         *
         * Zero Mode in case the chunk value is zero
         * Min Mode in case the value > 0 and height of bar is less than the smallest possible height of chunk
         * Normal mode is the mode of drawing chunk relative to the height of the bar and value
         */


        float actualChunkHeight = y + chunkHeight;
        if (actualChunkHeight > canvas.getHeight())
        {
            actualChunkHeight = canvas.getHeight();
        }

        chunkLeft = x;
        chunkRight = x + chunkWidth;
        chunkTop = y;
        chunkBottom = actualChunkHeight;

        LogUtil.logString("Start Drawing chunk **");
        LogUtil.logString("x = " + x);
        LogUtil.logString("Chunk Width = " + chunkWidth);
        LogUtil.logString("y = " + y);
        LogUtil.logString("Chunk Height = " + chunkHeight);

        float halfImageHeight = 0;
        if (meterBarChunkModel.isHelperVisible() && meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap() != null)
        {
            halfImageHeight = meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getHeight() / 2f;
        }

        // Create shader for gradient
        if (meterBarChunkModel.isGradientColor())
        {
            Shader shader = new LinearGradient(chunkLeft, chunkTop, chunkRight, chunkBottom, meterBarChunkModel.getColorStartGradient(), meterBarChunkModel.getColorEndGradient(), Shader.TileMode.CLAMP);
            barPaintDrawer.setShader(shader);
        }

        float margin;
        int textHeight, textWidth;
        Rect bounds = new Rect();
        String str;

        if(chunkDrawingMode == Chunk_Mode_Normal)
        {
            canvas.drawRect(x, y, chunkRight, chunkBottom, barPaintDrawer);
        }
        else
        {
            // Draw chunk rectangle
            str = meterBarChunkModel.getMiddleText().getText();
            middleTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            textHeight = bounds.height();
            textWidth = bounds.width();

            if(chunkDrawingMode == Chunk_Mode_Min)
            {
                canvas.drawRect(x, chunkBottom - textHeight - 20, chunkRight, chunkBottom, barPaintDrawer);
            }

            // Change the start point of chunk
            y = chunkTop = chunkBottom - textHeight - 20;
        }


        // Draw up text
        if(chunkDrawingMode == Chunk_Mode_Normal)
        {
            str = meterBarChunkModel.getUpText().getText();
            upTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            textHeight = bounds.height();
            textWidth = bounds.width();
            margin = meterBarChunkModel.getUpText().getMargin();
            canvas.drawText(str,
                    x + (chunkWidth / 2) - (textWidth / 2),
                    y + textHeight + margin + halfImageHeight,
                    upTextPaintDrawer);
        }

        int valueTextHeight = 0;

        // Draw Value text
        if (meterBarChunkModel.getMeterValueText() != null)
        {
            str = "" + (int) meterBarChunkModel.getValue();
            valueTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            valueTextHeight = bounds.height();
            textWidth = bounds.width();

            if(chunkDrawingMode == Chunk_Mode_Normal)
            {
                canvas.drawText(str,
                        x + (chunkWidth / 2) - (textWidth / 2),
                        y + ( (chunkBottom - chunkTop) / 2) + (valueTextHeight / 2),
                        valueTextPaintDrawer);
            }
            else
            {

                if(chunkDrawingMode == Chunk_Mode_Zero || chunkDrawingMode == Chunk_Mode_Min)
                {
                    valueTextPaintDrawer.setColor(meterBarChunkModel.getMeterValueText().getZeroTextColor());
                }

                // In min & zero modes draw text outside the rectangle
                canvas.drawText(str,
                        x + (chunkWidth / 2) - (textWidth / 2),
                        y - valueTextMarginInMinMode,
                        valueTextPaintDrawer);
                LogUtil.logString("Value Text Height: " + valueTextHeight);
            }
        }


        str = meterBarChunkModel.getMiddleText().getText();
        if(chunkDrawingMode == Chunk_Mode_Zero)
        {
            middleTextPaintDrawer.setColor(meterBarChunkModel.getMiddleText().getZeroTextColor());
        }
        middleTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
        textHeight = bounds.height();
        textWidth = bounds.width();
        canvas.drawText(str,
                x + (chunkWidth / 2) - (textWidth / 2),
                y + ( (chunkBottom - chunkTop) / 2) + (textHeight / 2) + (chunkDrawingMode == Chunk_Mode_Normal ? valueTextHeight : 0),
                middleTextPaintDrawer);

        if(chunkDrawingMode == Chunk_Mode_Normal)
        {
            str = meterBarChunkModel.getDownText().getText();
            downTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            textHeight = bounds.height();
            textWidth = bounds.width();
            margin = meterBarChunkModel.getDownText().getMargin();
            canvas.drawText(str,
                    x + (chunkWidth / 2) - (textWidth / 2),
                    y + chunkHeight - textHeight - margin,
                    downTextPaintDrawer);
        }

        // Draw helper
        if (meterBarChunkModel.isHelperVisible() && meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap() != null)
        {
            bitmapLeft = x + (chunkWidth / 2) - (meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getWidth() / 2);
            bitmapTop = y - (meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getHeight() / 2);
            bitmapRight = bitmapLeft + meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getWidth();
            bitmapBottom = bitmapTop + meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getHeight();

            if(chunkDrawingMode == Chunk_Mode_Min || chunkDrawingMode == Chunk_Mode_Zero)
            {
                // In zero and min modes, draw helper outside rectangle
                bitmapTop -= valueTextHeight;
                bitmapTop -= circleMarginInMinMode;
                bitmapTop -= (meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getHeight() / 2);

                bitmapBottom -= valueTextHeight;
                bitmapBottom -= circleMarginInMinMode;
                bitmapBottom -= (meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getHeight() / 2);
            }

            // Fill color that appears as background for helper option
            canvas.drawCircle(bitmapLeft + ((bitmapRight - bitmapLeft) / 2),
                    bitmapTop + ((bitmapBottom - bitmapTop) / 2), meterBarChunkModel.getMeterBarChunkHelper().getHotAreaRadius(), barHelperPaintDrawer);

            // draw image of helper option
            canvas.drawBitmap(meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap(), bitmapLeft, bitmapTop, null);
        }


        LogUtil.logString("Chunk Drawn");
    }



    /**
     * calculate the min height of view
     *
     * @return
     */
    public float calculateMinHeight()
    {
        float minHeight = 0f;

        if(meterBarChunkModel.isHelperVisible())
        {
            // calculate the half height of the helper option. It is drawn inside th ebar
            minHeight += (float)meterBarChunkModel.getMeterBarChunkHelper().getHelperBitmap().getHeight();
        }

        String str;
        Rect bounds = new Rect();

        // Calculate the height of the up text
        str = meterBarChunkModel.getDownText().getText();
        if(str.length() > 0) {
            upTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            minHeight += bounds.height() + meterBarChunkModel.getUpText().getMargin();
        }

        // Calculate the height of the Value text
        str = "" + (int) meterBarChunkModel.getValue();
        if(meterBarChunkModel.getMeterValueText() != null) {
            valueTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            minHeight += bounds.height();
        }

        // Calculate the height of the Middle text
        str = meterBarChunkModel.getMiddleText().getText();
        if(str.length() > 0) {
            middleTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            minHeight += bounds.height() + meterBarChunkModel.getMiddleText().getMargin();
        }

        // Calculate the height of the Down text
        str = meterBarChunkModel.getDownText().getText();
        if(str.length() > 0) {
            downTextPaintDrawer.getTextBounds(str, 0, str.length(), bounds);
            minHeight += bounds.height() + meterBarChunkModel.getDownText().getMargin();
        }

        return minHeight;
    }

    public void setChunkDrawingMode(int mode)
    {
        chunkDrawingMode = mode;
    }

    public int getChunkDrawingMode() {
        return chunkDrawingMode;
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


    //Define the drawing mode
    public final static int Chunk_Mode_Normal = 0;
    public final static int Chunk_Mode_Zero = 1;
    public final static int Chunk_Mode_Min = 2;
    private int chunkDrawingMode = Chunk_Mode_Normal;

    private final int valueTextMarginInMinMode =  10;
    private final int circleMarginInMinMode = valueTextMarginInMinMode + 10;
}

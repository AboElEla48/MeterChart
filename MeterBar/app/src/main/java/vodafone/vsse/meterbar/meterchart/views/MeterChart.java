package vodafone.vsse.meterbar.meterchart.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import vodafone.vsse.meterbar.meterchart.models.MeterBarModel;
import vodafone.vsse.meterbar.meterchart.models.MeterChartModel;
import vodafone.vsse.meterbar.meterchart.models.MeterInfoCircleModel;
import vodafone.vsse.meterbar.meterchart.models.TooltipModel;
import vodafone.vsse.meterbar.meterchart.utils.PaintUtil;
import vodafone.vsse.meterbar.meterchart.utils.animation.AnimationListener;
import vodafone.vsse.meterbar.meterchart.utils.animation.AnimationNotifier;

/**
 * Created by AboElEla on 3/24/2016.
 * <p/>
 * This custom control will draw the column chart according to given columns data
 */
public class MeterChart extends View
{

    public MeterChart(Context context)
    {
        super(context);
        initView();
    }

    public MeterChart(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    /**
     * init view
     */
    private void initView()
    {
        setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                float x = event.getX();
                float y = event.getY();

                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    {
                        // Check if the click on circle info caption
                        if (isCircleVisible() && infoCircle.isTouchEventInCircleInfo(x, y))
                        {
                            isCircleClicked = true;
                            invalidate();
                        }
                        else if (meterChartModel.isChartHandleVisible() && isTouchEventInChartHandle(x, y))
                        {
                            //Notify listener that user clicked chart handle to move chart
                            if(meterChartModel.getMeterChartListener() != null)
                            {
                                meterChartModel.getMeterChartListener().chartHandleClicked();
                            }
                            break;
                        }
                        else
                        {

                            // Check if the click on one of the bars
                            for (int i = 0; i < barsList.size(); i++)
                            {
                                if (barsList.get(i).isTouchEventInBar(x, y))
                                {

                                    // point inside bar
                                    barsList.get(i).onTouchEvent(x, y);
                                    break;
                                }
                            }

                        }

                        return true;
                    }

                    case MotionEvent.ACTION_UP:
                    {
                        if (isCircleClicked)
                        {
                            isCircleClicked = false;
                            invalidate();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }


    /**
     * Init chart with meter model
     *
     * @param meterChartModel this is the model used to init meter chart
     */
    public void initChart(MeterChartModel meterChartModel)
    {
        this.meterChartModel = meterChartModel;
        infoCircle = new MeterInfoCircle(meterChartModel.getInfoCircleModel());
    }

    /**
     * Control the visibility of info circle
     *
     * @param isVisible flag to show/hide info circle
     */
    public void setInfoCircleVisible(boolean isVisible)
    {
        meterChartModel.setIsCircleVisible(isVisible);
        invalidate();
    }

    /**
     * Show/hide tooltip on bar
     *
     * @param barID
     * @param isVisible
     */
    public void setBarTooltipVisible(int barID, boolean isVisible)
    {
        for (MeterBarModel meterBarModel : meterChartModel.getMeterBars())
        {
            if (meterBarModel.getId() == barID)
            {
                meterBarModel.setIsTooltipVisible(isVisible);
                break;
            }
        }

        invalidate();
    }

    /**
     * Show/hide tooltip on bar
     *
     * @param barID
     * @param tooltipModel
     * @param isVisible
     */
    public void setBarTooltipVisible(int barID, TooltipModel tooltipModel, boolean isVisible)
    {
        for (MeterBarModel meterBarModel : meterChartModel.getMeterBars())
        {
            if (meterBarModel.getId() == barID)
            {
                meterBarModel.setTooltipModel(tooltipModel);
                meterBarModel.setIsTooltipVisible(isVisible);
                break;
            }
        }

        invalidate();
    }

    /**
     * Check if circle info is visible or not
     *
     * @return
     */
    public boolean isCircleVisible()
    {
        return meterChartModel.isCircleVisible();
    }

    /**
     * Draw chart animated
     */
    public void drawChartAnimated(int animationDuration)
    {
        doChartAnimation(animationDuration);
    }

    /**
     * Draw chart animated with given step
     *
     * @param animationDuration
     * @param animationDownStep
     */
    public void drawChartAnimated(int animationDuration, int animationDownStep)
    {
        setAnimationStep(animationDownStep);

        doChartAnimation(animationDuration);
    }

    /**
     * Do chart animation
     *
     * @param animationDuration
     */
    private void doChartAnimation(int animationDuration)
    {
        if (meterChartModel.isCircleVisible())
        {
            // Animate info circle
            int originalAnimationCircleValue = meterChartModel.getInfoCircleModel().getInfoCircleCurrentUnits();
            meterChartModel.getInfoCircleModel().setInfoCircleCurrentUnits(meterChartModel.getInfoCircleModel().getInfoCircleMaxUnits());

            AnimationNotifier animationNotifier = new AnimationNotifier(animationDuration, new AnimationCircleDownListener());
            animationNotifier.addAnimationTag(Animation_Original_Circle_Val_Key, originalAnimationCircleValue);

            animationNotifier.startAnimation();
        }

        for (MeterBarModel meterBarModel : meterChartModel.getMeterBars())
        {
            // Animate last chunk
            int size = meterBarModel.getBarChunks().size();
            double originalAnimationChunkValue = meterBarModel.getBarChunks().get(size - 1).getValue();
            meterBarModel.getBarChunks().get(size - 1).setValue(originalAnimationChunkValue +
                    meterBarModel.getBarMaxValue() - meterBarModel.getChunksTotalValue());

            AnimationNotifier animationNotifier = new AnimationNotifier(animationDuration, new AnimationDownListener());
            animationNotifier.addAnimationTag(Animation_Bar_Model_Key, meterBarModel);
            animationNotifier.addAnimationTag(Animation_Original_Chunk_Val_Key, originalAnimationChunkValue);

            animationNotifier.startAnimation();
        }
    }

    /**
     * Fill bars of chart
     *
     * @param animationDuration
     */
    public void fillChart(int animationDuration)
    {
        doFillChart(animationDuration);
    }

    /**
     * fill chart with given animation step
     *
     * @param animationDuration
     * @param animationStep
     */
    public void fillChart(int animationDuration, int animationStep)
    {
        setAnimationStep(animationStep);

        doFillChart(animationDuration);
    }

    /**
     * Do fill animation
     *
     * @param animationDuration
     */
    private void doFillChart(int animationDuration)
    {
        for (MeterBarModel meterBarModel : meterChartModel.getMeterBars())
        {
            AnimationNotifier animationNotifier = new AnimationNotifier(animationDuration, new AnimationUpListener());
            animationNotifier.addAnimationTag(Animation_Bar_Model_Key, meterBarModel);
            animationNotifier.startAnimation();
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        // Draw Chart bars
        drawBars(canvas);

        // Draw chart circles
        drawCircle(canvas);

        // Draw chart handle image
        drawHandle(canvas);
    }

    /**
     * Draw bars
     *
     * @param canvas
     */
    private void drawBars(Canvas canvas)
    {
        // get the dimensions of view
        int chartWidth = getWidth();
        int chartHeight = getHeight();

        int numOfBars = meterChartModel.getMeterBars().size();

        // Draw chart views

        // Draw bars
        double barWidth = (double) chartWidth / (double) numOfBars - (meterChartModel.getBarsMargin() * (numOfBars - 1));
        float x = 0f;
        float y = 0f;
        barsList = new ArrayList<>();
        for (MeterBarModel barModel : meterChartModel.getMeterBars())
        {
            MeterBar meterBar = new MeterBar(barModel, meterChartModel.getMeterChartListener());
            meterBar.drawBar(canvas, x, (float) barWidth);
            x += barWidth + meterChartModel.getBarsMargin();

            barsList.add(meterBar);
        }
    }

    /**
     * Draw chunks
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas)
    {
        if (meterChartModel.isCircleVisible())
        {
            // get the dimensions of view
            int chartWidth = getWidth();
            int chartHeight = getHeight();

            MeterInfoCircleModel circleModel = meterChartModel.getInfoCircleModel();
            float heightUnitPerPixel = (float) chartHeight / (float) circleModel.getInfoCircleMaxUnits();

            float x = chartWidth / 2;
            float y = chartHeight - heightUnitPerPixel * (float) circleModel.getInfoCircleCurrentUnits();

            infoCircle.drawCircle(canvas, x, y, isCircleClicked);
        }

    }

    /**
     * Draw the chart handle
     *
     * @param canvas
     */
    private void drawHandle(Canvas canvas)
    {
        if (meterChartModel.isChartHandleVisible())
        {
            // get the dimensions of view
            int chartWidth = getWidth();
            int chartHeight = getHeight();

            handleChartLeft = (chartWidth / 2) - (meterChartModel.getChartHandle().getWidth() / 2);
            handleChartTop = chartHeight - meterChartModel.getChartHandle().getHeight() - meterChartModel.getChartHandleMargin();

            handleChartRight = handleChartLeft + meterChartModel.getChartHandle().getWidth();
            handleChartBottom = handleChartTop + meterChartModel.getChartHandle().getHeight();

            // draw bitmap handle
            canvas.drawBitmap(meterChartModel.getChartHandle(), handleChartLeft, handleChartTop, null);
        }
    }

    /**
     * Check if event touch on chart handle or not
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchEventInChartHandle(float x, float y)
    {
        return PaintUtil.isPointInsideRect(x, y, handleChartLeft, handleChartTop, handleChartRight, handleChartBottom);
    }

    /**
     * Define listener for Circle animation
     */
    private class AnimationCircleDownListener implements AnimationListener
    {
        @Override
        public boolean isAnimationFinished(HashMap<String, Object> animationTags)
        {
            int originalCircleValue = ((Integer) animationTags.get(Animation_Original_Circle_Val_Key)).intValue();
            if (originalCircleValue < meterChartModel.getInfoCircleModel().getInfoCircleCurrentUnits())
            {
                return false;
            }
            return true;
        }

        @Override
        public void notifyAnimationStep(HashMap<String, Object> animationTags)
        {
            int currentUnits = meterChartModel.getInfoCircleModel().getInfoCircleCurrentUnits();
            meterChartModel.getInfoCircleModel().setInfoCircleCurrentUnits(currentUnits - 1);

            invalidate();
        }
    }

    /**
     * Define listener for down animation
     */
    private class AnimationDownListener implements AnimationListener
    {
        @Override
        public boolean isAnimationFinished(HashMap<String, Object> animationTags)
        {

            MeterBarModel meterBarModel = (MeterBarModel) animationTags.get(Animation_Bar_Model_Key);
            double originalAnimationChunkValue = ((Double) animationTags.get(Animation_Original_Chunk_Val_Key)).doubleValue();

            int size = meterBarModel.getBarChunks().size();
            if (originalAnimationChunkValue >= meterBarModel.getBarChunks().get(size - 1).getValue())
            {
                return true;
            }

            return false;
        }

        @Override
        public void notifyAnimationStep(HashMap<String, Object> animationTags)
        {

            MeterBarModel meterBarModel = (MeterBarModel) animationTags.get(Animation_Bar_Model_Key);

            //Decrease animation step to redraw bar chunks
            int size = meterBarModel.getBarChunks().size();
            double currentVal = meterBarModel.getBarChunks().get(size - 1).getValue();

            double originalAnimationChunkValue = ((Double) animationTags.get(Animation_Original_Chunk_Val_Key)).doubleValue();
            if (currentVal + animationDownStep < originalAnimationChunkValue)
            {
                currentVal = originalAnimationChunkValue;
            }
            else
            {
                currentVal += animationDownStep;
            }

            meterBarModel.getBarChunks().get(size - 1).setValue(currentVal);

            // Draw bar
            invalidate();
        }
    }


    public void setAnimationStep(int animationStep)
    {
        this.animationStep = animationStep;

        animationDownStep = -1 * animationStep;
    }

    public int getAnimationStep()
    {
        return animationStep;
    }

    /**
     * Define listener for up animation
     */
    private class AnimationUpListener implements AnimationListener
    {

        @Override
        public boolean isAnimationFinished(HashMap<String, Object> animationTags)
        {
            // Check if meter reached the max value
            MeterBarModel meterBarModel = (MeterBarModel) animationTags.get(Animation_Bar_Model_Key);
            return meterBarModel.getChunksTotalValue() >= meterBarModel.getBarMaxValue();
        }

        @Override
        public void notifyAnimationStep(HashMap<String, Object> animationTags)
        {

            // Perform one step up
            MeterBarModel meterBarModel = (MeterBarModel) animationTags.get(Animation_Bar_Model_Key);
            double step;
            if (meterBarModel.getChunksTotalValue() + animationStep > meterBarModel.getBarMaxValue())
            {
                step = meterBarModel.getBarMaxValue() - meterBarModel.getChunksTotalValue();
            }
            else
            {
                step = animationStep;
            }

            meterBarModel.getBarChunks().get(meterBarModel.getBarChunks().size() - 1).setValue(
                    meterBarModel.getBarChunks().get(meterBarModel.getBarChunks().size() - 1).getValue() + step
            );

            // Draw bar bar
            invalidate();
        }
    }

    private final String LOG_TAG = "Meter_Chart_Log";

    private int animationStep = 100;
    private int animationDownStep = -1 * animationStep;

    // Hold the model representing the data of the control
    private MeterChartModel meterChartModel;
    private MeterInfoCircle infoCircle;
    private boolean isCircleClicked = false;

    // Hold list of bars drawn
    private ArrayList<MeterBar> barsList;

    private float handleChartLeft;
    private float handleChartTop;
    private float handleChartRight;
    private float handleChartBottom;

    private final String Animation_Bar_Model_Key = "Animation_Bar_Model_Key";
    private final String Animation_Original_Chunk_Val_Key = "Animation_Original_Chunk_Val_Key";
    private final String Animation_Original_Circle_Val_Key = "Animation_Original_Circle_Val_Key";
}

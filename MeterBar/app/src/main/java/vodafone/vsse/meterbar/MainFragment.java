package vodafone.vsse.meterbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import vodafone.vsse.meterbar.meterchart.listeners.MeterChartListener;
import vodafone.vsse.meterbar.meterchart.models.MeterBarChunkHelper;
import vodafone.vsse.meterbar.meterchart.models.MeterBarModel;
import vodafone.vsse.meterbar.meterchart.models.MeterInfoCircleModel;
import vodafone.vsse.meterbar.meterchart.models.MeterChartModel;
import vodafone.vsse.meterbar.meterchart.models.MeterBarChunkModel;
import vodafone.vsse.meterbar.meterchart.models.MeterText;
import vodafone.vsse.meterbar.meterchart.models.MeterValueText;
import vodafone.vsse.meterbar.meterchart.models.TooltipModel;
import vodafone.vsse.meterbar.meterchart.views.MeterChart;


/**
 * This is a test fragment for meter component
 */
public class MainFragment extends Fragment implements MeterChartListener{

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        setHasOptionsMenu(true);

        // Get Meter Bar
        meterChart = (MeterChart)rootView.findViewById(R.id.content_main_fragment_meter_chart);
        meterChart.initChart(initMeterModel(currentMode));
        meterChart.setInfoCircleVisible(true);

        // Draw chart Animated
        if(drawAnimated)
        {
            meterChart.drawChartAnimated(Animation_Duration);
        }

        View redrawBtn = rootView.findViewById(R.id.redraw_btn);
        redrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meterChart.initChart(initMeterModel(currentMode));

                // Draw chart Animated
                meterChart.drawChartAnimated(Animation_Duration);
            }
        });

        View refillBtn = rootView.findViewById(R.id.refill_btn);
        refillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // refill bars with max values
                meterChart.fillChart(Animation_Duration);
            }
        });

        return rootView;
    }

    /**
     * Init meter model with dummy testing data
     *
     * @return
     */
    private MeterChartModel initMeterModel(int mode)
    {
        MeterChartModel meterChartModel = new MeterChartModel();

        // Load Handle image of chart
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inScaled = false;
        Bitmap handleBitmap = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.handle, options);
        meterChartModel.setChartHandle(handleBitmap, 20, true);

        // Init info circle of meter model
        MeterInfoCircleModel meterInfoCircleModel = new MeterInfoCircleModel();
        meterInfoCircleModel.setValueText(new MeterValueText(25, 0xff000000, 0xff000000, true, false));
        meterInfoCircleModel.setInfoCircleMaxUnits(28);
        meterInfoCircleModel.setInfoCircleCurrentUnits(16);
        meterInfoCircleModel.setDetailsText(new MeterText("16 Details", 20, 0xff000000, 0xff000000, 0, false, false));
        meterInfoCircleModel.setInfoCircleRadius(50f);
        meterInfoCircleModel.setInfoCircleDetailsRadius(80f);
        meterInfoCircleModel.setInfoCircleMargin(20);

        meterChartModel.setInfoCircleModel(meterInfoCircleModel);
        meterChartModel.setIsCircleVisible(true);

        // set margin among bars
        meterChartModel.setBarsMargin(10);

        //set meter bars to meter model
        ArrayList<MeterBarModel> meterBars = new ArrayList<>();

        // Init Bars of meter model

        options = new BitmapFactory.Options();
        //options.inScaled = false;
        Bitmap helperBitmap = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.plus_shadow, options);
        float helperHotAreaRadius = 40f;

        // Red Gradient #E72502 : EB5B22


        //Create Red Bar
        MeterBarModel redMeterBar = new MeterBarModel();
        redMeterBar.setId(Red_Bar_ID);
        ArrayList<MeterBarChunkModel> redMeterChunks = new ArrayList<>();

        //Green Chunk
        MeterBarChunkModel greenMeterBarChunkModel = new MeterBarChunkModel();
        greenMeterBarChunkModel.setId(GREEN_CHUNK_ID);
        greenMeterBarChunkModel.setBackgroundColor(0xff7ED321);
        greenMeterBarChunkModel.setIsGradientColor(false);
        greenMeterBarChunkModel.setDownText(new MeterText("Down Text", 25, 0xFFFFFFFF, 0xff7ED321, 0, false, true));
        greenMeterBarChunkModel.setMiddleText(new MeterText("Green Middle Text", 40, 0xFFFFFFFF, 0xff7ED321, 0, true, true));
        greenMeterBarChunkModel.setIsHelperVisible(false);
        greenMeterBarChunkModel.setMeterBarChunkHelper(new MeterBarChunkHelper(helperBitmap, 0xff7ED321, helperHotAreaRadius));
        greenMeterBarChunkModel.setValue(mode == Meter_Mode_Normal ? 1000 : 20);
        redMeterChunks.add(greenMeterBarChunkModel);


        // Red Chunk
        MeterBarChunkModel redMeterBarChunkModel = new MeterBarChunkModel();
        redMeterBarChunkModel.setId(RED_CHUNK_ID);
        redMeterBarChunkModel.setColorStartGradient(0xffEB5B22);
        redMeterBarChunkModel.setColorEndGradient(0xffE72502);
        redMeterBarChunkModel.setIsGradientColor(true);
        redMeterBarChunkModel.setDownText(new MeterText("Down Text", 30, 0xFFFFFFFF, 0xffE72502, 0, false, true));
        redMeterBarChunkModel.setUpText(new MeterText("Up Text", 30, 0xFFFFFFFF, 0xffE72502, 0, false, true));
        redMeterBarChunkModel.setMiddleText(new MeterText("von 5000 MB", 44, 0xFFFFFFFF, 0xffE72502, 0, true, true));
        redMeterBarChunkModel.setMeterValueText(new MeterValueText(70, 0xFFFFFFFF, 0xffE72502, true, true));
        redMeterBarChunkModel.setIsHelperVisible(true);
        redMeterBarChunkModel.setMeterBarChunkHelper(new MeterBarChunkHelper(helperBitmap, 0xffE72502, helperHotAreaRadius));
        redMeterBarChunkModel.setValue(mode == Meter_Mode_Normal ? 3000 : 20);
        redMeterChunks.add(redMeterBarChunkModel);

        redMeterBar.setBarChunks(redMeterChunks);
        redMeterBar.setBarMaxValue(5000);
        meterBars.add(redMeterBar);


        //Create Yellow Bar
        MeterBarModel yellowMeterBar = new MeterBarModel();
        yellowMeterBar.setId(Yellow_Bar_ID);
        ArrayList<MeterBarChunkModel> yellowMeterChunks = new ArrayList<>();


        //Orange Chunk
        MeterBarChunkModel orangeMeterBarChunkModel = new MeterBarChunkModel();
        orangeMeterBarChunkModel.setId(Orange_CHUNK_ID);
        orangeMeterBarChunkModel.setBackgroundColor(0xffEC6957);
        orangeMeterBarChunkModel.setIsGradientColor(false);
        orangeMeterBarChunkModel.setDownText(new MeterText("Down Text", 15, 0xFFFFFFFF, 0xffEC6957, 0, false, true));
        orangeMeterBarChunkModel.setUpText(new MeterText("Up Text", 15, 0xFFFFFFFF, 0xffEC6957, 0, false, true));
        orangeMeterBarChunkModel.setMiddleText(new MeterText("Middle Text", 25, 0xFFFFFFFF, 0xffEC6957, 0, true, true));
        orangeMeterBarChunkModel.setIsHelperVisible(false);
        orangeMeterBarChunkModel.setValue(400);
        yellowMeterChunks.add(orangeMeterBarChunkModel);


        //Yellow Chunk
        MeterBarChunkModel yellowMeterBarChunkModel = new MeterBarChunkModel();
        yellowMeterBarChunkModel.setId(Yellow_CHUNK_ID);
        yellowMeterBarChunkModel.setColorStartGradient(0xffF1C629);
        yellowMeterBarChunkModel.setColorEndGradient(0xffFFA700);
        yellowMeterBarChunkModel.setIsGradientColor(true);
        yellowMeterBarChunkModel.setDownText(new MeterText("Down Text", 20, 0xFFFFFFFF, 0xffF1C629, 0, false, true));
        yellowMeterBarChunkModel.setMiddleText(new MeterText("von 2000 MB", 35, 0xFFFFFFFF, 0xffF1C629, 0, true, true));
        yellowMeterBarChunkModel.setMeterValueText(new MeterValueText(70, 0xFFFFFFFF, 0xffF1C629, true, true));
        yellowMeterBarChunkModel.setIsHelperVisible(true);
        yellowMeterBarChunkModel.setMeterBarChunkHelper(new MeterBarChunkHelper(helperBitmap, 0xffFFA700, helperHotAreaRadius));
        yellowMeterBarChunkModel.setValue(mode == Meter_Mode_Normal ? 900 : (mode == Meter_Mode_Min ? 20 : 0));
        yellowMeterChunks.add(yellowMeterBarChunkModel);

        yellowMeterBar.setBarChunks(yellowMeterChunks);
        yellowMeterBar.setBarMaxValue(3000);
        meterBars.add(yellowMeterBar);

        // set meter bars
        meterChartModel.setMeterBars(meterBars);
        meterChartModel.setMeterChartListener(this);

        return meterChartModel;
    }

    @Override
    public void chunkBarClicked(int chunkID) {
        String text = "%s is clicked";

        String chunkName = mapChunkToName(chunkID);
        Snackbar.make(this.getView(), String.format(text, chunkName), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void chunkIconClicked(int chunkID) {
        String text = "(Helper) %s option is clicked";
        String chunkName = mapChunkToName(chunkID);
        Snackbar.make(this.getView(), String.format(text, chunkName), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void chartHandleClicked()
    {
        String text = "Chart Handle clicked";
        Snackbar.make(this.getView(), text, Snackbar.LENGTH_LONG).show();
    }

    private String mapChunkToName(int id)
    {
        switch (id)
        {
            case GREEN_CHUNK_ID:
            {
                return GREEN_CHUNK_Name;

            }

            case RED_CHUNK_ID:
            {
                return RED_CHUNK_Name;

            }

            case Yellow_CHUNK_ID:
            {
                return Yellow_CHUNK_Name;

            }

            case Orange_CHUNK_ID:
            {
                return Orange_CHUNK_Name;

            }
        }

        return "";
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_normal:
            {
                currentMode = Meter_Mode_Normal;
                meterChart.initChart(initMeterModel(currentMode));
                meterChart.drawChartAnimated(Animation_Duration);
                return true;
            }

            case R.id.action_min:
            {
                currentMode = Meter_Mode_Min;
                meterChart.initChart(initMeterModel(currentMode));
                meterChart.drawChartAnimated(Animation_Duration);
                return true;
            }

            case R.id.action_zero:
            {
                currentMode = Meter_Mode_Zero;
                meterChart.initChart(initMeterModel(currentMode));
                meterChart.drawChartAnimated(Animation_Duration);
                return true;
            }

            case R.id.action_show_tips:
            {
                meterChart.setBarTooltipVisible(Red_Bar_ID,
                        new TooltipModel(new MeterText(Red_Tip_Text, 25, 0xffffffff, 0, 0, false, false), 0xffEB5B22), true);
                meterChart.setBarTooltipVisible(Yellow_Bar_ID,
                        new TooltipModel(new MeterText(Yellow_Tip_Text, 25, 0xffffffff, 0, 0, false, false), 0xffF1C629), true);
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    private final static int Red_Bar_ID = 1;
    private final static int Yellow_Bar_ID = 2;

    private final static int GREEN_CHUNK_ID = 10;
    private final static int RED_CHUNK_ID = 11;
    private final static String GREEN_CHUNK_Name = "Green Chunk";
    private final static String RED_CHUNK_Name = "Red Chunk";


    private final static int Yellow_CHUNK_ID = 20;
    private final static int Orange_CHUNK_ID = 21;
    private final static String Yellow_CHUNK_Name = "Yellow Chunk";
    private final static String Orange_CHUNK_Name = "Orange Chunk";

    private final int Animation_Duration = 50;

    private MeterChart meterChart;

    private final int Meter_Mode_Normal = 0;
    private final int Meter_Mode_Min = 1;
    private final int Meter_Mode_Zero = 2;
    private int currentMode = Meter_Mode_Normal;


    private boolean drawAnimated = true;

    private final String Red_Tip_Text = "This is the text of the tooltip of Red bar. It is somehow long text to show text wrapping. This can be extra";
    private final String Yellow_Tip_Text = "This is the text of the tooltip of Yellow bar. It is somehow long text to show text wrapping. This can be extra";


}

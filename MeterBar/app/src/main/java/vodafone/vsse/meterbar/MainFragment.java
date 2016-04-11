package vodafone.vsse.meterbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

        // Get Meter Bar
        meterChart = (MeterChart)rootView.findViewById(R.id.content_main_fragment_meter_chart);
        meterChart.initChart(initMeterModel());
        meterChart.setInfoCircleVisible(true);

        // Draw chart Animated
        meterChart.drawChartAnimated(Animation_Duration);

        View redrawBtn = rootView.findViewById(R.id.redraw_btn);
        redrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meterChart.initChart(initMeterModel());

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
    private MeterChartModel initMeterModel()
    {
        MeterChartModel meterChartModel = new MeterChartModel();

        // Init info circle of meter model
        MeterInfoCircleModel meterInfoCircleModel = new MeterInfoCircleModel();
        meterInfoCircleModel.setCaptionText(new MeterText("16", "", 25, 0xff000000, 0, true, false));
        meterInfoCircleModel.setDetailsText(new MeterText("16 Details ", "", 20, 0xff000000, 0, false, false));
        meterInfoCircleModel.setInfoCircleRadius(50f);
        meterInfoCircleModel.setInfoCircleDetailsRadius(80f);
        meterInfoCircleModel.setInfoCircleMargin(20);

        meterChartModel.setInfoCircleModel(meterInfoCircleModel);
        meterChartModel.setIsCircleVisible(true);
        meterChartModel.setBarsMargin(10);

        //set meter bars to meter model
        ArrayList<MeterBarModel> meterBars = new ArrayList<>();

        // Init Bars of meter model

        Bitmap helperBitmap = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.plus_shadow);
        float helperHotAreaRadius = 40f;

        // Red Gradient #E72502 : EB5B22

        //Create Red Bar
        MeterBarModel redMeterBar = new MeterBarModel();
        redMeterBar.setId(Red_Bar_ID);
        redMeterBar.setIsZeroTipVisible(true);

        ArrayList<MeterBarChunkModel> redMeterChunks = new ArrayList<>();

        //Green Chunk
        MeterBarChunkModel greenMeterBarChunkModel = new MeterBarChunkModel();
        greenMeterBarChunkModel.setId(GREEN_CHUNK_ID);
        greenMeterBarChunkModel.setBackgroundColor(0xff7ED321);
        greenMeterBarChunkModel.setIsGradientColor(false);
        //pinkMeterBarChunkModel.setUpText(new MeterText("Down Text", "", 25, 0xFFFFFFFF, helperBitmap.getHeight() / 2, false, true));
        greenMeterBarChunkModel.setDownText(new MeterText("Down Text", "", 25, 0xFFFFFFFF, helperBitmap.getHeight() / 2, false, true));
        greenMeterBarChunkModel.setMiddleText(new MeterText("Middle Text", "", 40, 0xFFFFFFFF, 0, true, true));
        greenMeterBarChunkModel.setIsHelperVisible(true);
        greenMeterBarChunkModel.setMeterBarChunkHelper(new MeterBarChunkHelper(helperBitmap, 0xff7ED321, helperHotAreaRadius));
        greenMeterBarChunkModel.setValue(1000);
        redMeterChunks.add(greenMeterBarChunkModel);

        // Red Chunk
        MeterBarChunkModel redMeterBarChunkModel = new MeterBarChunkModel();
        redMeterBarChunkModel.setId(RED_CHUNK_ID);
        redMeterBarChunkModel.setColorStartGradient(0xffEB5B22);
        redMeterBarChunkModel.setColorEndGradient(0xffE72502);
        redMeterBarChunkModel.setIsGradientColor(true);
        redMeterBarChunkModel.setDownText(new MeterText("Down Text", "", 30, 0xFFFFFFFF, 0, false, true));
        redMeterBarChunkModel.setUpText(new MeterText("Up Text", "", 30, 0xFFFFFFFF, helperBitmap.getHeight() / 2, false, true));
        redMeterBarChunkModel.setMiddleText(new MeterText("von 5000 MB", "", 44, 0xFFFFFFFF, 0, true, true));
        redMeterBarChunkModel.setMeterValueText(new MeterValueText("", 70, 0xFFFFFFFF, 0, true, true));
        redMeterBarChunkModel.setIsHelperVisible(true);
        redMeterBarChunkModel.setMeterBarChunkHelper(new MeterBarChunkHelper(helperBitmap, 0xffE72502, helperHotAreaRadius));
        redMeterBarChunkModel.setValue(3000);
        redMeterChunks.add(redMeterBarChunkModel);

        redMeterBar.setBarChunks(redMeterChunks);
        redMeterBar.setBarMaxValue(5000);
        meterBars.add(redMeterBar);

        //Create Yellow Bar
        MeterBarModel yellowMeterBar = new MeterBarModel();
        yellowMeterBar.setId(Yellow_Bar_ID);
        yellowMeterBar.setIsZeroTipVisible(true);
        ArrayList<MeterBarChunkModel> yellowMeterChunks = new ArrayList<>();

        //Orange Chunk
        MeterBarChunkModel orangeMeterBarChunkModel = new MeterBarChunkModel();
        orangeMeterBarChunkModel.setId(Orange_CHUNK_ID);
        orangeMeterBarChunkModel.setBackgroundColor(0xffEC6957);
        orangeMeterBarChunkModel.setIsGradientColor(false);
        orangeMeterBarChunkModel.setDownText(new MeterText("Down Text", "", 15, 0xFFFFFFFF, helperBitmap.getHeight()/2, false, true));
        orangeMeterBarChunkModel.setUpText(new MeterText("Up Text", "", 15, 0xFFFFFFFF, 0, false, true));
        orangeMeterBarChunkModel.setMiddleText(new MeterText("Middle Text", "", 25, 0xFFFFFFFF, 0, true, true));
        orangeMeterBarChunkModel.setIsHelperVisible(false);
        //orangeMeterBarChunkModel.setHelperBitmap(helperBitmap);
        orangeMeterBarChunkModel.setValue(400);
        yellowMeterChunks.add(orangeMeterBarChunkModel);
//
        //Yellow Chunk
        MeterBarChunkModel yellowMeterBarChunkModel = new MeterBarChunkModel();
        yellowMeterBarChunkModel.setId(Yellow_CHUNK_ID);
        yellowMeterBarChunkModel.setColorStartGradient(0xffF1C629);
        yellowMeterBarChunkModel.setColorEndGradient(0xffFFA700);
        yellowMeterBarChunkModel.setIsGradientColor(true);
        yellowMeterBarChunkModel.setDownText(new MeterText("Down Text", "", 20, 0xFFFFFFFF, 0, false, true));
        //yellowMeterBarChunkModel.setUpText(new MeterText("Up Text", "", 20, 0xFFFFFFFF, 0, false, true));
        yellowMeterBarChunkModel.setMiddleText(new MeterText("von 2000 MB", "", 35, 0xFFFFFFFF, 0, true, true));
        yellowMeterBarChunkModel.setMeterValueText(new MeterValueText("", 70, 0xFFFFFFFF, 0, true, true));
        yellowMeterBarChunkModel.setIsHelperVisible(true);
        yellowMeterBarChunkModel.setMeterBarChunkHelper(new MeterBarChunkHelper(helperBitmap, 0xffFFA700, helperHotAreaRadius));
        yellowMeterBarChunkModel.setValue(900);
        yellowMeterChunks.add(yellowMeterBarChunkModel);

        yellowMeterBar.setBarChunks(yellowMeterChunks);
        yellowMeterBar.setBarMaxValue(2000);
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
    public void chunkHelperClicked(int chunkID) {
        String text = "(Helper) %s option is clicked";
        String chunkName = mapChunkToName(chunkID);
        Snackbar.make(this.getView(), String.format(text, chunkName), Snackbar.LENGTH_LONG).show();
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

}

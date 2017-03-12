package teamhardcoder.y_fi;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class PersonalChartFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static String groupId;

    private float[] yData = {25.46f, 67.12f, 82.05f, 42.01f, 23.99f, 13.52f};
    private String[] xData = {"Food", "Rent", "Electricity", "Gas", "Phone", "Internet"};
    PieChart pieChart;

    public PersonalChartFragment() {
    }

    public static PersonalChartFragment newInstance(String groupId) {
        PersonalChartFragment fragment = new PersonalChartFragment();
        Bundle args = new Bundle();
        //args.putString("groupId", groupId);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_personal_chart_fragment, container, false);



        pieChart = (PieChart) rootView.findViewById(R.id.idPieChart);

        Description desc = new Description();
        desc.setText("Tap Slice for More Details");

        pieChart.setDescription(desc);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Expense Percentages");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        pieChart.setUsePercentValues(true);

        addDataSet();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Log.d("ChartFragment", "onValueSelected" + e.toString() );

                int pos1 = e.toString().indexOf("y: ");
                String expenses = e.toString().substring(pos1 + 3);

                for(int i = 0; i < yData.length; i++)
                {
                    if(yData[i] == Float.parseFloat(expenses))
                    {
                        pos1 = i;
                        break;
                    }
                }

                String category = xData[pos1];
                String res = "Category: " + category + "\n" + "Expense: $" +  expenses;

                Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });

        return rootView;
    }



    public void addDataSet(){
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        //  LOADING INTO THE DATA SET HERE, TAKE STUFF FROM DATABASE

        for(int i = 0; i < yData.length; i++)
        {
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }

        for(int i = 0; i < xData.length; i++)
        {
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();

        for(int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for(int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);


        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setData(pieData);
        pieChart.invalidate();
     }


}
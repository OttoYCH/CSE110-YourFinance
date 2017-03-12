package teamhardcoder.y_fi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import teamhardcoder.y_fi.database.data.PersonalExpense;


public class PersonalChartFragment extends Fragment {

    PieChart pieChart;
    ArrayList<PieEntry> yEntrys;
    ArrayList<String> xEntrys;
    static List<PersonalExpense> personalExpenseListLocal;

    public PersonalChartFragment() {
    }

    public static PersonalChartFragment newInstance(List<PersonalExpense> personalExpenseList) {
        PersonalChartFragment fragment = new PersonalChartFragment();
        personalExpenseListLocal = personalExpenseList;
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

        addDataSet(personalExpenseListLocal);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                Log.d("ChartFragment", "onValueSelected" + e.toString() );

                int pos1 = e.toString().indexOf("y: ");
                String expenses = e.toString().substring(pos1 + 3);

                for (int i = 0; i < yEntrys.size(); i++)
                {
                    if (yEntrys.get(i).getValue() == Float.parseFloat(expenses))
                    {
                        pos1 = i;
                        break;
                    }
                }

                String category = xEntrys.get(pos1);

                String res = "Category: " + category + "\n" + "Expense: $" +  expenses;

                Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {
            }
        });
        return rootView;
    }

    public void addDataSet(List<PersonalExpense> personalExpenseList) {
        personalExpenseListLocal = personalExpenseList;
        yEntrys = new ArrayList<>();
        xEntrys = new ArrayList<>();

        //  LOADING INTO THE DATA SET HERE, TAKE STUFF FROM DATABASE

        /*for(int i = 0; i < yData.length; i++)
        {
            yEntrys.add(new PieEntry(yData[i], xData[i]));
        }

        for(int i = 0; i < xData.length; i++)
        {
            xEntrys.add(xData[i]);
        }
        */
        HashMap<String, Double> category_expense = new HashMap<>();
        for (PersonalExpense pe : personalExpenseListLocal) {
            if (category_expense.containsKey(pe.getCategoryName()))
                category_expense.put(pe.getCategoryName(),
                        category_expense.get(pe.getCategoryName()) + pe.getAmount());
            else
                category_expense.put(pe.getCategoryName(), pe.getAmount());
        }

        for (Map.Entry<String, Double> entry : category_expense.entrySet()) {
            xEntrys.add(entry.getKey());
            yEntrys.add(new PieEntry(Float.parseFloat(String.format("%.2f", entry.getValue())),
                    entry.getKey()));
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
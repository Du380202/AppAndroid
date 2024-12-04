package com.example.bookstore.activity.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookstore.R;
import com.example.bookstore.api.admin.OrderApi;
import com.example.bookstore.dto.RevenueDto;
import com.example.bookstore.retrofit.ApiService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {
    public String getYear;
    private int delay = 1000;
//    Toolbar toolbar;
    List<RevenueDto> revenueDto = new ArrayList<>();
    Button btnGetYearBill;
    PieChart pieChart;
    BarChart barChart;
    TextView tvTitle;
    ImageView backIcon;
    private AutoCompleteTextView edtYear;
    ArrayList<String> years = new ArrayList<>();

    private ArrayAdapter<String> yearAdapter;


    int[] colors = new int[] {
            Color.rgb(255, 102, 0),   // Màu cam
            Color.rgb(255, 204, 0),   // Màu vàng
            Color.rgb(51, 102, 204),  // Màu xanh dương
            Color.rgb(153, 0, 204),   // Màu tím
            Color.rgb(255, 51, 153),  // Màu hồng
            Color.rgb(0, 153, 204),   // Màu lam
            Color.rgb(204, 0, 0),     // Màu đỏ
            Color.rgb(0, 204, 102),   // Màu xanh lá
            Color.rgb(153, 153, 153), // Màu xám
            Color.rgb(0, 204, 204),   // Màu cyan
            Color.rgb(255, 0, 255),   // Màu magenta
            Color.rgb(0, 128, 128)    // Màu teal
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        setControl();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, years);
        edtYear.setAdapter(adapter);
        edtYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtYear.showDropDown();

            }
        });

        edtYear.setOnItemClickListener((parent, view, position, id) -> {
            String selectedYear = (String) parent.getItemAtPosition(position);
            getData(Integer.parseInt(selectedYear));
        });

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }

    private void getData(int year) {
        ApiService apiService = new ApiService();
        OrderApi orderApi = apiService.getRetrofit().create(OrderApi.class);
        orderApi.getRevenue(year).enqueue(new Callback<List<RevenueDto>>() {
            @Override
            public void onResponse(Call<List<RevenueDto>> call, Response<List<RevenueDto>> response) {
                revenueDto = response.body();
                showBarChart();
            }

            @Override
            public void onFailure(Call<List<RevenueDto>> call, Throwable throwable) {

            }
        });
    }

    private void showBarChart() {
        barChart.getAxisRight().setDrawLabels (false);
        List<BarEntry> barEntries = new ArrayList<>();
        for (RevenueDto revenueDto1 : revenueDto) {

            barEntries.add(new BarEntry(revenueDto1.getMonth(), Float.parseFloat(String.valueOf(revenueDto1.getTotalRevenue()))));
        }
        barChart.getAxisRight().setDrawLabels (false);
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        BarDataSet dataSet = new BarDataSet(barEntries, "Biểu đồ doanh thu năm " + getYear);
        dataSet.setValueTextSize(12f);
        dataSet.setColors (colors);
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateXY(delay, delay);
        barChart.invalidate();
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setGranularity(1f);
        barChart.getXAxis().setGranularityEnabled(true);
    }

    private void setControl() {
//        toolbar = findViewById(R.id.toolbar1);
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
        tvTitle = findViewById(R.id.tvTitle);
        edtYear = findViewById(R.id.edtYear);
        backIcon = findViewById(R.id.back_icon);
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 2010; i--) {
            years.add(String.valueOf(i));
        }
    }
}
//package com.example.bookstore.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.bookstore.R;
//
//// PaymentActivity.java
//
//public class PaymentActivity extends AppCompatActivity {
//
//    private RadioButton radioButtonVNPay, radioButtonZaloPay;
//    private Button btnConfirmPayment;
//    private TextView tvTotalAmount;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);
//
//        LinearLayout paymentMethodLayout = findViewById(R.id.paymentMethodLayout);
//        LinearLayout paymentInfoLayout = findViewById(R.id.paymentInfoLayout);
//        TextView tvSelectedPaymentMethod = findViewById(R.id.tvSelectedPaymentMethod);
//
//// Khi chọn ZaloPay
//        findViewById(R.id.zaloPayOption).setOnClickListener(view -> {
//            tvSelectedPaymentMethod.setText("ZaloPay");
//            paymentMethodLayout.setVisibility(View.GONE);
//            paymentInfoLayout.setVisibility(View.VISIBLE);
//        });
//
//// Khi chọn VNPay
//        findViewById(R.id.vnpayOption).setOnClickListener(view -> {
//            tvSelectedPaymentMethod.setText("VNPay");
//            paymentMethodLayout.setVisibility(View.GONE);
//            paymentInfoLayout.setVisibility(View.VISIBLE);
//        });
//    }
//}

package com.marwaeltayeb.das.view;

import static com.marwaeltayeb.das.utils.Constant.ORDER;
import static com.marwaeltayeb.das.utils.Constant.PRODUCTID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.marwaeltayeb.das.R;
import com.marwaeltayeb.das.databinding.ActivityStatusBinding;
import com.marwaeltayeb.das.model.Order;

import java.text.DecimalFormat;

public class StatusActivity extends AppCompatActivity implements View.OnClickListener {

    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStatusBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_status);

        // Receive Order object
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra(ORDER);

        productId = order.getProductId();
        binding.orderDate.setText(order.getOrderDate());
        binding.orderNumber.setText(order.getOrderNumber());
        binding.userName.setText(order.getUserName());
        binding.userAddress.setText(order.getShippingAddress());
        binding.userPhone.setText(order.getShippingPhone());
        binding.txtProductName.setText(order.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(order.getProductPrice());
        binding.txtProductPrice.setText(formattedPrice + " VNĐ");
        String statusString = "Proceed";
        switch(order.getOrderDateStatus()) {
            case 0:
                statusString = "Cancelled";
                break;
            case 1:
                statusString = "Completed";
                break;
            case 2:
                statusString = "Paid By Visa";
                break;
            case 3:
                statusString = "Pending Confirm";
                break;
            case 4:
                statusString = "Confirmed";
                break;
            case 5:
                statusString = "Shipped";
                break;
            default:
        }
        String status = getString(R.string.item, statusString);
        binding.orderStatus.setText(status);

        binding.reOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reOrder) {
            Intent reOrderIntent = new Intent(this, OrderProductActivity.class);
            reOrderIntent.putExtra(PRODUCTID, productId);
            startActivity(reOrderIntent);
        }
    }
}

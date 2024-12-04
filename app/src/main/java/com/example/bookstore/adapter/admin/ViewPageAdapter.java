package com.example.bookstore.adapter.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bookstore.fragment.AllOrderFragment;
import com.example.bookstore.fragment.CancelOrderFragment;
import com.example.bookstore.fragment.ProcessOrderFragment;
import com.example.bookstore.fragment.ShippingOrderFragment;
import com.example.bookstore.fragment.SuccessOrderFragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllOrderFragment();
            case 1:
                return new ProcessOrderFragment();
            case 2:
                return new ShippingOrderFragment();
            case 3:
                return new SuccessOrderFragment();
            case 4:
                return new CancelOrderFragment();
            default:
                return new DialogFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tất cả";
            case 1:
                return "Chờ xác nhận";
            case 2:
                return "Đang giao";
            case 3:
                return "Đã giao";
            case 4:
                return "Đã hủy";
            default:
                return null;
        }
    }
}

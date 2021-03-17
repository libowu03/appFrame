package com.frame.main.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.frame.main.BR;
import com.frame.main.viewModel.BaseViewModel;

public class DataBinding extends BaseObservable {
    private BaseViewModel data;

    public DataBinding(BaseViewModel viewModel){
        this.data = viewModel;
    }

    private BaseViewModel getViewModel(){
        return data;
    }
}

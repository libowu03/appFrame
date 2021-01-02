package com.frame.main.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.frame.main.BR;
import com.frame.main.viewModel.BaseViewModel;

public class DataBinding extends BaseObservable {
    private BaseViewModel vm;

    private void setVm(BaseViewModel vm) {
        this.vm = vm;
        notifyPropertyChanged(BR.vm);
    }

    @Bindable
    private BaseViewModel getVm(){
        return vm;
    }
}

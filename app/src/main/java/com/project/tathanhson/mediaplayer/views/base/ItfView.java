package com.project.tathanhson.mediaplayer.views.base;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.project.tathanhson.mediaplayer.viewmodels.BaseViewModel;

public interface ItfView<V extends ViewBinding, VM extends BaseViewModel> {
    @NonNull
    Class<VM> initViewModelClass();
    @NonNull
    V initViewBinding();

    void initViews();
}

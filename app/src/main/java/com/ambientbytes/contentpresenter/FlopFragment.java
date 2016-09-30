package com.ambientbytes.contentpresenter;


import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ambientbytes.contentpresenter.databinding.FragmentFlopBinding;
import com.ambientbytes.contentpresenter.viewmodels.FlopViewModel;

import org.jetbrains.annotations.NotNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class FlopFragment extends Fragment {
    private FlopViewModel viewModel;


    public FlopFragment() {
        // Required empty public constructor
    }

    public void setViewModel(@NotNull FlopViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFlopBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flop, container, false);
        binding.setVm(this.viewModel);

        return binding.getRoot();
    }

}

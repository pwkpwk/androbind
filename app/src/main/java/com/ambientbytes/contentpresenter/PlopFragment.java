package com.ambientbytes.contentpresenter;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ambientbytes.contentpresenter.databinding.FragmentPlopBinding;
import com.ambientbytes.contentpresenter.viewmodels.PlopViewModel;

import org.jetbrains.annotations.NotNull;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlopFragment extends Fragment {
    private PlopViewModel viewModel;

    public PlopFragment() {
        // Required empty public constructor
    }

    public void setViewModel(@NotNull PlopViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentPlopBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_plop, container, false);
        binding.setVm(this.viewModel);

        return binding.getRoot();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}

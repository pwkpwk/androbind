package com.ambientbytes.contentpresenter.viewmodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ambientbytes.contentpresenter.BR;

import org.jetbrains.annotations.NotNull;

public final class RootViewModel extends BaseObservable {
    @NotNull private final IViewModelPresenter presenter;
    private boolean isPlopVisible;
    private boolean isFlopVisible;

    public RootViewModel(@NotNull final IViewModelPresenter presenter) {
        this.presenter = presenter;
        this.isPlopVisible = true;
        this.isFlopVisible = true;
    }

    @Bindable
    public boolean getIsPlopVisible() {
        return this.isPlopVisible;
    }

    @Bindable
    public boolean getIsFlopVisible() {
        return this.isFlopVisible;
    }

    public final void showPlopView() {
        this.presenter.Present(new PlopViewModel());
        setIsPlopVisible(false);
        setIsFlopVisible(true);
    }

    public final void showFlopView() {
        this.presenter.Present(new FlopViewModel());
        setIsPlopVisible(true);
        setIsFlopVisible(false);
    }

    private void setIsPlopVisible(final boolean visible) {
        if (visible != this.isPlopVisible) {
            this.isPlopVisible = visible;
            notifyPropertyChanged(BR.isPlopVisible);
        }
    }

    private void setIsFlopVisible(final boolean visible) {
        if (visible != this.isFlopVisible) {
            this.isFlopVisible = visible;
            notifyPropertyChanged(BR.isFlopVisible);
        }
    }
}

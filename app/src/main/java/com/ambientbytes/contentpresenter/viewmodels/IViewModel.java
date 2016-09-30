package com.ambientbytes.contentpresenter.viewmodels;

import org.jetbrains.annotations.NotNull;

public interface IViewModel {
    void Presenting(@NotNull IViewModelPresenter presenter);
    void Presented(@NotNull IViewModelPresenter presenter);
    void Dismissing(@NotNull IViewModelPresenter presenter);
    void Dismissed(@NotNull IViewModelPresenter presenter);
}

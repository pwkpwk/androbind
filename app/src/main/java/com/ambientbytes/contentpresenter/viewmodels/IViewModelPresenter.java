package com.ambientbytes.contentpresenter.viewmodels;

import org.jetbrains.annotations.Nullable;

public interface IViewModelPresenter {
    void Present(@Nullable IViewModel viewModel);
}

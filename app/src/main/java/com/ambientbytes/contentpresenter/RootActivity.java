package com.ambientbytes.contentpresenter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;

import com.ambientbytes.contentpresenter.databinding.RootActivityBinding;
import com.ambientbytes.contentpresenter.viewmodels.BooleanToViewVisibleConverter;
import com.ambientbytes.contentpresenter.viewmodels.FlopViewModel;
import com.ambientbytes.contentpresenter.viewmodels.IViewModel;
import com.ambientbytes.contentpresenter.viewmodels.IViewModelPresenter;
import com.ambientbytes.contentpresenter.viewmodels.PlopViewModel;
import com.ambientbytes.contentpresenter.viewmodels.RootViewModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RootActivity extends Activity implements IViewModelPresenter {
    private IViewModel presentedViewModel;
    private boolean isPlop;

    private abstract class AnimationListener implements Animation.AnimationListener {
        @NotNull private final IViewModel viewModel;
        @NotNull private final IViewModelPresenter presenter;

        @Override
        public void onAnimationStart(Animation animation) {
            this.onStart(this.viewModel, this.presenter);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animation.setAnimationListener(null);
            this.onEnd(this.viewModel, this.presenter);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        protected AnimationListener(@NotNull IViewModel viewModel, @NotNull final IViewModelPresenter presenter, @NotNull Animation animation) {
            this.viewModel = viewModel;
            this.presenter = presenter;
            animation.setAnimationListener(this);
        }

        protected void onStart(@NotNull IViewModel viewModel, @NotNull IViewModelPresenter presenter) {
        }

        protected void onEnd(@NotNull IViewModel viewModel, @NotNull IViewModelPresenter presenter) {
        }
    }

    private final class FadeInAnimationListener extends AnimationListener {
        public FadeInAnimationListener(@NotNull IViewModel viewModel, @NotNull final IViewModelPresenter presenter, @NotNull Animation animation) {
            super(viewModel, presenter, animation);
        }

        @Override
        protected void onStart(@NotNull IViewModel viewModel, @NotNull IViewModelPresenter presenter) {
            viewModel.Presenting(presenter);
        }

        @Override
        protected void onEnd(@NotNull IViewModel viewModel, @NotNull IViewModelPresenter presenter) {
            viewModel.Presented(presenter);
        }
    }

    private final class FadeOutAnimationListener extends AnimationListener {
        @NotNull private final ViewAnimator animator;
        @NotNull private final View view;

        public FadeOutAnimationListener(@NotNull IViewModel viewModel,
                                        @NotNull final IViewModelPresenter presenter,
                                        @NotNull Animation animation,
                                        @NotNull ViewAnimator animator,
                                        @NotNull View view) {
            super(viewModel, presenter, animation);
            this.animator = animator;
            this.view = view;
        }

        @Override
        protected void onStart(@NotNull IViewModel viewModel, @NotNull IViewModelPresenter presenter) {
            viewModel.Dismissing(presenter);
        }

        @Override
        protected void onEnd(@NotNull IViewModel viewModel, @NotNull IViewModelPresenter presenter) {
            viewModel.Dismissed(presenter);
            //
            // After the animation has finished, it is safe to remove the faded-out view from the animator.
            //
            this.animator.removeView(this.view);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RootActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.root_activity);
        //
        // Compose the view model - inject all dependencies.
        //
        RootViewModel vm = new RootViewModel(this);
        binding.setVm(vm);
        binding.setBoolToVisible(new BooleanToViewVisibleConverter());

        final ViewAnimator animator = (ViewAnimator) findViewById(R.id.content_presenter);
        //
        // Adjust duration of in and out animations to something that does not infuriate users.
        //
        Animation a = animator.getInAnimation();

        if (null != a)
            a.setDuration(100);
        a = animator.getOutAnimation();
        if (null != a)
            a.setDuration(100);
        //
        // Perform initial navigation.
        //
        vm.showPlopView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void Present(@Nullable IViewModel viewModel) {
        //
        // IViewModelPresenter.Present
        // pick a view for the view model and show it in the presenter animator.
        //
        ViewAnimator animator = (ViewAnimator) findViewById(R.id.content_presenter);
        View newView = null;

        if (null != viewModel)
            newView = resolveView(viewModel);

        if (null == newView) {
            //
            // No new view; remove all views
            //
            if (null != this.presentedViewModel)
                this.presentedViewModel.Dismissing(this);
            animator.removeAllViews();
            if (null != this.presentedViewModel) {
                this.presentedViewModel.Dismissed(this);
                this.presentedViewModel = null;
            }
        } else {
            final Animation in = animator.getInAnimation();
            final Animation out = animator.getOutAnimation();
            final View currentView = animator.getCurrentView();
            final int count = animator.getChildCount();

            if (null != in) {
                //
                //
                //
                final FadeInAnimationListener fadeIn = new FadeInAnimationListener(viewModel, this, in);
            } else {
                viewModel.Presenting(this);
            }

            if (null != currentView) {
                if (null != out) {
                    final FadeOutAnimationListener fadeOut = new FadeOutAnimationListener(this.presentedViewModel, this, out, animator, currentView);
                } else {
                    this.presentedViewModel.Dismissing(this);
                }
            }

            animator.addView(newView);
            animator.setDisplayedChild(count);

            if (null == out && null != this.presentedViewModel) {
                this.presentedViewModel.Dismissed(this);
                animator.removeView(currentView);
            }

            if (null == in)
                viewModel.Presented(this);

            this.presentedViewModel = viewModel;
        }
    }

    private View resolveView(@NotNull IViewModel viewModel) {
        final Class vmClass = viewModel.getClass();
        final String vmClassNamne = vmClass.getName();
        View resolvedView = null;

        if (vmClassNamne.endsWith("PlopViewModel")) {
            PlopFragment view = new PlopFragment();
            FrameLayout layout = new FrameLayout(getApplicationContext());
            FragmentTransaction trans;

            view.setViewModel((PlopViewModel)viewModel);
            layout.setId(R.id.plop_fragment_layout);
            trans = getFragmentManager().beginTransaction();
            trans.add(R.id.plop_fragment_layout, view);
            trans.commit();

            resolvedView = layout;
        } else if (vmClassNamne.endsWith("FlopViewModel")) {
            FlopFragment view = new FlopFragment();
            FrameLayout layout = new FrameLayout(getApplicationContext());
            FragmentTransaction trans;

            view.setViewModel((FlopViewModel)viewModel);
            layout.setId(R.id.flop_fragment_layout);
            trans = getFragmentManager().beginTransaction();
            trans.add(R.id.flop_fragment_layout, view);
            trans.commit();

            resolvedView = layout;
        }

        return resolvedView;
    }
}

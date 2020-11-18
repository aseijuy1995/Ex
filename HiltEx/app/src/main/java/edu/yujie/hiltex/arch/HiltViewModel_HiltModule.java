package edu.yujie.hiltex.arch;

import androidx.hilt.lifecycle.ViewModelAssistedFactory;
import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.codegen.OriginatingElement;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(ActivityRetainedComponent.class)
@OriginatingElement(
    topLevelClass = HiltViewModel.class
)
public interface HiltViewModel_HiltModule {
  @Binds
  @IntoMap
  @StringKey("edu.yujie.hiltex.arch.HiltViewModel")
  ViewModelAssistedFactory<? extends ViewModel> bind(HiltViewModel_AssistedFactory factory);
}

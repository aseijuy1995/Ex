package edu.yujie.hiltex.arch;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelAssistedFactory;
import androidx.lifecycle.SavedStateHandle;
import java.lang.Override;
import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("androidx.hilt.AndroidXHiltProcessor")
public final class HiltViewModel_AssistedFactory implements ViewModelAssistedFactory<HiltViewModel> {
  private final Provider<HiltRepository> repo;

  @Inject
  HiltViewModel_AssistedFactory(Provider<HiltRepository> repo) {
    this.repo = repo;
  }

  @Override
  @NonNull
  public HiltViewModel create(SavedStateHandle arg0) {
    return new HiltViewModel(repo.get(), arg0);
  }
}

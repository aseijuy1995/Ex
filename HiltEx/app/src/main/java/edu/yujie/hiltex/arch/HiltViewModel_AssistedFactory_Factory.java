package edu.yujie.hiltex.arch;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class HiltViewModel_AssistedFactory_Factory implements Factory<HiltViewModel_AssistedFactory> {
  private final Provider<HiltRepository> repoProvider;

  public HiltViewModel_AssistedFactory_Factory(Provider<HiltRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public HiltViewModel_AssistedFactory get() {
    return newInstance(repoProvider);
  }

  public static HiltViewModel_AssistedFactory_Factory create(
      Provider<HiltRepository> repoProvider) {
    return new HiltViewModel_AssistedFactory_Factory(repoProvider);
  }

  public static HiltViewModel_AssistedFactory newInstance(Provider<HiltRepository> repo) {
    return new HiltViewModel_AssistedFactory(repo);
  }
}

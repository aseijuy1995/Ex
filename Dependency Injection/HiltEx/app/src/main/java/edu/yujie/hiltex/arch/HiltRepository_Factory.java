package edu.yujie.hiltex.arch;

import dagger.internal.Factory;
import edu.yujie.hiltex.ApiService;
import edu.yujie.hiltex.room.AppDao;
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
public final class HiltRepository_Factory implements Factory<HiltRepository> {
  private final Provider<ApiService> serviceProvider;

  private final Provider<AppDao> daoProvider;

  public HiltRepository_Factory(Provider<ApiService> serviceProvider,
      Provider<AppDao> daoProvider) {
    this.serviceProvider = serviceProvider;
    this.daoProvider = daoProvider;
  }

  @Override
  public HiltRepository get() {
    HiltRepository instance = newInstance();
    HiltRepository_MembersInjector.injectService(instance, serviceProvider.get());
    HiltRepository_MembersInjector.injectDao(instance, daoProvider.get());
    return instance;
  }

  public static HiltRepository_Factory create(Provider<ApiService> serviceProvider,
      Provider<AppDao> daoProvider) {
    return new HiltRepository_Factory(serviceProvider, daoProvider);
  }

  public static HiltRepository newInstance() {
    return new HiltRepository();
  }
}

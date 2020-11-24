package edu.yujie.hiltex.arch;

import dagger.MembersInjector;
import dagger.internal.InjectedFieldSignature;
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
public final class HiltRepository_MembersInjector implements MembersInjector<HiltRepository> {
  private final Provider<ApiService> serviceProvider;

  private final Provider<AppDao> daoProvider;

  public HiltRepository_MembersInjector(Provider<ApiService> serviceProvider,
      Provider<AppDao> daoProvider) {
    this.serviceProvider = serviceProvider;
    this.daoProvider = daoProvider;
  }

  public static MembersInjector<HiltRepository> create(Provider<ApiService> serviceProvider,
      Provider<AppDao> daoProvider) {
    return new HiltRepository_MembersInjector(serviceProvider, daoProvider);
  }

  @Override
  public void injectMembers(HiltRepository instance) {
    injectService(instance, serviceProvider.get());
    injectDao(instance, daoProvider.get());
  }

  @InjectedFieldSignature("edu.yujie.hiltex.arch.HiltRepository.service")
  public static void injectService(HiltRepository instance, ApiService service) {
    instance.service = service;
  }

  @InjectedFieldSignature("edu.yujie.hiltex.arch.HiltRepository.dao")
  public static void injectDao(HiltRepository instance, AppDao dao) {
    instance.dao = dao;
  }
}

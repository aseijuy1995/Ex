package edu.yujie.hiltex.arch;

import dagger.MembersInjector;
import dagger.internal.InjectedFieldSignature;
import edu.yujie.hiltex.HiltSimple;
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
public final class App_MembersInjector implements MembersInjector<App> {
  private final Provider<HiltSimple> hiltSimpleProvider;

  public App_MembersInjector(Provider<HiltSimple> hiltSimpleProvider) {
    this.hiltSimpleProvider = hiltSimpleProvider;
  }

  public static MembersInjector<App> create(Provider<HiltSimple> hiltSimpleProvider) {
    return new App_MembersInjector(hiltSimpleProvider);
  }

  @Override
  public void injectMembers(App instance) {
    injectHiltSimple(instance, hiltSimpleProvider.get());
  }

  @InjectedFieldSignature("edu.yujie.hiltex.arch.App.hiltSimple")
  public static void injectHiltSimple(App instance, HiltSimple hiltSimple) {
    instance.hiltSimple = hiltSimple;
  }
}

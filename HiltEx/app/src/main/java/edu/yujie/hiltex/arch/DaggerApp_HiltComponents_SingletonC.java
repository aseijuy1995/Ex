package edu.yujie.hiltex.arch;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.hilt.lifecycle.ViewModelAssistedFactory;
import androidx.hilt.lifecycle.ViewModelFactoryModules_ActivityModule_ProvideFactoryFactory;
import androidx.hilt.lifecycle.ViewModelFactoryModules_FragmentModule_ProvideFactoryFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.MemoizedSentinel;
import dagger.internal.Preconditions;
import edu.yujie.hiltex.HiltActivity;
import edu.yujie.hiltex.ApiService;
import edu.yujie.hiltex.HiltFragment;
import edu.yujie.hiltex.HiltFragment_MembersInjector;
import edu.yujie.hiltex.HiltSimple;
import edu.yujie.hiltex.hilt.NetworkModule;
import edu.yujie.hiltex.hilt.NetworkModule_ProvideApiServiceFactory;
import edu.yujie.hiltex.hilt.NetworkModule_ProvideOkHttpClientFactory;
import edu.yujie.hiltex.hilt.NetworkModule_ProvideRetrofitFactory;
import edu.yujie.hiltex.hilt.QualifierModule;
import edu.yujie.hiltex.hilt.RoomModule;
import edu.yujie.hiltex.hilt.RoomModule_ProvideAppDaoFactory;
import edu.yujie.hiltex.hilt.RoomModule_ProvideAppDataBaseFactory;
import edu.yujie.hiltex.hilt.WorkService;
import edu.yujie.hiltex.hilt.WorkServiceImpl;
import edu.yujie.hiltex.room.AppDao;
import edu.yujie.hiltex.room.AppResultDataBase;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerApp_HiltComponents_SingletonC extends App_HiltComponents.SingletonC {
  private final ApplicationContextModule applicationContextModule;

  private volatile Object okHttpClient = new MemoizedSentinel();

  private volatile Object retrofit = new MemoizedSentinel();

  private volatile Object apiService = new MemoizedSentinel();

  private volatile Object appResultDataBase = new MemoizedSentinel();

  private volatile Object appDao = new MemoizedSentinel();

  private DaggerApp_HiltComponents_SingletonC(
      ApplicationContextModule applicationContextModuleParam) {
    this.applicationContextModule = applicationContextModuleParam;
  }

  public static Builder builder() {
    return new Builder();
  }

  private OkHttpClient getOkHttpClient() {
    Object local = okHttpClient;
    if (local instanceof MemoizedSentinel) {
      synchronized (local) {
        local = okHttpClient;
        if (local instanceof MemoizedSentinel) {
          local = NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();
          okHttpClient = DoubleCheck.reentrantCheck(okHttpClient, local);
        }
      }
    }
    return (OkHttpClient) local;
  }

  private Retrofit getRetrofit() {
    Object local = retrofit;
    if (local instanceof MemoizedSentinel) {
      synchronized (local) {
        local = retrofit;
        if (local instanceof MemoizedSentinel) {
          local = NetworkModule_ProvideRetrofitFactory.provideRetrofit(getOkHttpClient());
          retrofit = DoubleCheck.reentrantCheck(retrofit, local);
        }
      }
    }
    return (Retrofit) local;
  }

  private ApiService getApiService() {
    Object local = apiService;
    if (local instanceof MemoizedSentinel) {
      synchronized (local) {
        local = apiService;
        if (local instanceof MemoizedSentinel) {
          local = NetworkModule_ProvideApiServiceFactory.provideApiService(getRetrofit());
          apiService = DoubleCheck.reentrantCheck(apiService, local);
        }
      }
    }
    return (ApiService) local;
  }

  private AppResultDataBase getAppResultDataBase() {
    Object local = appResultDataBase;
    if (local instanceof MemoizedSentinel) {
      synchronized (local) {
        local = appResultDataBase;
        if (local instanceof MemoizedSentinel) {
          local = RoomModule_ProvideAppDataBaseFactory.provideAppDataBase(ApplicationContextModule_ProvideApplicationFactory.provideApplication(applicationContextModule));
          appResultDataBase = DoubleCheck.reentrantCheck(appResultDataBase, local);
        }
      }
    }
    return (AppResultDataBase) local;
  }

  private AppDao getAppDao() {
    Object local = appDao;
    if (local instanceof MemoizedSentinel) {
      synchronized (local) {
        local = appDao;
        if (local instanceof MemoizedSentinel) {
          local = RoomModule_ProvideAppDaoFactory.provideAppDao(getAppResultDataBase());
          appDao = DoubleCheck.reentrantCheck(appDao, local);
        }
      }
    }
    return (AppDao) local;
  }

  @Override
  public ActivityRetainedComponentBuilder retainedComponentBuilder() {
    return new ActivityRetainedCBuilder();
  }

  @Override
  public ServiceComponentBuilder serviceComponentBuilder() {
    return new ServiceCBuilder();
  }

  @Override
  public void injectApp(App app) {
    injectApp2(app);
  }

  @Override
  public WorkService injectWorkService() {
    return new WorkServiceImpl();
  }

  private App injectApp2(App instance) {
    App_MembersInjector.injectHiltSimple(instance, new HiltSimple());
    return instance;
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder networkModule(NetworkModule networkModule) {
      Preconditions.checkNotNull(networkModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder qualifierModule(QualifierModule qualifierModule) {
      Preconditions.checkNotNull(qualifierModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder roomModule(RoomModule roomModule) {
      Preconditions.checkNotNull(roomModule);
      return this;
    }

    public App_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new DaggerApp_HiltComponents_SingletonC(applicationContextModule);
    }
  }

  private final class ActivityRetainedCBuilder implements App_HiltComponents.ActivityRetainedC.Builder {
    @Override
    public App_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl();
    }
  }

  private final class ActivityRetainedCImpl extends App_HiltComponents.ActivityRetainedC {
    private ActivityRetainedCImpl() {

    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder();
    }

    private final class ActivityCBuilder implements App_HiltComponents.ActivityC.Builder {
      private Activity activity;

      @Override
      public ActivityCBuilder activity(Activity activity) {
        this.activity = Preconditions.checkNotNull(activity);
        return this;
      }

      @Override
      public App_HiltComponents.ActivityC build() {
        Preconditions.checkBuilderRequirement(activity, Activity.class);
        return new ActivityCImpl(activity);
      }
    }

    private final class ActivityCImpl extends App_HiltComponents.ActivityC {
      private final Activity activity;

      private volatile Provider<HiltRepository> hiltRepositoryProvider;

      private volatile Provider<HiltViewModel_AssistedFactory> hiltViewModel_AssistedFactoryProvider;

      private ActivityCImpl(Activity activityParam) {
        this.activity = activityParam;
      }

      private HiltRepository getHiltRepository() {
        return injectHiltRepository(HiltRepository_Factory.newInstance());
      }

      private Provider<HiltRepository> getHiltRepositoryProvider() {
        Object local = hiltRepositoryProvider;
        if (local == null) {
          local = new SwitchingProvider<>(1);
          hiltRepositoryProvider = (Provider<HiltRepository>) local;
        }
        return (Provider<HiltRepository>) local;
      }

      private HiltViewModel_AssistedFactory getHiltViewModel_AssistedFactory() {
        return new HiltViewModel_AssistedFactory(getHiltRepositoryProvider());
      }

      private Provider<HiltViewModel_AssistedFactory> getHiltViewModel_AssistedFactoryProvider() {
        Object local = hiltViewModel_AssistedFactoryProvider;
        if (local == null) {
          local = new SwitchingProvider<>(0);
          hiltViewModel_AssistedFactoryProvider = (Provider<HiltViewModel_AssistedFactory>) local;
        }
        return (Provider<HiltViewModel_AssistedFactory>) local;
      }

      private Map<String, Provider<ViewModelAssistedFactory<? extends ViewModel>>> getMapOfStringAndProviderOfViewModelAssistedFactoryOf(
          ) {
        return Collections.<String, Provider<ViewModelAssistedFactory<? extends ViewModel>>>singletonMap("edu.yujie.hiltex.arch.HiltViewModel", (Provider) getHiltViewModel_AssistedFactoryProvider());
      }

      private ViewModelProvider.Factory getProvideFactory() {
        return ViewModelFactoryModules_ActivityModule_ProvideFactoryFactory.provideFactory(activity, ApplicationContextModule_ProvideApplicationFactory.provideApplication(DaggerApp_HiltComponents_SingletonC.this.applicationContextModule), getMapOfStringAndProviderOfViewModelAssistedFactoryOf());
      }

      @Override
      public Set<ViewModelProvider.Factory> getActivityViewModelFactory() {
        return Collections.<ViewModelProvider.Factory>singleton(getProvideFactory());
      }

      @Override
      public FragmentComponentBuilder fragmentComponentBuilder() {
        return new FragmentCBuilder();
      }

      @Override
      public ViewComponentBuilder viewComponentBuilder() {
        return new ViewCBuilder();
      }

      @Override
      public void injectHiltActivity(HiltActivity hiltActivity) {
      }

      private HiltRepository injectHiltRepository(HiltRepository instance) {
        HiltRepository_MembersInjector.injectService(instance, DaggerApp_HiltComponents_SingletonC.this.getApiService());
        HiltRepository_MembersInjector.injectDao(instance, DaggerApp_HiltComponents_SingletonC.this.getAppDao());
        return instance;
      }

      private final class FragmentCBuilder implements App_HiltComponents.FragmentC.Builder {
        private Fragment fragment;

        @Override
        public FragmentCBuilder fragment(Fragment fragment) {
          this.fragment = Preconditions.checkNotNull(fragment);
          return this;
        }

        @Override
        public App_HiltComponents.FragmentC build() {
          Preconditions.checkBuilderRequirement(fragment, Fragment.class);
          return new FragmentCImpl(fragment);
        }
      }

      private final class FragmentCImpl extends App_HiltComponents.FragmentC {
        private final Fragment fragment;

        private FragmentCImpl(Fragment fragmentParam) {
          this.fragment = fragmentParam;
        }

        private ViewModelProvider.Factory getProvideFactory() {
          return ViewModelFactoryModules_FragmentModule_ProvideFactoryFactory.provideFactory(fragment, ApplicationContextModule_ProvideApplicationFactory.provideApplication(DaggerApp_HiltComponents_SingletonC.this.applicationContextModule), ActivityCImpl.this.getMapOfStringAndProviderOfViewModelAssistedFactoryOf());
        }

        @Override
        public Set<ViewModelProvider.Factory> getFragmentViewModelFactory() {
          return Collections.<ViewModelProvider.Factory>singleton(getProvideFactory());
        }

        @Override
        public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
          return new ViewWithFragmentCBuilder();
        }

        @Override
        public void injectHiltFragment(HiltFragment hiltFragment) {
          injectHiltFragment2(hiltFragment);
        }

        private HiltFragment injectHiltFragment2(HiltFragment instance) {
          HiltFragment_MembersInjector.injectHiltSimple(instance, new HiltSimple());
          return instance;
        }

        private final class ViewWithFragmentCBuilder implements App_HiltComponents.ViewWithFragmentC.Builder {
          private View view;

          @Override
          public ViewWithFragmentCBuilder view(View view) {
            this.view = Preconditions.checkNotNull(view);
            return this;
          }

          @Override
          public App_HiltComponents.ViewWithFragmentC build() {
            Preconditions.checkBuilderRequirement(view, View.class);
            return new ViewWithFragmentCImpl(view);
          }
        }

        private final class ViewWithFragmentCImpl extends App_HiltComponents.ViewWithFragmentC {
          private ViewWithFragmentCImpl(View view) {

          }
        }
      }

      private final class ViewCBuilder implements App_HiltComponents.ViewC.Builder {
        private View view;

        @Override
        public ViewCBuilder view(View view) {
          this.view = Preconditions.checkNotNull(view);
          return this;
        }

        @Override
        public App_HiltComponents.ViewC build() {
          Preconditions.checkBuilderRequirement(view, View.class);
          return new ViewCImpl(view);
        }
      }

      private final class ViewCImpl extends App_HiltComponents.ViewC {
        private ViewCImpl(View view) {

        }
      }

      private final class SwitchingProvider<T> implements Provider<T> {
        private final int id;

        SwitchingProvider(int id) {
          this.id = id;
        }

        @SuppressWarnings("unchecked")
        @Override
        public T get() {
          switch (id) {
            case 0: // edu.yujie.hiltex.arch.HiltViewModel_AssistedFactory
            return (T) ActivityCImpl.this.getHiltViewModel_AssistedFactory();

            case 1: // edu.yujie.hiltex.arch.HiltRepository
            return (T) ActivityCImpl.this.getHiltRepository();

            default: throw new AssertionError(id);
          }
        }
      }
    }
  }

  private final class ServiceCBuilder implements App_HiltComponents.ServiceC.Builder {
    private Service service;

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public App_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(service);
    }
  }

  private final class ServiceCImpl extends App_HiltComponents.ServiceC {
    private ServiceCImpl(Service service) {

    }
  }
}

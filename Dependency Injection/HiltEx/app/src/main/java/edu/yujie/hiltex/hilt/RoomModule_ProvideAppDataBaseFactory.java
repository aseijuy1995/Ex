package edu.yujie.hiltex.hilt;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import edu.yujie.hiltex.room.AppResultDataBase;
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
public final class RoomModule_ProvideAppDataBaseFactory implements Factory<AppResultDataBase> {
  private final Provider<Application> applicationProvider;

  public RoomModule_ProvideAppDataBaseFactory(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  @Override
  public AppResultDataBase get() {
    return provideAppDataBase(applicationProvider.get());
  }

  public static RoomModule_ProvideAppDataBaseFactory create(
      Provider<Application> applicationProvider) {
    return new RoomModule_ProvideAppDataBaseFactory(applicationProvider);
  }

  public static AppResultDataBase provideAppDataBase(Application application) {
    return Preconditions.checkNotNull(RoomModule.INSTANCE.provideAppDataBase(application), "Cannot return null from a non-@Nullable @Provides method");
  }
}

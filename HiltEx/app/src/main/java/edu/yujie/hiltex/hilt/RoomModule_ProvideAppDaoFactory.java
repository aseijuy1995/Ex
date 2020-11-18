package edu.yujie.hiltex.hilt;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import edu.yujie.hiltex.room.AppDao;
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
public final class RoomModule_ProvideAppDaoFactory implements Factory<AppDao> {
  private final Provider<AppResultDataBase> appResultDatabaseProvider;

  public RoomModule_ProvideAppDaoFactory(Provider<AppResultDataBase> appResultDatabaseProvider) {
    this.appResultDatabaseProvider = appResultDatabaseProvider;
  }

  @Override
  public AppDao get() {
    return provideAppDao(appResultDatabaseProvider.get());
  }

  public static RoomModule_ProvideAppDaoFactory create(
      Provider<AppResultDataBase> appResultDatabaseProvider) {
    return new RoomModule_ProvideAppDaoFactory(appResultDatabaseProvider);
  }

  public static AppDao provideAppDao(AppResultDataBase appResultDatabase) {
    return Preconditions.checkNotNull(RoomModule.INSTANCE.provideAppDao(appResultDatabase), "Cannot return null from a non-@Nullable @Provides method");
  }
}

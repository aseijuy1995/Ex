package edu.yujie.hiltex.hilt;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class QualifierModule_ProvideTasksLocalDataSourceFactory implements Factory<DataSource> {
  @Override
  public DataSource get() {
    return provideTasksLocalDataSource();
  }

  public static QualifierModule_ProvideTasksLocalDataSourceFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DataSource provideTasksLocalDataSource() {
    return Preconditions.checkNotNull(QualifierModule.INSTANCE.provideTasksLocalDataSource(), "Cannot return null from a non-@Nullable @Provides method");
  }

  private static final class InstanceHolder {
    private static final QualifierModule_ProvideTasksLocalDataSourceFactory INSTANCE = new QualifierModule_ProvideTasksLocalDataSourceFactory();
  }
}

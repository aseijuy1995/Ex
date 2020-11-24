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
public final class QualifierModule_ProvideTasksRemoteDataSourceFactory implements Factory<DataSource> {
  @Override
  public DataSource get() {
    return provideTasksRemoteDataSource();
  }

  public static QualifierModule_ProvideTasksRemoteDataSourceFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DataSource provideTasksRemoteDataSource() {
    return Preconditions.checkNotNull(QualifierModule.INSTANCE.provideTasksRemoteDataSource(), "Cannot return null from a non-@Nullable @Provides method");
  }

  private static final class InstanceHolder {
    private static final QualifierModule_ProvideTasksRemoteDataSourceFactory INSTANCE = new QualifierModule_ProvideTasksRemoteDataSourceFactory();
  }
}

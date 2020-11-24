package edu.yujie.hiltex.hilt;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class QualifierModule_ProvideTasksRepositoryFactory implements Factory<Repository> {
  private final Provider<DataSource> dataSource1Provider;

  private final Provider<DataSource> dataSource2Provider;

  public QualifierModule_ProvideTasksRepositoryFactory(Provider<DataSource> dataSource1Provider,
      Provider<DataSource> dataSource2Provider) {
    this.dataSource1Provider = dataSource1Provider;
    this.dataSource2Provider = dataSource2Provider;
  }

  @Override
  public Repository get() {
    return provideTasksRepository(dataSource1Provider.get(), dataSource2Provider.get());
  }

  public static QualifierModule_ProvideTasksRepositoryFactory create(
      Provider<DataSource> dataSource1Provider, Provider<DataSource> dataSource2Provider) {
    return new QualifierModule_ProvideTasksRepositoryFactory(dataSource1Provider, dataSource2Provider);
  }

  public static Repository provideTasksRepository(DataSource dataSource1, DataSource dataSource2) {
    return Preconditions.checkNotNull(QualifierModule.INSTANCE.provideTasksRepository(dataSource1, dataSource2), "Cannot return null from a non-@Nullable @Provides method");
  }
}

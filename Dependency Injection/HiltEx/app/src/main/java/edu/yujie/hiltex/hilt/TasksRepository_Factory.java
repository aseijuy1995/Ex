package edu.yujie.hiltex.hilt;

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
public final class TasksRepository_Factory implements Factory<TasksRepository> {
  private final Provider<DataSource> dataSource1Provider;

  private final Provider<DataSource> dataSource2Provider;

  public TasksRepository_Factory(Provider<DataSource> dataSource1Provider,
      Provider<DataSource> dataSource2Provider) {
    this.dataSource1Provider = dataSource1Provider;
    this.dataSource2Provider = dataSource2Provider;
  }

  @Override
  public TasksRepository get() {
    return newInstance(dataSource1Provider.get(), dataSource2Provider.get());
  }

  public static TasksRepository_Factory create(Provider<DataSource> dataSource1Provider,
      Provider<DataSource> dataSource2Provider) {
    return new TasksRepository_Factory(dataSource1Provider, dataSource2Provider);
  }

  public static TasksRepository newInstance(DataSource dataSource1, DataSource dataSource2) {
    return new TasksRepository(dataSource1, dataSource2);
  }
}

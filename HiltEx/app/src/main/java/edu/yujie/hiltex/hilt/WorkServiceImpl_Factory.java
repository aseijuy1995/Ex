package edu.yujie.hiltex.hilt;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class WorkServiceImpl_Factory implements Factory<WorkServiceImpl> {
  @Override
  public WorkServiceImpl get() {
    return newInstance();
  }

  public static WorkServiceImpl_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WorkServiceImpl newInstance() {
    return new WorkServiceImpl();
  }

  private static final class InstanceHolder {
    private static final WorkServiceImpl_Factory INSTANCE = new WorkServiceImpl_Factory();
  }
}

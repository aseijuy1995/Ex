package edu.yujie.junitex

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(ExampleUnitTest::class, ExampleUnitTest2::class)//聚集測試類
class SuiteTest
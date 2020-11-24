package edu.yujie.koinex

import edu.yujie.koinex.module.ComponentA
import edu.yujie.koinex.module.ComponentB
import edu.yujie.koinex.module.appModule
import io.mockk.mockkClass
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.inject
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito

class MyTest : KoinTest {
    val b: ComponentB by inject()

    @Test
    fun `should inject mt componentB`() {
        val module = module {
            single { ComponentA() }
            single { ComponentB(get()) }
        }
        startKoin {
            modules(module)
        }

        val a: ComponentA = get<ComponentA>()
        assertNotNull(b.a)
        assertEquals(b.a, a)
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(appModule)
    }

    @get:Rule
    val mockProvider = MockProviderRule.create {
        Mockito.mock(it.java)
        mockkClass(it)
    }


}
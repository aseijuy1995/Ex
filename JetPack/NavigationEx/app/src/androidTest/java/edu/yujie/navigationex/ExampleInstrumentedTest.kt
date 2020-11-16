package edu.yujie.navigationex

<<<<<<< HEAD
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import io.mockk.verify
=======
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("edu.yujie.navigationex", appContext.packageName)
    }
<<<<<<< HEAD

//    @Test
//    fun testNavigationToFragFour() {
//        val navController = mockk<NavController>()
//
//        val thirdScenario = FragmentScenario.launchInContainer(ThirdFragment::class.java)
//
//        thirdScenario.onFragment {
//            Navigation.setViewNavController(it.requireView(), navController)
//        }
//
//
//        Espresso.onView(ViewMatchers.withId(R.id.textView)).perform(ViewActions.click())
//
//        verify {
//            navController.navigate(R.id.action_frag_first_to_frag_second)
//        }
//    }




=======
>>>>>>> 5353c08b8648ab256f1ef3dde1fdfbf3127f072e
}
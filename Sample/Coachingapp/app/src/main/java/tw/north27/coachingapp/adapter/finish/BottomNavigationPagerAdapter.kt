package tw.north27.coachingapp.adapter.finish

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import tw.north27.coachingapp.model.result.Authority
import tw.north27.coachingapp.ui2.fragment.main.MainHomeFragment
import tw.north27.coachingapp.ui2.fragment.main.NoticeCenterFragment
import tw.north27.coachingapp.ui2.fragment.main.PersonalCenterFragment
import tw.north27.coachingapp.ui2.fragment.main.QuestionAreaFragment

sealed class BottomNavigationIndex(val index: Int) {
    class MainHome(index: Int) : BottomNavigationIndex(index)
    class QuestionArea(index: Int) : BottomNavigationIndex(index)
    class StudyRoom(index: Int) : BottomNavigationIndex(index)
    class NoticeCenter(index: Int) : BottomNavigationIndex(index)
    class PersonalCenter(index: Int) : BottomNavigationIndex(index)
}

class BottomNavigationPagerAdapter(
    fragManager: FragmentManager,
    lifecycle: Lifecycle,
    val auth: String
) : FragmentStateAdapter(fragManager, lifecycle) {

    val fragmentFactory: Map<Int, () -> Fragment> = when (auth) {
        Authority.TEACHER.toString() -> {
            mapOf(
                BottomNavigationIndex.MainHome(0).index to { MainHomeFragment() },
                BottomNavigationIndex.QuestionArea(1).index to { QuestionAreaFragment() },
                BottomNavigationIndex.NoticeCenter(2).index to { NoticeCenterFragment() },
                BottomNavigationIndex.PersonalCenter(3).index to { PersonalCenterFragment() },
            )
        }
        Authority.STUDENT.toString() -> {
            mapOf(
                BottomNavigationIndex.QuestionArea(0).index to { QuestionAreaFragment() },
                BottomNavigationIndex.NoticeCenter(1).index to { NoticeCenterFragment() },
                BottomNavigationIndex.PersonalCenter(2).index to { PersonalCenterFragment() },
            )
        }
        else -> {
            mapOf()
        }
    }

    override fun getItemCount(): Int {
        return fragmentFactory.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentFactory[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}
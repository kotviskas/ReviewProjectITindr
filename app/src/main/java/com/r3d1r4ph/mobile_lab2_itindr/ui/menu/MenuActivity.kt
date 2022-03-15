package com.r3d1r4ph.mobile_lab2_itindr.ui.menu

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ActivityMenuBinding
import com.r3d1r4ph.mobile_lab2_itindr.ui.BaseActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats.ChatsFragment
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people.PeopleFragment
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.profile.ProfileFragment
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.search.SearchFragment
import com.r3d1r4ph.mobile_lab2_itindr.utils.interfaces.FragmentNavigator

class MenuActivity : BaseActivity(), FragmentNavigator {

    private val viewBinding by viewBinding(ActivityMenuBinding::bind, R.id.rootLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        configureBottomNavigationView()
    }

    private fun configureBottomNavigationView() {
        with(viewBinding.menuBottomNavigationView) {
            setOnItemSelectedListener { item ->
                performBottomNavigation(item)
            }
            selectedItemId = R.id.searchItem
        }
    }

    private fun performBottomNavigation(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchItem -> {
                navigateToFragment(SearchFragment.TAG, SearchFragment.newInstance())
            }
            R.id.peopleItem -> {
                navigateToFragment(PeopleFragment.TAG, PeopleFragment.newInstance())
            }
            R.id.chatsItem -> {
                navigateToFragment(ChatsFragment.TAG, ChatsFragment.newInstance())
            }
            R.id.profileItem -> {
                navigateToFragment(ProfileFragment.TAG, ProfileFragment.newInstance())
            }
        }
        return true
    }

    override fun navigateToFragment(tag: String, fragment: Fragment, bundle: Bundle?) {
        supportFragmentManager.commit {
            replace(
                R.id.menuFragmentContainerView,
                fragment,
                tag
            )
        }
    }
}
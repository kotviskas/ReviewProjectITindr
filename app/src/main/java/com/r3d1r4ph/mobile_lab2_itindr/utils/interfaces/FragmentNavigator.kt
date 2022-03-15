package com.r3d1r4ph.mobile_lab2_itindr.utils.interfaces

import android.os.Bundle
import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun navigateToFragment(tag: String, fragment: Fragment, bundle: Bundle? = null)
}
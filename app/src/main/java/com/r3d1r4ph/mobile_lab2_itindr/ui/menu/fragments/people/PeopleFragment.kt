package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.FragmentPeopleBinding
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people.recycler.UserItemDecoration
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people.recycler.UserPagingAdapter
import com.r3d1r4ph.mobile_lab2_itindr.ui.profile.rate.RateProfileActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.profile.rate.RateProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class PeopleFragment : Fragment(R.layout.fragment_people) {

    companion object {
        val TAG = PeopleFragment::class.java.simpleName
        fun newInstance() = PeopleFragment()
    }

    private val viewBinding by viewBinding(FragmentPeopleBinding::bind)
    private val viewModel by viewModels<PeopleViewModel>()

    private val userPagingAdapterListener = object : UserPagingAdapter.UserAdapterListener {
        override fun onItemClick(item: ProfileData) {
            val intent = Intent(requireContext(), RateProfileActivity::class.java)
                .putExtra(RateProfileViewModel.USER_ID_KEY, item.userId)
            startActivity(intent)
        }
    }
    private val userPagingAdapter = UserPagingAdapter(userPagingAdapterListener)


    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        subscribeOnUsersFlow()
    }

    private fun configureRecyclerView() = with(viewBinding) {
        peopleRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
            adapter = userPagingAdapter
            addItemDecoration(UserItemDecoration())
        }
    }

    @ExperimentalPagingApi
    private fun subscribeOnUsersFlow() {
        lifecycleScope.launch {
            viewModel.fetchUsers().distinctUntilChanged().collectLatest { pagingData ->
                userPagingAdapter.submitData(pagingData)
            }
        }
    }
}
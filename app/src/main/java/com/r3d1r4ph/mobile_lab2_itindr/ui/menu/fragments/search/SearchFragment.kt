package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.FragmentSearchBinding
import com.r3d1r4ph.mobile_lab2_itindr.ui.aboutuser.AboutUserDialogFragment
import com.r3d1r4ph.mobile_lab2_itindr.ui.match.MatchDialogFragment
import com.r3d1r4ph.mobile_lab2_itindr.utils.GeneralUtils

class SearchFragment : Fragment(R.layout.fragment_search) {

    companion object {
        val TAG: String = SearchFragment::class.java.simpleName
        fun newInstance() = SearchFragment()
    }

    private val viewBinding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<SearchViewModel>()

    private var detailedReviewDialog: AboutUserDialogFragment? = null
    private var matchDialog: MatchDialogFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.searchProfileInfoInclude.profileAvatarImageView.clipToOutline = true
        setOnClickListeners()
        setObservers()
        doRequestsToServer()
    }

    private fun setObservers() {

        observeProfileInclude()
        observeIsMutual()
    }

    private fun observeProfileInclude() {
        viewModel.currentUser.observe(this) { profile ->
            if (profile != null) {
                GeneralUtils.inflateProfileInfoInclude(
                    viewBinding.searchProfileInfoInclude,
                    profile,
                    layoutInflater
                )
            } else {
                GeneralUtils.inflateProfileInfoIncludeWithPlaceholder(
                    viewBinding.searchProfileInfoInclude,
                    layoutInflater
                )
                Toast.makeText(requireContext(), "that's all", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeIsMutual() {
        viewModel.likeIsMutual.observe(this) {
            if (it) {
                viewModel.getCurrentUserId()
                    ?.let { userId ->
                        matchDialog = MatchDialogFragment.newInstance(userId)
                        matchDialog?.show(
                            requireActivity().supportFragmentManager,
                            MatchDialogFragment.TAG
                        )
                    }
            }
        }
    }

    private fun setOnClickListeners() {
        viewBinding.searchCancelMaterialButton.setOnClickListener {
            viewModel.dislikeUser {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewBinding.searchLikeMaterialButton.setOnClickListener {
            viewModel.likeUser {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewBinding.searchProfileInfoInclude.profileAvatarImageView.setOnClickListener {
            viewModel.getCurrentUserId()?.let { userId ->
                detailedReviewDialog = AboutUserDialogFragment.newInstance(userId)
                detailedReviewDialog?.show(
                    requireActivity().supportFragmentManager,
                    AboutUserDialogFragment.TAG
                )
            }
        }
    }

    private fun doRequestsToServer() {
        viewModel.getUserFeed {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }
}
package com.r3d1r4ph.mobile_lab2_itindr.ui.profile

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.chip.Chip
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.FragmentProfileInfoBinding
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileParams
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.MenuActivity
import com.r3d1r4ph.mobile_lab2_itindr.utils.ImagePicker
import com.r3d1r4ph.mobile_lab2_itindr.utils.interfaces.SaveButtonOnClick

class ProfileInfoFragment : Fragment(R.layout.fragment_profile_info) {
    companion object {
        const val PROFILE_CREATE_MODE = "PROFILE CREATE MODE"
        const val PROFILE_EDIT_MODE = "PROFILE EDIT MODE"
        private const val PROFILE_MODE = "PROFILE MODE"
        val TAG: String = ProfileInfoFragment::class.java.simpleName
        fun newInstance(mode: String) =
            ProfileInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(PROFILE_MODE, mode)
                }
            }
    }

    private val viewBinding by viewBinding(FragmentProfileInfoBinding::bind)
    private val viewModel by viewModels<ProfileInfoViewModel>()
    private var saveButtonOnSuccess = {
        requireActivity().finish()
    }

    private var avatarUri: Uri? = null
    private lateinit var imagePicker: ImagePicker

    private val openGalleryHandler = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGrantedCheck(
            granted,
            { imagePicker.pickGalleryImage() },
            Manifest.permission.READ_EXTERNAL_STORAGE,
            R.string.permission_denied_gallery
        )
    }

    private val openCameraHandler = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGrantedCheck(
            granted,
            { imagePicker.takeCameraPhoto(requireContext()) },
            Manifest.permission.CAMERA,
            R.string.permission_denied_camera
        )
    }

    private fun permissionGrantedCheck(
        granted: Boolean,
        pickImage: () -> Unit,
        permission: String,
        permissionDenied: Int
    ) {
        when {
            granted -> {
                pickImage()
            }
            else -> {
                permissionDeniedOutput(
                    permission,
                    permissionDenied
                )
            }
        }
    }

    private fun permissionDeniedOutput(permission: String, permissionDenied: Int) {
        if (!shouldShowRequestPermissionRationale(permission)) {
            Toast.makeText(
                requireContext(),
                getString(R.string.permission_denied_with_check_mark),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                getString(permissionDenied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imagePicker = ImagePicker(
            requireActivity().activityResultRegistry,
            requireActivity(),
            { result ->
                if (result) {
                    setAvatarByUri(ImagePicker.currentAvatarUri)
                }
            },
            { imageUri ->
                imageUri?.let { uri ->
                    setAvatarByUri(uri)
                }
            })

        viewModel.updateTopics {
            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        }

        setObservers()
        configureProfileByMode(arguments?.getString(PROFILE_MODE).toString())
        viewBinding.infoAvatarImageView.clipToOutline = true
        setButtonClickListeners()
    }

    private fun setObservers() {
        setTopicsObserve()
        setPreProfileObserve()
    }

    private fun setTopicsObserve() {
        viewModel.topics.observe(this) { list ->
            viewBinding.infoTopicsChipGroup.removeAllViews()
            list.map { topic ->
                val chip = layoutInflater.inflate(R.layout.chip_template, null) as Chip
                chip.tag = topic.id
                chip.text = topic.title
                setChipTypeface(chip)
                viewBinding.infoTopicsChipGroup.addView(chip)
            }
        }
    }

    private fun setChipTypeface(chip: Chip) {
        chip.setOnCheckedChangeListener { view, isChecked ->
            view.typeface = when (isChecked) {
                true -> ResourcesCompat.getFont(requireContext(), R.font.montserrat_bold_700)
                false -> ResourcesCompat.getFont(requireContext(), R.font.montserrat_regular_400)
            }
        }
    }

    private fun setPreProfileObserve() {
        viewModel.readyToObserveProfile.observe(this) {
            if (it) {
                setProfileDataObserve()
            }
        }
    }

    private fun setProfileDataObserve() {
        viewModel.profileData.observe(this) { profile ->
            viewBinding.infoNameTextInputEditText.setText(profile.name)
            profile.aboutMyself?.let {
                viewBinding.infoAboutYourselfTextInputEditText.setText(
                    it
                )
            }
            profile.avatar?.let { url -> setAvatarByUri(url.toUri()) }
            profile.topics.map {
                viewBinding.infoTopicsChipGroup.findViewWithTag<Chip>(it.id).isChecked =
                    true
            }
        }
    }

    private fun configureProfileByMode(profileMode: String) {
        if (profileMode == PROFILE_CREATE_MODE) {
            viewBinding.infoAboutYourselfTextView.text =
                resources.getString(R.string.create_profile_tell_about_yourself)
            viewBinding.infoTypeInterestsTextView.text =
                resources.getString(R.string.create_profile_specify_interests)
            saveButtonOnSuccess = {
                val intent = Intent(requireContext(), MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setAvatarByUri(uri: Uri) {
        viewBinding.infoAvatarImageView.load(uri)
        avatarUri = uri
        viewBinding.infoPickPhotoButton.text =
            resources.getString(R.string.info_remove_photo)
    }

    private fun setButtonClickListeners() {
        viewBinding.infoPickPhotoButton.setOnClickListener {

            if (viewBinding.infoPickPhotoButton.text == resources.getString(R.string.info_pick_photo)) {
                createDialogToPickImage()
            } else {
                removeAvatar()
            }
        }

        val topics = mutableListOf<String>()
        for (chipId in viewBinding.infoTopicsChipGroup.checkedChipIds) {
            topics.add(viewBinding.infoTopicsChipGroup.findViewById<Chip>(chipId).tag.toString())
        }

        (activity as SaveButtonOnClick).setSaveButtonOnClick {

            viewModel.saveProfile(
                profileParams = ProfileParams(
                    viewBinding.infoNameTextInputEditText.text.toString(),
                    if (viewBinding.infoAboutYourselfTextInputEditText.text.toString() == "") null
                    else viewBinding.infoAboutYourselfTextInputEditText.text.toString(),
                    topics as List<String>
                ),
                avatarUri = avatarUri,
                context = requireContext(),
                onFailure = { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            )
        }
    }

    private fun createDialogToPickImage() {
        val options = arrayOf(
            getString(R.string.dialog_take_photo),
            getString(R.string.dialog_open_photo),
            getString(R.string.dialog_dismiss)
        )

        val builder = AlertDialog.Builder(requireContext())

        builder.setItems(options) { dialog, item ->
            when (options[item]) {
                getString(R.string.dialog_take_photo) -> {
                    openCameraHandler.launch(Manifest.permission.CAMERA)
                }
                getString(R.string.dialog_open_photo) -> {
                    openGalleryHandler.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                getString(R.string.dialog_dismiss) -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private fun removeAvatar() {
        viewBinding.infoAvatarImageView.load(R.drawable.ic_default_avatar)
        avatarUri = null
        viewBinding.infoPickPhotoButton.text = resources.getString(R.string.info_pick_photo)
    }
}
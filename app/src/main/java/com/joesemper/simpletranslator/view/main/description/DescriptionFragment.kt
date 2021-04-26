package com.joesemper.simpletranslator.view.main.description

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.joesemper.simpletranslator.R
import com.joesemper.simpletranslator.utils.network.isOnline
import com.joesemper.simpletranslator.utils.ui.AlertDialogFragment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_description.*

class DescriptionFragment: Fragment(R.layout.fragment_description) {

    private val args: DescriptionFragmentArgs by navArgs()

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "com.joesemper.simpletranslator.view.main.description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActionbarHomeButtonAsUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSwipeToRefreshListener()
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        with(requireActivity() as AppCompatActivity) {
            supportActionBar?.setHomeButtonEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setData() {
        tv_description_header.text = args.title
        tv_description.text = args.description
        if (args.img.isBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            usePicassoToLoadPhoto(iv_description, args.img)
        }
    }

    private fun setSwipeToRefreshListener() {
        description_fragment_swipe_refresh.setOnRefreshListener {
            startLoadingOrShowError()
        }
    }

    private fun startLoadingOrShowError() {
        if (isOnline(requireContext())) {
            setData()
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                requireActivity().supportFragmentManager,
                DIALOG_FRAGMENT_TAG
            )
            stopRefreshAnimationIfNeeded()
        }
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (description_fragment_swipe_refresh.isRefreshing) {
            description_fragment_swipe_refresh.isRefreshing = false
        }
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.with(requireContext()).load("https:$imageLink")
            .placeholder(R.drawable.ic_no_photo_vector).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()
                }

                override fun onError() {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            })
    }
}
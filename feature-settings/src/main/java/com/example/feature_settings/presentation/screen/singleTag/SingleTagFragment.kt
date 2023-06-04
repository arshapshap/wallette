package com.example.feature_settings.presentation.screen.singleTag

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.domain.models.Tag
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.common.presentation.extensions.*
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentSingleTagBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.google.android.flexbox.*
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog

class SingleTagFragment :
    BaseFragment<SingleTagViewModel>(R.layout.fragment_single_tag) {

    companion object {

        const val TAG_KEY = "category_key"
    }

    private val binding by viewBinding(FragmentSingleTagBinding::bind)
    private val component: SettingsComponent by lazy {
        FeatureUtils.getFeature(this, SettingsFeatureApi::class.java)
    }

    override fun inject() {
        component.inject(this)
    }

    @Suppress("DEPRECATION")
    override fun createViewModel(): BaseViewModel {
        return component.singleTagViewModel()
            .create(arguments?.getSerializable(TAG_KEY) as? Tag)
    }

    override fun initViews() {
        with (binding.saveLayout) {
            setStrokeVisibility(true)
            setColor(getColorPrimary())
            setImage(com.example.common.R.drawable.ic_done)
            setTitle(R.string.save)
            setOnClickListener {
                viewModel.save()
            }
        }

        with (binding.tagColorLayout) {
            setStrokeVisibility(true)
            setRightArrowVisible(true)
            setImage(R.drawable.ic_pallette)
            setTitle(R.string.tag_color)
            setOnClickListener {
                openColorPickerDialog {
                    viewModel.selectColor(it)
                }
            }
        }

        binding.tagNameEditText.doAfterTextChanged {
            viewModel.editName(it.toString())
        }
    }

    override fun subscribe() {
        viewModel.startTagLiveData.observe(viewLifecycleOwner) {
            it?.name?.let {
                binding.tagNameEditText.setText(it)
            }
        }

        viewModel.editingTagLiveData.observe(viewLifecycleOwner) {
            it?.color?.let {
                binding.tagColorLayout.setColor(it)
                binding.tagColorLayout.setOnClickListener {
                    openColorPickerDialog(it) {
                        viewModel.selectColor(it)
                    }
                }
            }
        }
    }

    private fun openColorPickerDialog(@ColorInt currentColor: Int? = null, onSelect: (Int) -> Unit) {
        val colorPicker: ColorPickerDialog = ColorPickerDialog.Builder()
            .setInitialColor(currentColor ?: Color.BLACK)
            .setColorModel(ColorModel.RGB)
            .setButtonOkText(android.R.string.ok)
            .setButtonCancelText(android.R.string.cancel)
            .onColorSelected { color: Int ->
                onSelect.invoke(color)
            }
            .create()
        colorPicker.show(childFragmentManager, "color_picker")
    }
}
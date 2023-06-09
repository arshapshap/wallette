package com.example.feature_settings.presentation.screen.tags

import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.di.FeatureUtils
import com.example.common.presentation.base.BaseFragment
import com.example.common.presentation.base.BaseViewModel
import com.example.feature_settings.R
import com.example.feature_settings.databinding.FragmentTagsBinding
import com.example.feature_settings.di.SettingsComponent
import com.example.feature_settings.di.SettingsFeatureApi
import com.example.feature_settings.presentation.screen.tags.recyclerView.TagsAdapter
import com.example.feature_settings.presentation.utils.*

class TagsFragment : BaseFragment<TagsViewModel>(R.layout.fragment_tags) {

    private val binding by viewBinding(FragmentTagsBinding::bind)
    private val component: SettingsComponent by lazy {
        FeatureUtils.getFeature(this, SettingsFeatureApi::class.java)
    }

    override fun inject() {
        component.inject(this)
    }

    override fun createViewModel(): BaseViewModel {
        return component.tagsViewModel().create()
    }

    override fun initViews() {
        with(binding.addTagLayout) {
            setStrokeVisibility(true)
            setRightArrowVisible(true)
            setColor(getColorPrimary())
            setImage(R.drawable.ic_plus_circle)
            setTitle(R.string.add_tag)
            setOnClickListener {
                viewModel.openTagCreating()
            }
        }

        binding.listRecyclerView.adapter = TagsAdapter {
            viewModel.openTag(it)
        }
    }

    override fun subscribe() {
        viewModel.listLiveData.observe(viewLifecycleOwner) {
            getTagsAdapter()?.setList(it)
        }
    }

    private fun getTagsAdapter()
        = (binding.listRecyclerView.adapter as? TagsAdapter)
}
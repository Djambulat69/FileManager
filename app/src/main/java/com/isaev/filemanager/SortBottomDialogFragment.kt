package com.isaev.filemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.color.MaterialColors
import com.isaev.filemanager.databinding.DialogSortByBinding


class SortBottomDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogSortByBinding? = null
    private val binding: DialogSortByBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSortByBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            requireArguments().getString(CURRENT_SORT_OPTION_ARG)?.let { optionName ->
                makeChecked(
                    when (SortOption.valueOf(optionName)) {
                        SortOption.NEWEST_FIRST -> binding.newestFirstSortOption
                        SortOption.OLDEST_FIRST -> binding.oldestFirstSortOption
                        SortOption.LARGEST_FIRST -> binding.largestFirstSortOption
                        SortOption.SMALLEST_FIRST -> binding.smallestFirstSortOption
                        SortOption.NAME_A_Z -> binding.nameAZSortOption
                        SortOption.NAME_Z_A -> binding.nameZASortOption
                    }
                )
            }

            newestFirstSortOption.setOnClickListener {
                (requireActivity() as? SortDialogCallback)?.sortOption(SortOption.NEWEST_FIRST)
                dismiss()
            }
            oldestFirstSortOption.setOnClickListener {
                (requireActivity() as? SortDialogCallback)?.sortOption(SortOption.OLDEST_FIRST)
                dismiss()
            }
            largestFirstSortOption.setOnClickListener {
                (requireActivity() as? SortDialogCallback)?.sortOption(SortOption.LARGEST_FIRST)
                dismiss()
            }
            smallestFirstSortOption.setOnClickListener {
                (requireActivity() as? SortDialogCallback)?.sortOption(SortOption.SMALLEST_FIRST)
                dismiss()
            }
            nameAZSortOption.setOnClickListener {
                (requireActivity() as? SortDialogCallback)?.sortOption(SortOption.NAME_A_Z)
                dismiss()
            }
            nameZASortOption.setOnClickListener {
                (requireActivity() as? SortDialogCallback)?.sortOption(SortOption.NAME_Z_A)
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun makeChecked(textView: TextView) {
        val primaryColor = MaterialColors.getColor(
            requireContext(),
            com.google.android.material.R.attr.colorPrimary,
            resources.getColor(R.color.purple_500, requireContext().theme)
        )
        textView.setTextColor(primaryColor)
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            ResourcesCompat.getDrawable(resources, R.drawable.check, requireContext().theme),
            null
        )


    }

    companion object {

        fun newInstance(currentSortOption: SortOption) = SortBottomDialogFragment().apply {
            arguments = bundleOf(CURRENT_SORT_OPTION_ARG to currentSortOption.name)
        }

        private const val CURRENT_SORT_OPTION_ARG = "current sort option"
    }
}
package com.exfarnanda1945.my_foody_course.ui.fragments.recipes.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.exfarnanda1945.my_foody_course.databinding.RecipesBottomSheetBinding
import com.exfarnanda1945.my_foody_course.util.Constants.DEFAULT_DIET_TYPE
import com.exfarnanda1945.my_foody_course.util.Constants.DEFAULT_MEAL_TYPE
import com.exfarnanda1945.my_foody_course.viewmodels.RecipeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RecipesBottomSheet : BottomSheetDialogFragment() {
    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val recipesViewModel: RecipeViewModel by activityViewModels()

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateCheckedChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateCheckedChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        }

        binding.apply {
            mealTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                val chipMealSelected = group.findViewById<Chip>(checkedIds[0])
                mealTypeChip = chipMealSelected.text.toString().lowercase(Locale.ROOT)
                mealTypeChipId = chipMealSelected.id
            }
            dietTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
                val chipDietSelected = group.findViewById<Chip>(checkedIds[0])
                dietTypeChip = chipDietSelected.text.toString().lowercase(Locale.ROOT)
                dietTypeChipId = chipDietSelected.id
            }
            applyBtn.setOnClickListener {
                recipesViewModel.saveMealAndDietTypeTemp(
                    mealTypeChip,
                    mealTypeChipId,
                    dietTypeChip,
                    dietTypeChipId
                )

                val action =
                    RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
                findNavController().navigate(action)
            }
        }

        return view
    }

    private fun updateCheckedChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            val targetView =chipGroup.findViewById<Chip>(chipId)
            targetView.isChecked = true
            chipGroup.requestChildFocus(targetView,targetView)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
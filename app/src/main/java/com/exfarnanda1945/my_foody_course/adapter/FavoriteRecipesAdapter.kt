package com.exfarnanda1945.my_foody_course.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.exfarnanda1945.my_foody_course.R
import com.exfarnanda1945.my_foody_course.data.database.entities.FavoriteRecipesEntity
import com.exfarnanda1945.my_foody_course.databinding.FavoriteRecipesRowLayoutBinding
import com.exfarnanda1945.my_foody_course.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.exfarnanda1945.my_foody_course.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteViewHolder>(), ActionMode.Callback {

    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View

    private val diffCallback = object : DiffUtil.ItemCallback<FavoriteRecipesEntity>() {
        override fun areItemsTheSame(
            oldItem: FavoriteRecipesEntity,
            newItem: FavoriteRecipesEntity
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: FavoriteRecipesEntity,
            newItem: FavoriteRecipesEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    private var multiSelection = false
    private var selectedRecipes = arrayListOf<FavoriteRecipesEntity>()
    private var setOfMyViewHolder = arrayListOf<FavoriteViewHolder>()


    inner class FavoriteViewHolder(private val itemBinding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        val cardLayout = itemBinding.favoriteRecipesRowLayout
        val card = itemBinding.favoriteRowCardview

        fun bind(data: FavoriteRecipesEntity) {
            itemBinding.apply {
                favoriteRecipesEntity = data
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            FavoriteRecipesRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        rootView = holder.itemView.rootView
        setOfMyViewHolder.add(holder)
        val data = differ.currentList[position]
        saveItemStateOnScroll(data,holder)
        holder.bind(data)
        holder.cardLayout.apply {
            // Single Click
            setOnClickListener {
                if (multiSelection) {
                    applySelection(holder, data)
                } else {
                    holder.itemView.findNavController().navigate(
                        FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                            data.result
                        )
                    )
                }
            }

            // Long Click
            setOnLongClickListener {
                if (multiSelection) {
                    applySelection(holder, data)
                    true
                } else {
                    multiSelection = true
                    requireActivity.startActionMode(this@FavoriteRecipesAdapter)
                    applySelection(holder, data)
                    true
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mActionMode = mode!!
        mode.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(
        mode: ActionMode?,
        item: MenuItem?
    ): Boolean {
        if (item?.itemId == R.id.delete_favorite_recipe_menu) {
            selectedRecipes.forEach {
                mViewModel.deleteFavoriteRecipe(it)
            }

            val snackBarMsg =
                if (selectedRecipes.size == 1) "${selectedRecipes.size} recipe removed." else "${selectedRecipes.size} recipes removed."

            showSnackBar(snackBarMsg)
            multiSelection = false
            selectedRecipes.clear()
            mActionMode.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        setOfMyViewHolder.forEach { vw ->
            changeRecipeStyle(
                vw,
                R.color.cardBackgroundColor,
                R.color.strokeColor
            )
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }


    fun setData(data: List<FavoriteRecipesEntity>?) {
        differ.submitList(data)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    private fun applySelection(holder: FavoriteViewHolder, currentRecipe: FavoriteRecipesEntity) {

        // if recipe selected already inside selected recipes, then will remove the selected recipe also change the color
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            applyTitleActionMode()
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        } else {
            // if recipe selected not inside selected recipes then will be added
            selectedRecipes.add(currentRecipe)
            applyTitleActionMode()
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        }
    }

    private fun changeRecipeStyle(
        holder: FavoriteViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {
        holder.apply {
            cardLayout.setBackgroundColor(ContextCompat.getColor(requireActivity, backgroundColor))
            card.strokeColor = ContextCompat.getColor(requireActivity, strokeColor)
        }
    }

    private fun applyTitleActionMode() {

        when (selectedRecipes.size) {
            0 -> {
                multiSelection = false
                mActionMode.finish()
            }
            else -> mActionMode.title = "${selectedRecipes.size} items selected."
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_SHORT).setAction("Okay") {}.show()
    }

    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized) {
            mActionMode.finish()
        }
    }

    fun saveItemStateOnScroll(currentRecipe: FavoriteRecipesEntity, holder: FavoriteViewHolder) {
        if (selectedRecipes.contains(currentRecipe)) {
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
        } else {
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
    }


}
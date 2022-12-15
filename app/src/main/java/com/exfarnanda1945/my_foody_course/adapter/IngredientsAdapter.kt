package com.exfarnanda1945.my_foody_course.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.exfarnanda1945.my_foody_course.databinding.IngredientsRowLayoutBinding
import com.exfarnanda1945.my_foody_course.model.ExtendedIngredientsItem

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ExtendedIngredientsItem>() {
        override fun areItemsTheSame(
            oldItem: ExtendedIngredientsItem,
            newItem: ExtendedIngredientsItem
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ExtendedIngredientsItem,
            newItem: ExtendedIngredientsItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)


    class IngredientsViewHolder(private val itemBinding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(data: ExtendedIngredientsItem) {
            itemBinding.ingredient = data
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(
            IngredientsRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setData(result: List<ExtendedIngredientsItem>?) {
        differ.submitList(result)
    }
}
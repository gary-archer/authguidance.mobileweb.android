package com.authguidance.mobileweb.views.errors

import androidx.recyclerview.widget.RecyclerView
import com.authguidance.mobileweb.databinding.ErrorListItemBinding

/*
 * A custom view holder class for error rows in a recycler view
 */
class ErrorListItemViewHolder(
    val layoutBinding: ErrorListItemBinding
) : RecyclerView.ViewHolder(layoutBinding.root) {

    /*
     * Bind a data item to a layout row
     */
    fun bind(item: ErrorListItemViewModel) {
        this.layoutBinding.model = item
        this.layoutBinding.executePendingBindings()
    }
}

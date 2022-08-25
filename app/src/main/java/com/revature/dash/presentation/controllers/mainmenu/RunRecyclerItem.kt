package com.revature.dash.presentation.controllers.mainmenu

import android.graphics.Color
import android.view.View
import com.revature.dash.R
import com.revature.dash.databinding.RunningItemBinding
import com.revature.dash.model.data.RunDay
import com.xwray.groupie.viewbinding.BindableItem

class RunRecyclerItem(val runDay: RunDay, private val selected:Boolean = false)
    :BindableItem<RunningItemBinding>(){
    override fun bind(viewBinding: RunningItemBinding, position: Int) {

        viewBinding.itemCompletion.text =
            if(runDay.completed) "Completed!" else "You got this!"

            viewBinding.root.setCardBackgroundColor(
                if(selected) Color.CYAN else Color.WHITE)
//        viewBinding.

        viewBinding.itemProgress.text = "Day: $position"
    }

    override fun getLayout() =
        R.layout.running_item

    override fun initializeViewBinding(view: View) =
        RunningItemBinding.bind(view)

}
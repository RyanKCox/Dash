package com.revature.dash.presentation.controllers.mainmenu

import android.view.View
import com.revature.dash.R
import com.revature.dash.databinding.RunningItemBinding
import com.revature.dash.model.data.RunDay
import com.xwray.groupie.viewbinding.BindableItem

class RunRecyclerItem(private val runDay: RunDay)
    :BindableItem<RunningItemBinding>(){
    override fun bind(viewBinding: RunningItemBinding, position: Int) {

        viewBinding.itemCompletion.text =
            if(runDay.completed) "Completed!" else "You got this!"

        viewBinding.itemProgress.text = "Day: $position"
    }

    override fun getLayout() =
        R.layout.running_item

    override fun initializeViewBinding(view: View) =
        RunningItemBinding.bind(view)

}
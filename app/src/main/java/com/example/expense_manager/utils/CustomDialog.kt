package com.example.expense_manager.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.example.expense_manager.databinding.CustomDialogLayoutBinding

class CustomDialog(context: Context) : Dialog(context) {

    lateinit var binding: CustomDialogLayoutBinding
    private var onCancelClickListener: (() -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = CustomDialogLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.cancelButton.setOnClickListener {
            onCancelClickListener?.invoke()
            dismiss()
        }
    }

    fun setDialogSize(
        height: Int = ViewGroup.LayoutParams.MATCH_PARENT,
        width: Int = ViewGroup.LayoutParams.MATCH_PARENT
    ) {
        val params = binding.dialogMainCardLayout.layoutParams
        params.height = height
        params.width = width
        binding.dialogMainCardLayout.requestLayout()
    }

    fun setHeaderText(text: String) {
        binding.headerText.text = text
    }

    fun getHeaderText():TextView{
        return binding.headerText
    }

    fun setSubTitleText(text: String) {
        binding.subtitleText.text = text
    }

    fun getSubTitleText():TextView{
        return binding.subtitleText
    }

    fun setMainContainer(view: View) {
        binding.dialogWidgetContainer.addView(view)
    }

    fun getMainContainer(): LinearLayout {
        return binding.dialogWidgetContainer
    }

    fun setOnCancelClickListener(listener: (() -> Unit)?) {
        onCancelClickListener = listener
    }

}
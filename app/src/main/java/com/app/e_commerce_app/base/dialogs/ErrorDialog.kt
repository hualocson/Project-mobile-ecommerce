package com.app.e_commerce_app.base.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import com.app.e_commerce_app.R

class ErrorDialog (
    context: Context,
    private val errorContent: String,
    private val textButton: String? = null
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val rootView = layoutInflater.inflate(R.layout.dialog_error, null, false)

        val tvContentError = rootView.findViewById<TextView>(R.id.tvContentError)
        tvContentError.text = errorContent

        val btnOK = rootView.findViewById<Button>(R.id.btnOK)
        if (textButton.isNullOrEmpty())
            btnOK.text = "OK"
        else
            btnOK.text = textButton
        btnOK.setOnClickListener {
            dismiss()
        }
        setContentView(rootView)
    }
}
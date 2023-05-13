package com.app.e_commerce_app.base.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.app.e_commerce_app.R
import com.app.e_commerce_app.utils.Status


class NotifyDialog(
    context: Context,
    private val title: String,
    private val message: String,
    private val textButton: String? = null,
    private val status: Status
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(R.color.transparent)
        setContentView(R.layout.dialog_notify)

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = title

        val tvContent = findViewById<TextView>(R.id.tvContent)
        tvContent.text = HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)

        val btnOK = findViewById<AppCompatButton>(R.id.btnOK)
        if (textButton.isNullOrEmpty())
            btnOK.text = "OK"
        else
            btnOK.text = textButton
        btnOK.setOnClickListener {
            dismiss()
        }


        if (status == Status.ERROR) {
            btnOK.setBackgroundResource(R.drawable.background_cta_button)
            findViewById<ImageView>(R.id.imvIcon).setImageResource(R.mipmap.im_error_notify_foreground)
        } else if (status == Status.WARNING) {
            btnOK.setBackgroundResource(R.drawable.background_cwarn_button)
            btnOK.setTextColor(ContextCompat.getColor(context, R.color.text_color))
            findViewById<ImageView>(R.id.imvIcon).setImageResource(R.mipmap.im_warning_notify_foreground)
        } else {
            btnOK.setBackgroundResource(R.drawable.background_csuccess_button)
            findViewById<ImageView>(R.id.imvIcon).setImageResource(R.mipmap.im_success_notify_foreground)

        }
    }

}
package com.app.e_commerce_app.ui.admin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.e_commerce_app.common.BindableAdapter
import com.app.e_commerce_app.databinding.AdminItemUserBinding
import com.app.e_commerce_app.model.UserJson

class UserAdminAdapter(
    private val context: Context,
    private val onClick: (UserJson) -> Unit
) : RecyclerView.Adapter<UserAdminAdapter.UserAdminViewHolder>(),
    BindableAdapter<UserJson> {

    private var items: ArrayList<UserJson> = ArrayList()

    inner class UserAdminViewHolder(private val binding: AdminItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindData(userJson: UserJson) {
            binding.useritem = userJson
            binding.executePendingBindings()

            binding.Layout.setOnClickListener {
                onClick(userJson)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdminViewHolder {
        val binding = AdminItemUserBinding.inflate(LayoutInflater.from(context), parent, false)

        return UserAdminViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserAdminViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    override fun setItems(items: List<UserJson>) {
        Log.d("LOADADMIN", items.toString())
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}
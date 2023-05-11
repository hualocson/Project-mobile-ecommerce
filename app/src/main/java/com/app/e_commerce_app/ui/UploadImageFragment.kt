package com.app.e_commerce_app.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.fragment.app.viewModels
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.databinding.FragmentProfileBinding
import com.app.e_commerce_app.databinding.FragmentUploadImgBinding
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class UploadImageFragment : BaseFragment<FragmentUploadImgBinding>(true) {

    private val userViewModel: UserViewModel by viewModels()
    private val REQUEST_CODE_IMG = 101
    private var selectImgUri: Uri? = null
    private var mUri: Uri? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUploadImgBinding {
        return FragmentUploadImgBinding.inflate(inflater, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.fetchUser()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.userViewModel = userViewModel
        registerAllExceptionEvent(userViewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(userViewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(userViewModel, viewLifecycleOwner)
        binding.btnSelect.setOnClickListener {
            setupPermissions()
        }
        binding.btnUploadapi.setOnClickListener {
            uploadUserImage()
        }
    }
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
        else{
            opqenImageChooser()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_IMG)
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_IMG -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i("TAG", "Permission has been denied by user")
                } else {
                    Log.i("TAG", "Permission has been granted by user")
                }
            }
        }
    }
    private fun opqenImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val minTypes = arrayOf("image/jpeg","image/jpg","image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, minTypes)
            startActivityForResult(it, REQUEST_CODE_IMG)
        }
    }

    private fun uploadUserImage(){
        val uri = Uri.parse(mUri.toString())
        val path = uri.path
        val file: File = File(path)
        val requestBody = file.path.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("avatar", file.name, requestBody)
        userViewModel.uploadImage(multipartBody)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_CODE_IMG -> {
                    selectImgUri = data?.data
                    mUri = selectImgUri
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectImgUri)
                    binding.profileImage.setImageBitmap(bitmap)
                    Log.d("uri", selectImgUri.toString())
                }
            }
        }
    }
}
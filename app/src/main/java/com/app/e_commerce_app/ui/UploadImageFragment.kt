package com.app.e_commerce_app.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.loader.content.CursorLoader
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.databinding.FragmentUploadImgBinding
import com.app.e_commerce_app.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

@AndroidEntryPoint
class UploadImageFragment : BaseFragment<FragmentUploadImgBinding>(true) {

    private val userViewModel: UserViewModel by viewModels()
    private var oldRemember : Boolean? = null
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
        createNotificationChannel()
    }

    // Notifications
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val desc = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name, importance).apply {
                description = desc
            }
            val notificationManager: NotificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification(){
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("From Us With Love")
            .setContentText("Upload Success")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(requireContext())) {
            val permission = ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.POST_NOTIFICATIONS)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeNotifyRequest()
            }
            notify(notificationId, builder.build())
        }
    }
    private fun makeNotifyRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_CODE_IMG)
    }
    //End Notifications

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
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }
    }
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE)

        val permissionWrite = ContextCompat.checkSelfPermission(requireContext(),
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)


        if (permission != PackageManager.PERMISSION_GRANTED || permissionWrite != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
        else{
            openImageChooser()
        }

        Log.d("CHECKMAIN", "AFTERAFTER CHOOSE")
        userViewModel.uploadSuccess.observe(viewLifecycleOwner, EventObserver { isSuccess ->
            if (isSuccess) {
                navigateToPage(R.id.profileFragment)
                sendNotification()
            }
        })
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
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
    private fun openImageChooser() {
        oldRemember = userViewModel.getRemember()
        userViewModel.setRemember(true)
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val minTypes = arrayOf("image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, minTypes)
            startActivityForResult(it, REQUEST_CODE_IMG)
        }
//        userViewModel.setRemember(remember!!)
    }

    private fun uploadUserImage(){
        val uri = Uri.parse(mUri.toString())
        val path = RealPathUtil.getRealPath(requireContext(), uri)
        val file = createFileFromRealPath(path)
        val requestBody = createRequestBody(file!!)
        val multipartBodyPart = createMultipartBodyPart(file!!, "avatar")
        userViewModel.uploadImage(multipartBodyPart)
    }

    fun createFileFromRealPath(realPath: String?): File? {
        if (realPath == null) {
            return null
        }
        return try {
            File(realPath)
        } catch (e: Exception) {
            null
        }
    }

    fun createRequestBody(file: File): RequestBody {
        return RequestBody.create(file.toURI().toURL().toString().toMediaTypeOrNull(), file)
    }

    fun createMultipartBodyPart(file: File, fieldName: String): MultipartBody.Part {
        val requestBody = createRequestBody(file)
        return MultipartBody.Part.createFormData(fieldName, file.name, requestBody)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        oldRemember?.let { userViewModel.setRemember(it) }
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
    object RealPathUtil {

        fun getRealPath(context: Context, fileUri: Uri): String? {
            // SDK >= 11 && SDK < 19
            return if (Build.VERSION.SDK_INT < 19) {
                getRealPathFromURIAPI11to18(context, fileUri)
            } else {
                getRealPathFromURIAPI19(context, fileUri)
            }// SDK > 19 (Android 4.4) and up
        }

        @SuppressLint("NewApi")
        fun getRealPathFromURIAPI11to18(context: Context, contentUri: Uri): String? {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            var result: String? = null

            val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
            val cursor = cursorLoader.loadInBackground()

            if (cursor != null) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                result = cursor.getString(columnIndex)
                cursor.close()
            }
            return result
        }

        /**
         * Get a file path from a Uri. This will get the the path for Storage Access
         * Framework Documents, as well as the _data field for the MediaStore and
         * other file-based ContentProviders.
         *
         * @param context The context.
         * @param uri     The Uri to query.
         * @author Niks
         */
        @SuppressLint("NewApi")
        fun getRealPathFromURIAPI19(context: Context, uri: Uri): String? {

            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }
                } else if (isDownloadsDocument(uri)) {
                    var cursor: Cursor? = null
                    try {
                        cursor = context.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME), null, null, null)
                        cursor!!.moveToNext()
                        val fileName = cursor.getString(0)
                        val path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                        if (!TextUtils.isEmpty(path)) {
                            return path
                        }
                    } finally {
                        cursor?.close()
                    }
                    val id = DocumentsContract.getDocumentId(uri)
                    if (id.startsWith("raw:")) {
                        return id.replaceFirst("raw:".toRegex(), "")
                    }
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), java.lang.Long.valueOf(id))

                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]

                    var contentUri: Uri? = null
                    when (type) {
                        "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                        "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }

                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])

                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }// MediaProvider
                // DownloadsProvider
            } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {

                // Return the remote address
                return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
                return uri.path
            }// File
            // MediaStore (and general)

            return null
        }

        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         * @author Niks
         */
        private fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                                  selectionArgs: Array<String>?): String? {

            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)

            try {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         */
        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         */
        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         */
        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        /**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         */
        private fun isGooglePhotosUri(uri: Uri): Boolean {
            return "com.google.android.apps.photos.content" == uri.authority
        }
    }
}
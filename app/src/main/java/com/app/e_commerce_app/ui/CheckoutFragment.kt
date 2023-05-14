package com.app.e_commerce_app.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.base.dialogs.ConfirmDialog
import com.app.e_commerce_app.common.EventObserver
import com.app.e_commerce_app.databinding.FragmentFinalCheckoutBinding
import com.app.e_commerce_app.model.CartItemModel
import com.app.e_commerce_app.model.toCartItemModel
import com.app.e_commerce_app.ui.adapter.CartAdapter
import com.app.e_commerce_app.utils.Status
import com.app.e_commerce_app.viewmodel.CheckoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.muddz.styleabletoast.StyleableToast

@AndroidEntryPoint
class CheckoutFragment : BaseFragment<FragmentFinalCheckoutBinding>(true),
    ConfirmDialog.ConfirmCallback {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFinalCheckoutBinding {
        return FragmentFinalCheckoutBinding.inflate(inflater, container, false)
    }

    private lateinit var viewStub: ViewStub
    private val viewModel: CheckoutViewModel by activityViewModels()

    private val args by navArgs<CheckoutFragmentArgs>()


    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(requireContext())
    }

    private fun observerEvent() {
        registerAllExceptionEvent(viewModel, viewLifecycleOwner)
        registerObserverLoadingEvent(viewModel, viewLifecycleOwner)
        registerObserverNavigateEvent(viewModel, viewLifecycleOwner)
    }

    private fun listenClickEvent() {
        binding.headerView.btnLeft.setOnClickListener {
            navigateBack()
        }

        binding.layoutShippingAddress.btnUpdate.setOnClickListener {
            viewModel.addresses.observe(viewLifecycleOwner) { addresses ->
                val arr = addresses!!.map { address ->
                    address.toChooseItem()
                }
                val action: NavDirections =
                    CheckoutFragmentDirections.actionCheckoutFragmentToChooseFragment(
                        items = arr.toTypedArray(),
                        chooseAddress = true
                    )
                navigateAction(action)
            }
        }

        binding.btnCheckout.setOnClickListener {
            var total: Long = 0L
            var shippingMethodId: Int = 0
            var shippingAddressId: Int = 0
            var items: List<CartItemModel> = listOf()

            viewModel.sumTotal.observe(viewLifecycleOwner) {
                total = it
            }

            viewModel.shippingMethod.observe(viewLifecycleOwner) {
                shippingMethodId = it.id
            }
            viewModel.cartEntity.observe(viewLifecycleOwner) { entity ->
                items = entity.map { it.toCartItemModel() }
            }
            viewModel.addressData.observe(viewLifecycleOwner) {
                if (it != null)
                    shippingAddressId = it.id
            }
            if (shippingAddressId == 0) {
                binding.tvShippingAddress.requestFocus()
                showNotify("Notification", "Please input your address!", status = Status.WARNING)
            } else if (shippingMethodId == 0) {
                binding.tvShippingAddress.requestFocus()
                showNotify(
                    "Notification",
                    "Please choose shipping method!",
                    status = Status.WARNING
                )
            } else {
                viewModel.createOrder(
                    total, shippingMethodId, shippingAddressId, items
                )
            }
        }
    }

    private fun listenChooseShippingClick() {
        viewStub = binding.layoutChooseShipping.viewStub!!
        val button = viewStub.inflate().findViewById<ImageButton>(R.id.btnChooseShipping)
        button.setOnClickListener {
            viewModel.shippingMethods.observe(viewLifecycleOwner) { data ->
                val arr = data!!.map { shippingMethod ->
                    shippingMethod.toChooseItem()
                }
                val action: NavDirections =
                    CheckoutFragmentDirections.actionCheckoutFragmentToChooseFragment(
                        items = arr.toTypedArray(),
                        chooseAddress = false
                    )
                navigateAction(action)
            }
        }
    }

    private fun listenShippingClick() {
        viewStub = binding.layoutShipping.viewStub!!
        val button = viewStub.inflate().findViewById<ImageButton>(R.id.btnUpdate)
        button.setOnClickListener {
            viewModel.shippingMethods.observe(viewLifecycleOwner) { data ->
                val arr = data!!.map { shippingMethod ->
                    shippingMethod.toChooseItem()
                }
                val action: NavDirections =
                    CheckoutFragmentDirections.actionCheckoutFragmentToChooseFragment(
                        items = arr.toTypedArray(),
                        chooseAddress = false
                    )
                navigateAction(action)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvOrderList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvOrderList.adapter = cartAdapter

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        observerEvent()
        cartAdapter.isInCheckout = true

        createNotificationChannel()
        if (args.cartItemList != null)
            viewModel.updateProductsData(args.cartItemList!!.toList())

        if (args.totalPrice != 0L)
            viewModel.updateTotalPrice(args.totalPrice)

        if (args.addressSelected == null && args.shippingMethodSelected == null) {
            listenChooseShippingClick()
            viewModel.fetchData()
        } else if (args.addressSelected != null && args.shippingMethodSelected == null) { //selected address
            viewModel.updateAddressSelected(args.addressSelected!!)
            if (viewModel.shippingMethod.value != null)
                listenShippingClick()
            else
                listenChooseShippingClick()
        } else if (args.addressSelected == null && args.shippingMethodSelected != null) {
            viewModel.updateShippingMethod(args.shippingMethodSelected!!)
            listenShippingClick()
        }

        listenClickEvent()

        viewModel.orderSuccess.observe(viewLifecycleOwner, EventObserver { isSuccess ->
            if (isSuccess) {
                showConfirm(
                    "Order Successful!",
                    "You have successfully made order",
                    "View Order",
                    "Back Home",
                    this
                )
                viewModel.deleteAllCart()
                sendNotification()
            }
        })
    }

    override fun negativeAction() {
        navigateToPage(R.id.action_checkoutFragment_to_homeFragment)
    }

    override fun positiveAction() {
        navigateToPage(R.id.action_checkoutFragment_to_orderFragment)
    }

    // Notifications
    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101
    private val REQUEST_CODE_IMG = 101

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val desc = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID,name, importance).apply {
                description = desc
            }
            val notificationManager: NotificationManager = requireActivity().getSystemService(
                Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification(){
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("From Us With Love")
            .setContentText("Thank For Shopping")
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
    //End Notifications
}
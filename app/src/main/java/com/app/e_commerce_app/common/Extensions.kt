package com.app.e_commerce_app.common

import com.app.e_commerce_app.R

val listHideBottomNavigationView: ArrayList<Int>
get() {
    val listDestination = ArrayList<Int>()
    listDestination.add(R.id.loginFragment)
    listDestination.add(R.id.signupFragment)
    listDestination.add(R.id.fillProfileFragment)
    listDestination.add(R.id.storeFragment)
    listDestination.add(R.id.productDetailFragment)
    listDestination.add(R.id.addressFragment)
    return listDestination
}
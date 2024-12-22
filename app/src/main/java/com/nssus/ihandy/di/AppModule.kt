package com.nssus.ihandy.di

import com.nssus.ihandy.ui.home.viewmodel.HomeViewModel
import com.nssus.ihandy.ui.common.inventorytaking.viewmodel.InvTakingViewModel
import com.nssus.ihandy.ui.login.viewmodel.LoginViewModel
import com.nssus.ihandy.ui.main.viewmodel.MainViewModel
import com.nssus.ihandy.ui.common.relabel.viewmodel.ReLabelViewModel
import com.nssus.ihandy.ui.common.shipbypro.viewmodel.ShipByProViewModel
import com.nssus.ihandy.ui.common.validate.viewmodel.ValidateViewModel
import com.nssus.ihandy.ui.common.yardentry.viewmodel.YardEntryViewModel
import com.nssus.ihandy.ui.production.crfhrec.viewmodel.CRFHReceiveViewModel
import com.nssus.ihandy.ui.production.cvloading.viewmodel.CVLoadingViewModel
import com.nssus.ihandy.ui.production.delcheck.viewmodel.DeliveryCheckViewModel
import com.nssus.ihandy.ui.production.rcltolling.viewmodel.RCLTollingViewModel
import com.nssus.ihandy.ui.shipping.chargerelo.viewmodel.ChargeReloViewModel
import com.nssus.ihandy.ui.shipping.pcshipping.viewmodel.PcShippingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModel

    //Common
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) } // get()
    viewModel { HomeViewModel() }
    viewModel { YardEntryViewModel(get()) }
    viewModel { InvTakingViewModel() }
    viewModel { ValidateViewModel() }
    viewModel { ReLabelViewModel() }
    viewModel { ShipByProViewModel(get()) }
    //Production
    viewModel { CVLoadingViewModel() }
    viewModel { RCLTollingViewModel() }
    viewModel { DeliveryCheckViewModel() }
    viewModel { CRFHReceiveViewModel() }

    //Shipping
    viewModel { ChargeReloViewModel() }
    viewModel { PcShippingViewModel(get()) }
}
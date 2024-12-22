package com.nssus.ihandy.model.shipping.pcshipping

import android.os.CountDownTimer
import androidx.annotation.StringRes
import com.nssus.ihandy.R
import com.nssus.ihandy.model.network.NetworkResult

data class PcShippingUIStateModel(
    var isLoading: Boolean = false,
    var isSuccess: Boolean = false,
    var isError: Boolean = false,
    var navigateType: PcShippingNavigateType = PcShippingNavigateType.NONE,
    var message: String? = null,
    var errorBody: PcShippingErrorModel? = null,
    //Flag
    var isClearAllTextField: Boolean = false,
    var isGetShipmentLotSuccess: Boolean = false,
    var isGetTruckSuccess: Boolean = false,
    var isGetTruckFail: Boolean = false,
    var isGetCoilResp: Boolean = false,
    var isGetCoilRespWithNeedWidth: Boolean = false,
    var isEnabledWidth : Boolean = false,
    var isGetWidthSuccess: Boolean = false,
    var isGetWidthFail: Boolean = false,
    var isClickedShipmentLot :Boolean = false,
    var isClickedTruck :Boolean = false,
    var isClickedCoilNo :Boolean = false,
    //Value
    var shipmentLot :String = "",
    var truck : String = "",
    var coilNumber : String = "",
    var width : Double = 0.0,
    var dock : String = "",
    var resListCoil : List<CoilDetail> = emptyList(),
    //FlagCountDown
    var countDownTime: Long = 0L,
    var isNextShipmentLot : Boolean = false,
    var isNextTruck : Boolean = false,
    var isNextCoil : Boolean = false,
    var isNextWidth : Boolean = true

)

enum class PcShippingNavigateType {
    GO_BACK,
    DISPLAY_CLEAR_ALL_DIALOG_WARNING,
    DISPLAY_NEXT_ACTION_DIALOG_WARNING,
    START_COUNTDOWN_TIMER,
    STOP_COUNTDOWN_TIMER,
    NONE

}

data class PcShippingErrorModel(
    val pcShippingErrorType: PcShippingErrorType = PcShippingErrorType.ERROR_FROM_API,
    var errorMsg: String? = null
)

enum class PcShippingErrorType(@StringRes val errorMsgId :Int = R.string.empty_string){
    ERROR_FROM_API,

    ERROR_FROM_API_SHIPMENT_LOT,
    EMPTY_SHIPMENT_LOT(R.string.common_empty_fill_shipment_lot_warning_msg),
    DIGIT_SHIPMENT_LOT(R.string.pc_shipping_digit_shipment_lot_warning_msg),
    NEXT_ACTION_SHIPMENT(R.string.pc_shipping_shipment_click_warning_msg),

    EMPTY_TRUCK(R.string.shipping_empty_truck_warning_msg),
    TRUCK_NOT_MATCHED(R.string.shipping_truck_not_match_warning_msg),

    EMPTY_COIL_NUMBER(R.string.common_empty_fill_coil_number_warning_msg),
    DIGIT_COIL(R.string.pc_shipping_digit_coil_number_warning_msg),
    DUPLICATE_COIL_NUMBER(R.string.common_duplicate_coil_number_warning_msg),
    ERROR_FROM_API_COIL_NO,

    EMPTY_WIDTH(R.string.pc_shipping_empty_width_warning_msg),
    ERROR_RANGE_WIDTH,
    ERROR_STRING_TYPE(R.string.common_error_type_warning_msg),

    ERROR_FROM_API_SEND_MQ
}
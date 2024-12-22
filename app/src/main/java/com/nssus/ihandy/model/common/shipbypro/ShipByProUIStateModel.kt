package com.nssus.ihandy.model.common.shipbypro

import androidx.annotation.StringRes
import com.nssus.ihandy.R


data class ShipByProUIStateModel(
    var isLoading: Boolean =false,
    var isSuccess: Boolean = false,
    var isError: Boolean = false,
    //Flag
    var isClearAllTextFieldValue: Boolean = false,
    var isGetShipmentLotRespSuccess: Boolean = false,
    var isGetCoilNumberResp: Boolean = false,
    var isAlreadyInitialFeature : Boolean = false,

    var message: String? = null,
    var navigateType: ShipByProNavigateType = ShipByProNavigateType.NONE,
    var errorBody: ShipByProErrorModel? = null,
    //Variable
    var shipmentLot : String ="",
    var coilNumber : String = "",
    var sumCoilWt : Double = 0.0,
    var lastCoil : String = "",
    var lastCoilWeight: Double =0.0,
    var shipSt : String ="",
    var resCoilList : ArrayList<GetCheckCoilResponse> = arrayListOf(),
    var countCoilList : Int = 0,
    )

enum class ShipByProNavigateType{

    GO_BACK,
    GO_TO_COIL_DETAIL_LS,
    DISPLAY_DIALOG_SEND_MQ,
    DISPLAY_DIALOG_WARNING,
    DISPLAY_DIALOG_SUCCESS,
    NONE,
    // Coil Detail List Screen
    BACK_TO_SHIP_BY_PRO_MAIN,
    DISPLAY_SHOW_REMOVE_COIL_DIALOG

}

enum class  ShipByProErrorType(@StringRes val errorMsgId : Int = R.string.empty_string){
    ERROR_FROM_API,
    EMPTY_SHIPMENT_LOT(R.string.common_empty_fill_shipment_lot_warning_msg),
    DIGIT_SHIPMENT_LOT(R.string.ship_by_product_digit_shipment_lot_warning_msg),
    EMPTY_COIL_NUMBER(R.string.common_empty_fill_coil_number_warning_msg),
    EMPTY_COIL_LIST(R.string.ship_by_pro_list_coil_empty),
    ERROR_FROM_API_SHIPMENT_LOT,
    ERROR_FROM_API_COIL_NO,
    DUPLICATE_COIL_NUMBER(R.string.common_duplicate_coil_number_warning_msg),
    ERROR_FROM_API_SEND_MQ
}

data class ShipByProErrorModel(
    val shipByProErrorType: ShipByProErrorType = ShipByProErrorType.ERROR_FROM_API,
    val errorMsg : String? = null
)
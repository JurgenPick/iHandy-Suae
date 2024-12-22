package com.nssus.ihandy.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    val space_textfield_to_textfield: Dp,
    val space_prefix_text_to_textfield: Dp,
    val space_textfield_to_icon: Dp,
    val space_top_content_card_to_header: Dp,
    val space_between_button_to_button: Dp,
    val space_bottom_content_card_to_button: Dp,
    val padding_bg_main: Dp,
    val padding_vertical_main_to_content_card: Dp,
    val padding_inner_content_to_top_title_gray_value: Dp,
    val padding_inner_content_to_top_title_gray_value_2: Dp,
    val padding_below_bottom_title_gray_value: Dp,
    val padding_vertical_gray_bg_value: Dp,
    val padding_bg_with_back_btn: Dp,
    val padding_card_to_content: Dp,
    val padding_small: Dp,
    val padding_normal: Dp,
    val size_card_border_small: Dp,
    val size_menu_card_corner: Dp,
    val size_card_corner_large: Dp,
    val size_button_corner_small: Dp,
    val size_button_corner_medium: Dp,
    val size_textfield_corner_small: Dp,
    val size_dropdown_dialog_corner: Dp,
    val min_height_text_field: Dp,
    val size_elevation_main_content_card: Dp,
    val border_text_field: Dp,
    val border_button: Dp
)

val defaultDimensions = Dimensions(
    space_textfield_to_textfield = 12.dp,
    space_prefix_text_to_textfield = 12.dp,
    space_textfield_to_icon = 12.dp,
    space_top_content_card_to_header = 10.dp,
    space_between_button_to_button = 14.dp,
    space_bottom_content_card_to_button = 12.dp,
    padding_bg_main = 20.dp,
    padding_vertical_main_to_content_card = 12.dp,
    padding_inner_content_to_top_title_gray_value = 60.dp,
    padding_inner_content_to_top_title_gray_value_2 = 25.dp,
    padding_below_bottom_title_gray_value = 20.dp,
    padding_vertical_gray_bg_value = 1.5.dp,
    padding_bg_with_back_btn = 24.dp,
    padding_card_to_content = 14.dp,
    padding_small = 6.dp,
    padding_normal = 10.dp,
    size_card_border_small = .5f.dp,
    size_menu_card_corner = 15.dp,
    size_card_corner_large = 34.dp,
    size_button_corner_small = 15.dp,
    size_button_corner_medium = 20.dp,
    size_textfield_corner_small = 5.dp,
    size_dropdown_dialog_corner = 10.dp,
    min_height_text_field = 40.dp,
    size_elevation_main_content_card = 10.dp,
    border_text_field = .1f.dp,
    border_button = 1.dp
)

val sw480Dimensions = defaultDimensions
package com.bernaferrari.diagonalwipeicon.demo

import com.bernaferrari.diagonalwipeicon.DrawableWipeIcon
import diagonalwipeicon.composeapp.generated.resources.Res
import diagonalwipeicon.composeapp.generated.resources.account_circle_24px
import diagonalwipeicon.composeapp.generated.resources.account_circle_off_24px
import diagonalwipeicon.composeapp.generated.resources.ad_24px
import diagonalwipeicon.composeapp.generated.resources.ad_group_24px
import diagonalwipeicon.composeapp.generated.resources.ad_group_off_24px
import diagonalwipeicon.composeapp.generated.resources.ad_off_24px
import diagonalwipeicon.composeapp.generated.resources.adaptive_audio_mic_24px
import diagonalwipeicon.composeapp.generated.resources.adaptive_audio_mic_off_24px
import diagonalwipeicon.composeapp.generated.resources.alarm_24px
import diagonalwipeicon.composeapp.generated.resources.alarm_off_24px
import diagonalwipeicon.composeapp.generated.resources.android_cell_4_bar_24px
import diagonalwipeicon.composeapp.generated.resources.android_cell_4_bar_off_24px
import diagonalwipeicon.composeapp.generated.resources.android_cell_5_bar_24px
import diagonalwipeicon.composeapp.generated.resources.android_cell_5_bar_off_24px
import diagonalwipeicon.composeapp.generated.resources.android_wifi_3_bar_24px
import diagonalwipeicon.composeapp.generated.resources.android_wifi_3_bar_off_24px
import diagonalwipeicon.composeapp.generated.resources.android_wifi_4_bar_24px
import diagonalwipeicon.composeapp.generated.resources.android_wifi_4_bar_off_24px
import diagonalwipeicon.composeapp.generated.resources.approval_delegation_24px
import diagonalwipeicon.composeapp.generated.resources.approval_delegation_off_24px
import diagonalwipeicon.composeapp.generated.resources.attach_file_24px
import diagonalwipeicon.composeapp.generated.resources.attach_file_off_24px
import diagonalwipeicon.composeapp.generated.resources.auto_stories_24px
import diagonalwipeicon.composeapp.generated.resources.auto_stories_off_24px
import diagonalwipeicon.composeapp.generated.resources.backlight_high_24px
import diagonalwipeicon.composeapp.generated.resources.backlight_high_off_24px
import diagonalwipeicon.composeapp.generated.resources.bar_chart_24px
import diagonalwipeicon.composeapp.generated.resources.bar_chart_off_24px
import diagonalwipeicon.composeapp.generated.resources.bedtime_24px
import diagonalwipeicon.composeapp.generated.resources.bedtime_off_24px
import diagonalwipeicon.composeapp.generated.resources.bid_landscape_24px
import diagonalwipeicon.composeapp.generated.resources.bid_landscape_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.bluetooth_24px
import diagonalwipeicon.composeapp.generated.resources.bluetooth_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.blur_off_24px
import diagonalwipeicon.composeapp.generated.resources.blur_on_24px
import diagonalwipeicon.composeapp.generated.resources.chat_bubble_24px
import diagonalwipeicon.composeapp.generated.resources.chat_bubble_off_24px
import diagonalwipeicon.composeapp.generated.resources.check_24px
import diagonalwipeicon.composeapp.generated.resources.closed_caption_24px
import diagonalwipeicon.composeapp.generated.resources.closed_caption_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.cloud_24px
import diagonalwipeicon.composeapp.generated.resources.cloud_off_24px
import diagonalwipeicon.composeapp.generated.resources.code_24px
import diagonalwipeicon.composeapp.generated.resources.code_off_24px
import diagonalwipeicon.composeapp.generated.resources.comment_24px
import diagonalwipeicon.composeapp.generated.resources.comments_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.contactless_24px
import diagonalwipeicon.composeapp.generated.resources.contactless_off_24px
import diagonalwipeicon.composeapp.generated.resources.content_copy_24px
import diagonalwipeicon.composeapp.generated.resources.content_paste_24px
import diagonalwipeicon.composeapp.generated.resources.content_paste_off_24px
import diagonalwipeicon.composeapp.generated.resources.contrast_24px
import diagonalwipeicon.composeapp.generated.resources.contrast_rtl_off_24px
import diagonalwipeicon.composeapp.generated.resources.conversion_path_24px
import diagonalwipeicon.composeapp.generated.resources.conversion_path_off_24px
import diagonalwipeicon.composeapp.generated.resources.cookie_24px
import diagonalwipeicon.composeapp.generated.resources.cookie_off_24px
import diagonalwipeicon.composeapp.generated.resources.credit_card_24px
import diagonalwipeicon.composeapp.generated.resources.credit_card_off_24px
import diagonalwipeicon.composeapp.generated.resources.dark_mode_24px
import diagonalwipeicon.composeapp.generated.resources.database_24px
import diagonalwipeicon.composeapp.generated.resources.database_off_24px
import diagonalwipeicon.composeapp.generated.resources.desktop_access_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.desktop_windows_24px
import diagonalwipeicon.composeapp.generated.resources.detection_and_zone_24px
import diagonalwipeicon.composeapp.generated.resources.detection_and_zone_off_24px
import diagonalwipeicon.composeapp.generated.resources.developer_board_24px
import diagonalwipeicon.composeapp.generated.resources.developer_board_off_24px
import diagonalwipeicon.composeapp.generated.resources.devices_24px
import diagonalwipeicon.composeapp.generated.resources.devices_off_24px
import diagonalwipeicon.composeapp.generated.resources.directions_24px
import diagonalwipeicon.composeapp.generated.resources.directions_alt_24px
import diagonalwipeicon.composeapp.generated.resources.directions_alt_off_24px
import diagonalwipeicon.composeapp.generated.resources.directions_bus_24px
import diagonalwipeicon.composeapp.generated.resources.directions_off_24px
import diagonalwipeicon.composeapp.generated.resources.do_not_disturb_off_24px
import diagonalwipeicon.composeapp.generated.resources.do_not_disturb_on_24px
import diagonalwipeicon.composeapp.generated.resources.domain_24px
import diagonalwipeicon.composeapp.generated.resources.domain_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.domain_verification_24px
import diagonalwipeicon.composeapp.generated.resources.domain_verification_off_24px
import diagonalwipeicon.composeapp.generated.resources.edit_24px
import diagonalwipeicon.composeapp.generated.resources.edit_off_24px
import diagonalwipeicon.composeapp.generated.resources.emergency_share_24px
import diagonalwipeicon.composeapp.generated.resources.emergency_share_off_24px
import diagonalwipeicon.composeapp.generated.resources.encrypted_24px
import diagonalwipeicon.composeapp.generated.resources.encrypted_off_24px
import diagonalwipeicon.composeapp.generated.resources.enterprise_24px
import diagonalwipeicon.composeapp.generated.resources.enterprise_off_24px
import diagonalwipeicon.composeapp.generated.resources.explore_24px
import diagonalwipeicon.composeapp.generated.resources.explore_off_24px
import diagonalwipeicon.composeapp.generated.resources.extension_24px
import diagonalwipeicon.composeapp.generated.resources.extension_off_24px
import diagonalwipeicon.composeapp.generated.resources.face_retouching_off_24px
import diagonalwipeicon.composeapp.generated.resources.fastfood_24px
import diagonalwipeicon.composeapp.generated.resources.file_copy_24px
import diagonalwipeicon.composeapp.generated.resources.file_copy_off_24px
import diagonalwipeicon.composeapp.generated.resources.file_download_24px
import diagonalwipeicon.composeapp.generated.resources.file_download_off_24px
import diagonalwipeicon.composeapp.generated.resources.file_save_24px
import diagonalwipeicon.composeapp.generated.resources.file_save_off_24px
import diagonalwipeicon.composeapp.generated.resources.file_upload_24px
import diagonalwipeicon.composeapp.generated.resources.file_upload_off_24px
import diagonalwipeicon.composeapp.generated.resources.filter_alt_24px
import diagonalwipeicon.composeapp.generated.resources.filter_alt_off_24px
import diagonalwipeicon.composeapp.generated.resources.filter_list_24px
import diagonalwipeicon.composeapp.generated.resources.filter_list_off_24px
import diagonalwipeicon.composeapp.generated.resources.fingerprint_24px
import diagonalwipeicon.composeapp.generated.resources.fingerprint_off_24px
import diagonalwipeicon.composeapp.generated.resources.flash_off_24px
import diagonalwipeicon.composeapp.generated.resources.flash_on_24px
import diagonalwipeicon.composeapp.generated.resources.flashlight_off_24px
import diagonalwipeicon.composeapp.generated.resources.flashlight_on_24px
import diagonalwipeicon.composeapp.generated.resources.folder_24px
import diagonalwipeicon.composeapp.generated.resources.folder_off_24px
import diagonalwipeicon.composeapp.generated.resources.font_download_24px
import diagonalwipeicon.composeapp.generated.resources.font_download_off_24px
import diagonalwipeicon.composeapp.generated.resources.format_paint_24px
import diagonalwipeicon.composeapp.generated.resources.format_paint_off_24px
import diagonalwipeicon.composeapp.generated.resources.format_quote_24px
import diagonalwipeicon.composeapp.generated.resources.format_quote_off_24px
import diagonalwipeicon.composeapp.generated.resources.frame_person_24px
import diagonalwipeicon.composeapp.generated.resources.frame_person_off_24px
import diagonalwipeicon.composeapp.generated.resources.graphic_eq_24px
import diagonalwipeicon.composeapp.generated.resources.graphic_eq_off_24px
import diagonalwipeicon.composeapp.generated.resources.grid_3x3_24px
import diagonalwipeicon.composeapp.generated.resources.grid_3x3_off_24px
import diagonalwipeicon.composeapp.generated.resources.grid_off_24px
import diagonalwipeicon.composeapp.generated.resources.grid_on_24px
import diagonalwipeicon.composeapp.generated.resources.group_24px
import diagonalwipeicon.composeapp.generated.resources.group_off_24px
import diagonalwipeicon.composeapp.generated.resources.hangout_video_24px
import diagonalwipeicon.composeapp.generated.resources.hangout_video_off_24px
import diagonalwipeicon.composeapp.generated.resources.hdr_off_24px
import diagonalwipeicon.composeapp.generated.resources.hdr_off_select_24px
import diagonalwipeicon.composeapp.generated.resources.hdr_on_24px
import diagonalwipeicon.composeapp.generated.resources.hdr_plus_24px
import diagonalwipeicon.composeapp.generated.resources.hdr_plus_off_24px
import diagonalwipeicon.composeapp.generated.resources.headset_mic_24px
import diagonalwipeicon.composeapp.generated.resources.headset_off_24px
import diagonalwipeicon.composeapp.generated.resources.hearing_24px
import diagonalwipeicon.composeapp.generated.resources.hearing_aid_24px
import diagonalwipeicon.composeapp.generated.resources.hearing_aid_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.hearing_aid_disabled_left_24px
import diagonalwipeicon.composeapp.generated.resources.hearing_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.hide_source_24px
import diagonalwipeicon.composeapp.generated.resources.hls_24px
import diagonalwipeicon.composeapp.generated.resources.hls_off_24px
import diagonalwipeicon.composeapp.generated.resources.hourglass_24px
import diagonalwipeicon.composeapp.generated.resources.hourglass_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.invert_colors_24px
import diagonalwipeicon.composeapp.generated.resources.invert_colors_off_24px
import diagonalwipeicon.composeapp.generated.resources.key_24px
import diagonalwipeicon.composeapp.generated.resources.key_off_24px
import diagonalwipeicon.composeapp.generated.resources.keyboard_arrow_down_24px
import diagonalwipeicon.composeapp.generated.resources.keyboard_arrow_left_24px
import diagonalwipeicon.composeapp.generated.resources.keyboard_arrow_right_24px
import diagonalwipeicon.composeapp.generated.resources.keyboard_arrow_up_24px
import diagonalwipeicon.composeapp.generated.resources.label_24px
import diagonalwipeicon.composeapp.generated.resources.label_off_24px
import diagonalwipeicon.composeapp.generated.resources.light_mode_24px
import diagonalwipeicon.composeapp.generated.resources.light_off_24px
import diagonalwipeicon.composeapp.generated.resources.lightbulb_24px
import diagonalwipeicon.composeapp.generated.resources.link_24px
import diagonalwipeicon.composeapp.generated.resources.link_off_24px
import diagonalwipeicon.composeapp.generated.resources.location_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.location_off_24px
import diagonalwipeicon.composeapp.generated.resources.location_on_24px
import diagonalwipeicon.composeapp.generated.resources.location_searching_24px
import diagonalwipeicon.composeapp.generated.resources.lock_24px
import diagonalwipeicon.composeapp.generated.resources.luggage_24px
import diagonalwipeicon.composeapp.generated.resources.macro_off_24px
import diagonalwipeicon.composeapp.generated.resources.mail_24px
import diagonalwipeicon.composeapp.generated.resources.mail_off_24px
import diagonalwipeicon.composeapp.generated.resources.match_case_24px
import diagonalwipeicon.composeapp.generated.resources.match_case_off_24px
import diagonalwipeicon.composeapp.generated.resources.media_bluetooth_off_24px
import diagonalwipeicon.composeapp.generated.resources.media_bluetooth_on_24px
import diagonalwipeicon.composeapp.generated.resources.media_output_24px
import diagonalwipeicon.composeapp.generated.resources.media_output_off_24px
import diagonalwipeicon.composeapp.generated.resources.meeting_room_24px
import diagonalwipeicon.composeapp.generated.resources.mic_24px
import diagonalwipeicon.composeapp.generated.resources.mic_external_off_24px
import diagonalwipeicon.composeapp.generated.resources.mic_external_on_24px
import diagonalwipeicon.composeapp.generated.resources.mic_off_24px
import diagonalwipeicon.composeapp.generated.resources.mobile_24px
import diagonalwipeicon.composeapp.generated.resources.mobile_hand_24px
import diagonalwipeicon.composeapp.generated.resources.mobile_hand_left_24px
import diagonalwipeicon.composeapp.generated.resources.mobile_hand_left_off_24px
import diagonalwipeicon.composeapp.generated.resources.mobile_hand_off_24px
import diagonalwipeicon.composeapp.generated.resources.mobile_off_24px
import diagonalwipeicon.composeapp.generated.resources.mobile_sound_24px
import diagonalwipeicon.composeapp.generated.resources.mobile_sound_off_24px
import diagonalwipeicon.composeapp.generated.resources.mobiledata_arrows_24px
import diagonalwipeicon.composeapp.generated.resources.mobiledata_off_24px
import diagonalwipeicon.composeapp.generated.resources.mode_cool_24px
import diagonalwipeicon.composeapp.generated.resources.mode_cool_off_24px
import diagonalwipeicon.composeapp.generated.resources.mode_fan_24px
import diagonalwipeicon.composeapp.generated.resources.mode_fan_off_24px
import diagonalwipeicon.composeapp.generated.resources.mode_heat_24px
import diagonalwipeicon.composeapp.generated.resources.mode_heat_off_24px
import diagonalwipeicon.composeapp.generated.resources.mouse_lock_24px
import diagonalwipeicon.composeapp.generated.resources.mouse_lock_off_24px
import diagonalwipeicon.composeapp.generated.resources.movie_24px
import diagonalwipeicon.composeapp.generated.resources.movie_off_24px
import diagonalwipeicon.composeapp.generated.resources.music_note_24px
import diagonalwipeicon.composeapp.generated.resources.music_off_24px
import diagonalwipeicon.composeapp.generated.resources.near_me_24px
import diagonalwipeicon.composeapp.generated.resources.near_me_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.nearby_24px
import diagonalwipeicon.composeapp.generated.resources.nearby_off_24px
import diagonalwipeicon.composeapp.generated.resources.nfc_24px
import diagonalwipeicon.composeapp.generated.resources.nfc_off_24px
import diagonalwipeicon.composeapp.generated.resources.night_sight_auto_24px
import diagonalwipeicon.composeapp.generated.resources.night_sight_auto_off_24px
import diagonalwipeicon.composeapp.generated.resources.no_accounts_24px
import diagonalwipeicon.composeapp.generated.resources.no_encryption_24px
import diagonalwipeicon.composeapp.generated.resources.no_food_24px
import diagonalwipeicon.composeapp.generated.resources.no_luggage_24px
import diagonalwipeicon.composeapp.generated.resources.no_meals_24px
import diagonalwipeicon.composeapp.generated.resources.no_meeting_room_24px
import diagonalwipeicon.composeapp.generated.resources.no_photography_24px
import diagonalwipeicon.composeapp.generated.resources.no_stroller_24px
import diagonalwipeicon.composeapp.generated.resources.no_transfer_24px
import diagonalwipeicon.composeapp.generated.resources.noise_control_off_24px
import diagonalwipeicon.composeapp.generated.resources.noise_control_on_24px
import diagonalwipeicon.composeapp.generated.resources.north_east_24px
import diagonalwipeicon.composeapp.generated.resources.north_west_24px
import diagonalwipeicon.composeapp.generated.resources.notification_audio_24px
import diagonalwipeicon.composeapp.generated.resources.notification_audio_off_24px
import diagonalwipeicon.composeapp.generated.resources.notifications_24px
import diagonalwipeicon.composeapp.generated.resources.notifications_off_24px
import diagonalwipeicon.composeapp.generated.resources.offline_pin_24px
import diagonalwipeicon.composeapp.generated.resources.offline_pin_off_24px
import diagonalwipeicon.composeapp.generated.resources.open_in_new_24px
import diagonalwipeicon.composeapp.generated.resources.open_in_new_off_24px
import diagonalwipeicon.composeapp.generated.resources.password_2_24px
import diagonalwipeicon.composeapp.generated.resources.password_2_off_24px
import diagonalwipeicon.composeapp.generated.resources.pause_24px
import diagonalwipeicon.composeapp.generated.resources.person_24px
import diagonalwipeicon.composeapp.generated.resources.person_add_24px
import diagonalwipeicon.composeapp.generated.resources.person_add_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.person_off_24px
import diagonalwipeicon.composeapp.generated.resources.personal_bag_24px
import diagonalwipeicon.composeapp.generated.resources.personal_bag_off_24px
import diagonalwipeicon.composeapp.generated.resources.phone_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.phone_enabled_24px
import diagonalwipeicon.composeapp.generated.resources.photo_camera_24px
import diagonalwipeicon.composeapp.generated.resources.piano_24px
import diagonalwipeicon.composeapp.generated.resources.piano_off_24px
import diagonalwipeicon.composeapp.generated.resources.picture_in_picture_24px
import diagonalwipeicon.composeapp.generated.resources.picture_in_picture_off_24px
import diagonalwipeicon.composeapp.generated.resources.pill_24px
import diagonalwipeicon.composeapp.generated.resources.pill_off_24px
import diagonalwipeicon.composeapp.generated.resources.play_arrow_24px
import diagonalwipeicon.composeapp.generated.resources.play_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.portable_wifi_off_24px
import diagonalwipeicon.composeapp.generated.resources.power_24px
import diagonalwipeicon.composeapp.generated.resources.power_off_24px
import diagonalwipeicon.composeapp.generated.resources.preview_24px
import diagonalwipeicon.composeapp.generated.resources.preview_off_24px
import diagonalwipeicon.composeapp.generated.resources.print_24px
import diagonalwipeicon.composeapp.generated.resources.print_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.public_24px
import diagonalwipeicon.composeapp.generated.resources.public_off_24px
import diagonalwipeicon.composeapp.generated.resources.raw_off_24px
import diagonalwipeicon.composeapp.generated.resources.raw_on_24px
import diagonalwipeicon.composeapp.generated.resources.receipt_long_24px
import diagonalwipeicon.composeapp.generated.resources.receipt_long_off_24px
import diagonalwipeicon.composeapp.generated.resources.report_24px
import diagonalwipeicon.composeapp.generated.resources.report_off_24px
import diagonalwipeicon.composeapp.generated.resources.restaurant_24px
import diagonalwipeicon.composeapp.generated.resources.router_24px
import diagonalwipeicon.composeapp.generated.resources.router_off_24px
import diagonalwipeicon.composeapp.generated.resources.safety_check_24px
import diagonalwipeicon.composeapp.generated.resources.safety_check_off_24px
import diagonalwipeicon.composeapp.generated.resources.science_24px
import diagonalwipeicon.composeapp.generated.resources.science_off_24px
import diagonalwipeicon.composeapp.generated.resources.select_window_24px
import diagonalwipeicon.composeapp.generated.resources.select_window_off_24px
import diagonalwipeicon.composeapp.generated.resources.sensors_24px
import diagonalwipeicon.composeapp.generated.resources.sensors_krx_24px
import diagonalwipeicon.composeapp.generated.resources.sensors_krx_off_24px
import diagonalwipeicon.composeapp.generated.resources.sensors_off_24px
import diagonalwipeicon.composeapp.generated.resources.shift_lock_24px
import diagonalwipeicon.composeapp.generated.resources.shift_lock_off_24px
import diagonalwipeicon.composeapp.generated.resources.shopping_cart_24px
import diagonalwipeicon.composeapp.generated.resources.shopping_cart_off_24px
import diagonalwipeicon.composeapp.generated.resources.signal_cellular_off_24px
import diagonalwipeicon.composeapp.generated.resources.signal_wifi_off_24px
import diagonalwipeicon.composeapp.generated.resources.smart_card_reader_24px
import diagonalwipeicon.composeapp.generated.resources.smart_card_reader_off_24px
import diagonalwipeicon.composeapp.generated.resources.south_east_24px
import diagonalwipeicon.composeapp.generated.resources.south_west_24px
import diagonalwipeicon.composeapp.generated.resources.speaker_notes_24px
import diagonalwipeicon.composeapp.generated.resources.speaker_notes_off_24px
import diagonalwipeicon.composeapp.generated.resources.speed_24px
import diagonalwipeicon.composeapp.generated.resources.stack_24px
import diagonalwipeicon.composeapp.generated.resources.stack_off_24px
import diagonalwipeicon.composeapp.generated.resources.stop_24px
import diagonalwipeicon.composeapp.generated.resources.stroller_24px
import diagonalwipeicon.composeapp.generated.resources.subtitles_24px
import diagonalwipeicon.composeapp.generated.resources.subtitles_off_24px
import diagonalwipeicon.composeapp.generated.resources.supervised_user_circle_24px
import diagonalwipeicon.composeapp.generated.resources.supervised_user_circle_off_24px
import diagonalwipeicon.composeapp.generated.resources.sync_24px
import diagonalwipeicon.composeapp.generated.resources.sync_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.sync_saved_locally_24px
import diagonalwipeicon.composeapp.generated.resources.sync_saved_locally_off_24px
import diagonalwipeicon.composeapp.generated.resources.tamper_detection_off_24px
import diagonalwipeicon.composeapp.generated.resources.tamper_detection_on_24px
import diagonalwipeicon.composeapp.generated.resources.text_ad_24px
import diagonalwipeicon.composeapp.generated.resources.text_ad_off_24px
import diagonalwipeicon.composeapp.generated.resources.timer_24px
import diagonalwipeicon.composeapp.generated.resources.timer_off_24px
import diagonalwipeicon.composeapp.generated.resources.touchpad_mouse_24px
import diagonalwipeicon.composeapp.generated.resources.touchpad_mouse_off_24px
import diagonalwipeicon.composeapp.generated.resources.tv_24px
import diagonalwipeicon.composeapp.generated.resources.tv_off_24px
import diagonalwipeicon.composeapp.generated.resources.unlicense_24px
import diagonalwipeicon.composeapp.generated.resources.unpublished_24px
import diagonalwipeicon.composeapp.generated.resources.update_24px
import diagonalwipeicon.composeapp.generated.resources.update_disabled_24px
import diagonalwipeicon.composeapp.generated.resources.usb_24px
import diagonalwipeicon.composeapp.generated.resources.usb_off_24px
import diagonalwipeicon.composeapp.generated.resources.verified_24px
import diagonalwipeicon.composeapp.generated.resources.verified_off_24px
import diagonalwipeicon.composeapp.generated.resources.video_camera_front_24px
import diagonalwipeicon.composeapp.generated.resources.video_camera_front_off_24px
import diagonalwipeicon.composeapp.generated.resources.videocam_24px
import diagonalwipeicon.composeapp.generated.resources.videocam_off_24px
import diagonalwipeicon.composeapp.generated.resources.videogame_asset_24px
import diagonalwipeicon.composeapp.generated.resources.videogame_asset_off_24px
import diagonalwipeicon.composeapp.generated.resources.view_in_ar_24px
import diagonalwipeicon.composeapp.generated.resources.view_in_ar_off_24px
import diagonalwipeicon.composeapp.generated.resources.visibility_24px
import diagonalwipeicon.composeapp.generated.resources.visibility_off_24px
import diagonalwipeicon.composeapp.generated.resources.voice_chat_24px
import diagonalwipeicon.composeapp.generated.resources.voice_chat_off_24px
import diagonalwipeicon.composeapp.generated.resources.voice_over_off_24px
import diagonalwipeicon.composeapp.generated.resources.voice_selection_24px
import diagonalwipeicon.composeapp.generated.resources.voice_selection_off_24px
import diagonalwipeicon.composeapp.generated.resources.volume_off_24px
import diagonalwipeicon.composeapp.generated.resources.volume_up_24px
import diagonalwipeicon.composeapp.generated.resources.vpn_key_24px
import diagonalwipeicon.composeapp.generated.resources.vpn_key_off_24px
import diagonalwipeicon.composeapp.generated.resources.vr180_create2d_24px
import diagonalwipeicon.composeapp.generated.resources.vr180_create2d_off_24px
import diagonalwipeicon.composeapp.generated.resources.warning_24px
import diagonalwipeicon.composeapp.generated.resources.warning_off_24px
import diagonalwipeicon.composeapp.generated.resources.watch_24px
import diagonalwipeicon.composeapp.generated.resources.watch_off_24px
import diagonalwipeicon.composeapp.generated.resources.web_asset_24px
import diagonalwipeicon.composeapp.generated.resources.web_asset_off_24px
import diagonalwipeicon.composeapp.generated.resources.wifi_24px
import diagonalwipeicon.composeapp.generated.resources.wifi_off_24px
import diagonalwipeicon.composeapp.generated.resources.wifi_tethering_24px
import diagonalwipeicon.composeapp.generated.resources.wifi_tethering_off_24px

/**
 * Material Symbols outlined drawables used for icon morphing and UI controls.
 */
object MaterialSymbolIcons {
    val BidLandscapeDisabled = DrawableWipeIcon(Res.drawable.bid_landscape_disabled_24px)
    val BidLandscape = DrawableWipeIcon(Res.drawable.bid_landscape_24px)
    val BluetoothDisabled = DrawableWipeIcon(Res.drawable.bluetooth_disabled_24px)
    val Bluetooth = DrawableWipeIcon(Res.drawable.bluetooth_24px)
    val ClosedCaptionDisabled = DrawableWipeIcon(Res.drawable.closed_caption_disabled_24px)
    val ClosedCaption = DrawableWipeIcon(Res.drawable.closed_caption_24px)
    val CommentsDisabled = DrawableWipeIcon(Res.drawable.comments_disabled_24px)
    val Comment = DrawableWipeIcon(Res.drawable.comment_24px)
    val DesktopAccessDisabled = DrawableWipeIcon(Res.drawable.desktop_access_disabled_24px)
    val DesktopWindows = DrawableWipeIcon(Res.drawable.desktop_windows_24px)
    val DomainDisabled = DrawableWipeIcon(Res.drawable.domain_disabled_24px)
    val Domain = DrawableWipeIcon(Res.drawable.domain_24px)
    val HearingAidDisabled = DrawableWipeIcon(Res.drawable.hearing_aid_disabled_24px)
    val HearingAid = DrawableWipeIcon(Res.drawable.hearing_aid_24px)
    val HearingAidDisabledLeft = DrawableWipeIcon(Res.drawable.hearing_aid_disabled_left_24px)
    val HearingDisabled = DrawableWipeIcon(Res.drawable.hearing_disabled_24px)
    val Hearing = DrawableWipeIcon(Res.drawable.hearing_24px)
    val HourglassDisabled = DrawableWipeIcon(Res.drawable.hourglass_disabled_24px)
    val Hourglass = DrawableWipeIcon(Res.drawable.hourglass_24px)
    val LocationDisabled = DrawableWipeIcon(Res.drawable.location_disabled_24px)
    val LocationSearching = DrawableWipeIcon(Res.drawable.location_searching_24px)
    val NearMeDisabled = DrawableWipeIcon(Res.drawable.near_me_disabled_24px)
    val NearMe = DrawableWipeIcon(Res.drawable.near_me_24px)
    val PersonAddDisabled = DrawableWipeIcon(Res.drawable.person_add_disabled_24px)
    val PersonAdd = DrawableWipeIcon(Res.drawable.person_add_24px)
    val PhoneDisabled = DrawableWipeIcon(Res.drawable.phone_disabled_24px)
    val PhoneEnabled = DrawableWipeIcon(Res.drawable.phone_enabled_24px)
    val PlayDisabled = DrawableWipeIcon(Res.drawable.play_disabled_24px)
    val PlayArrow = DrawableWipeIcon(Res.drawable.play_arrow_24px)
    val PrintDisabled = DrawableWipeIcon(Res.drawable.print_disabled_24px)
    val Print = DrawableWipeIcon(Res.drawable.print_24px)
    val SyncDisabled = DrawableWipeIcon(Res.drawable.sync_disabled_24px)
    val Sync = DrawableWipeIcon(Res.drawable.sync_24px)
    val UpdateDisabled = DrawableWipeIcon(Res.drawable.update_disabled_24px)
    val Update = DrawableWipeIcon(Res.drawable.update_24px)
    val AccountCircleOff = DrawableWipeIcon(Res.drawable.account_circle_off_24px)
    val AccountCircle = DrawableWipeIcon(Res.drawable.account_circle_24px)
    val AdGroupOff = DrawableWipeIcon(Res.drawable.ad_group_off_24px)
    val AdGroup = DrawableWipeIcon(Res.drawable.ad_group_24px)
    val AdOff = DrawableWipeIcon(Res.drawable.ad_off_24px)
    val Ad = DrawableWipeIcon(Res.drawable.ad_24px)
    val AdaptiveAudioMicOff = DrawableWipeIcon(Res.drawable.adaptive_audio_mic_off_24px)
    val AdaptiveAudioMic = DrawableWipeIcon(Res.drawable.adaptive_audio_mic_24px)
    val AlarmOff = DrawableWipeIcon(Res.drawable.alarm_off_24px)
    val Alarm = DrawableWipeIcon(Res.drawable.alarm_24px)
    val AndroidCell4BarOff = DrawableWipeIcon(Res.drawable.android_cell_4_bar_off_24px)
    val AndroidCell4Bar = DrawableWipeIcon(Res.drawable.android_cell_4_bar_24px)
    val AndroidCell5BarOff = DrawableWipeIcon(Res.drawable.android_cell_5_bar_off_24px)
    val AndroidCell5Bar = DrawableWipeIcon(Res.drawable.android_cell_5_bar_24px)
    val AndroidWifi3BarOff = DrawableWipeIcon(Res.drawable.android_wifi_3_bar_off_24px)
    val AndroidWifi3Bar = DrawableWipeIcon(Res.drawable.android_wifi_3_bar_24px)
    val AndroidWifi4BarOff = DrawableWipeIcon(Res.drawable.android_wifi_4_bar_off_24px)
    val AndroidWifi4Bar = DrawableWipeIcon(Res.drawable.android_wifi_4_bar_24px)
    val ApprovalDelegationOff = DrawableWipeIcon(Res.drawable.approval_delegation_off_24px)
    val ApprovalDelegation = DrawableWipeIcon(Res.drawable.approval_delegation_24px)
    val AttachFileOff = DrawableWipeIcon(Res.drawable.attach_file_off_24px)
    val AttachFile = DrawableWipeIcon(Res.drawable.attach_file_24px)
    val AutoStoriesOff = DrawableWipeIcon(Res.drawable.auto_stories_off_24px)
    val AutoStories = DrawableWipeIcon(Res.drawable.auto_stories_24px)
    val BacklightHighOff = DrawableWipeIcon(Res.drawable.backlight_high_off_24px)
    val BacklightHigh = DrawableWipeIcon(Res.drawable.backlight_high_24px)
    val BarChartOff = DrawableWipeIcon(Res.drawable.bar_chart_off_24px)
    val BarChart = DrawableWipeIcon(Res.drawable.bar_chart_24px)
    val BedtimeOff = DrawableWipeIcon(Res.drawable.bedtime_off_24px)
    val Bedtime = DrawableWipeIcon(Res.drawable.bedtime_24px)
    val BlurOff = DrawableWipeIcon(Res.drawable.blur_off_24px)
    val BlurOn = DrawableWipeIcon(Res.drawable.blur_on_24px)
    val ChatBubbleOff = DrawableWipeIcon(Res.drawable.chat_bubble_off_24px)
    val ChatBubble = DrawableWipeIcon(Res.drawable.chat_bubble_24px)
    val CloudOff = DrawableWipeIcon(Res.drawable.cloud_off_24px)
    val Cloud = DrawableWipeIcon(Res.drawable.cloud_24px)
    val CodeOff = DrawableWipeIcon(Res.drawable.code_off_24px)
    val Code = DrawableWipeIcon(Res.drawable.code_24px)
    val ContactlessOff = DrawableWipeIcon(Res.drawable.contactless_off_24px)
    val Contactless = DrawableWipeIcon(Res.drawable.contactless_24px)
    val ContentPasteOff = DrawableWipeIcon(Res.drawable.content_paste_off_24px)
    val ContentPaste = DrawableWipeIcon(Res.drawable.content_paste_24px)
    val ContrastRtlOff = DrawableWipeIcon(Res.drawable.contrast_rtl_off_24px)
    val ConversionPathOff = DrawableWipeIcon(Res.drawable.conversion_path_off_24px)
    val ConversionPath = DrawableWipeIcon(Res.drawable.conversion_path_24px)
    val CookieOff = DrawableWipeIcon(Res.drawable.cookie_off_24px)
    val Cookie = DrawableWipeIcon(Res.drawable.cookie_24px)
    val CreditCardOff = DrawableWipeIcon(Res.drawable.credit_card_off_24px)
    val CreditCard = DrawableWipeIcon(Res.drawable.credit_card_24px)
    val DatabaseOff = DrawableWipeIcon(Res.drawable.database_off_24px)
    val Database = DrawableWipeIcon(Res.drawable.database_24px)
    val DetectionAndZoneOff = DrawableWipeIcon(Res.drawable.detection_and_zone_off_24px)
    val DetectionAndZone = DrawableWipeIcon(Res.drawable.detection_and_zone_24px)
    val DeveloperBoardOff = DrawableWipeIcon(Res.drawable.developer_board_off_24px)
    val DeveloperBoard = DrawableWipeIcon(Res.drawable.developer_board_24px)
    val DevicesOff = DrawableWipeIcon(Res.drawable.devices_off_24px)
    val Devices = DrawableWipeIcon(Res.drawable.devices_24px)
    val DirectionsAltOff = DrawableWipeIcon(Res.drawable.directions_alt_off_24px)
    val DirectionsAlt = DrawableWipeIcon(Res.drawable.directions_alt_24px)
    val DirectionsOff = DrawableWipeIcon(Res.drawable.directions_off_24px)
    val Directions = DrawableWipeIcon(Res.drawable.directions_24px)
    val DoNotDisturbOff = DrawableWipeIcon(Res.drawable.do_not_disturb_off_24px)
    val DoNotDisturbOn = DrawableWipeIcon(Res.drawable.do_not_disturb_on_24px)
    val DomainVerificationOff = DrawableWipeIcon(Res.drawable.domain_verification_off_24px)
    val DomainVerification = DrawableWipeIcon(Res.drawable.domain_verification_24px)
    val EditOff = DrawableWipeIcon(Res.drawable.edit_off_24px)
    val Edit = DrawableWipeIcon(Res.drawable.edit_24px)
    val EmergencyShareOff = DrawableWipeIcon(Res.drawable.emergency_share_off_24px)
    val EmergencyShare = DrawableWipeIcon(Res.drawable.emergency_share_24px)
    val EncryptedOff = DrawableWipeIcon(Res.drawable.encrypted_off_24px)
    val Encrypted = DrawableWipeIcon(Res.drawable.encrypted_24px)
    val EnterpriseOff = DrawableWipeIcon(Res.drawable.enterprise_off_24px)
    val Enterprise = DrawableWipeIcon(Res.drawable.enterprise_24px)
    val ExploreOff = DrawableWipeIcon(Res.drawable.explore_off_24px)
    val Explore = DrawableWipeIcon(Res.drawable.explore_24px)
    val ExtensionOff = DrawableWipeIcon(Res.drawable.extension_off_24px)
    val Extension = DrawableWipeIcon(Res.drawable.extension_24px)
    val FaceRetouchingOff = DrawableWipeIcon(Res.drawable.face_retouching_off_24px)
    val Contrast = DrawableWipeIcon(Res.drawable.contrast_24px)
    val FileCopyOff = DrawableWipeIcon(Res.drawable.file_copy_off_24px)
    val FileCopy = DrawableWipeIcon(Res.drawable.file_copy_24px)
    val FileDownloadOff = DrawableWipeIcon(Res.drawable.file_download_off_24px)
    val FileDownload = DrawableWipeIcon(Res.drawable.file_download_24px)
    val FileSaveOff = DrawableWipeIcon(Res.drawable.file_save_off_24px)
    val FileSave = DrawableWipeIcon(Res.drawable.file_save_24px)
    val FileUploadOff = DrawableWipeIcon(Res.drawable.file_upload_off_24px)
    val FileUpload = DrawableWipeIcon(Res.drawable.file_upload_24px)
    val FilterAltOff = DrawableWipeIcon(Res.drawable.filter_alt_off_24px)
    val FilterAlt = DrawableWipeIcon(Res.drawable.filter_alt_24px)
    val FilterListOff = DrawableWipeIcon(Res.drawable.filter_list_off_24px)
    val FilterList = DrawableWipeIcon(Res.drawable.filter_list_24px)
    val FingerprintOff = DrawableWipeIcon(Res.drawable.fingerprint_off_24px)
    val Fingerprint = DrawableWipeIcon(Res.drawable.fingerprint_24px)
    val FlashOff = DrawableWipeIcon(Res.drawable.flash_off_24px)
    val FlashOn = DrawableWipeIcon(Res.drawable.flash_on_24px)
    val FlashlightOff = DrawableWipeIcon(Res.drawable.flashlight_off_24px)
    val FlashlightOn = DrawableWipeIcon(Res.drawable.flashlight_on_24px)
    val FolderOff = DrawableWipeIcon(Res.drawable.folder_off_24px)
    val Folder = DrawableWipeIcon(Res.drawable.folder_24px)
    val FontDownloadOff = DrawableWipeIcon(Res.drawable.font_download_off_24px)
    val FontDownload = DrawableWipeIcon(Res.drawable.font_download_24px)
    val FormatPaintOff = DrawableWipeIcon(Res.drawable.format_paint_off_24px)
    val FormatPaint = DrawableWipeIcon(Res.drawable.format_paint_24px)
    val FormatQuoteOff = DrawableWipeIcon(Res.drawable.format_quote_off_24px)
    val FormatQuote = DrawableWipeIcon(Res.drawable.format_quote_24px)
    val FramePersonOff = DrawableWipeIcon(Res.drawable.frame_person_off_24px)
    val FramePerson = DrawableWipeIcon(Res.drawable.frame_person_24px)
    val GraphicEqOff = DrawableWipeIcon(Res.drawable.graphic_eq_off_24px)
    val GraphicEq = DrawableWipeIcon(Res.drawable.graphic_eq_24px)
    val Grid3x3Off = DrawableWipeIcon(Res.drawable.grid_3x3_off_24px)
    val Grid3x3 = DrawableWipeIcon(Res.drawable.grid_3x3_24px)
    val GridOff = DrawableWipeIcon(Res.drawable.grid_off_24px)
    val GridOn = DrawableWipeIcon(Res.drawable.grid_on_24px)
    val GroupOff = DrawableWipeIcon(Res.drawable.group_off_24px)
    val Group = DrawableWipeIcon(Res.drawable.group_24px)
    val HangoutVideoOff = DrawableWipeIcon(Res.drawable.hangout_video_off_24px)
    val HangoutVideo = DrawableWipeIcon(Res.drawable.hangout_video_24px)
    val HdrOff = DrawableWipeIcon(Res.drawable.hdr_off_24px)
    val HdrOn = DrawableWipeIcon(Res.drawable.hdr_on_24px)
    val HdrOffSelect = DrawableWipeIcon(Res.drawable.hdr_off_select_24px)
    val HdrPlusOff = DrawableWipeIcon(Res.drawable.hdr_plus_off_24px)
    val HdrPlus = DrawableWipeIcon(Res.drawable.hdr_plus_24px)
    val HeadsetOff = DrawableWipeIcon(Res.drawable.headset_off_24px)
    val HeadsetMic = DrawableWipeIcon(Res.drawable.headset_mic_24px)
    val HlsOff = DrawableWipeIcon(Res.drawable.hls_off_24px)
    val Hls = DrawableWipeIcon(Res.drawable.hls_24px)
    val HideSource = DrawableWipeIcon(Res.drawable.hide_source_24px)
    val InvertColorsOff = DrawableWipeIcon(Res.drawable.invert_colors_off_24px)
    val InvertColors = DrawableWipeIcon(Res.drawable.invert_colors_24px)
    val KeyOff = DrawableWipeIcon(Res.drawable.key_off_24px)
    val Key = DrawableWipeIcon(Res.drawable.key_24px)
    val LabelOff = DrawableWipeIcon(Res.drawable.label_off_24px)
    val Label = DrawableWipeIcon(Res.drawable.label_24px)
    val LinkOff = DrawableWipeIcon(Res.drawable.link_off_24px)
    val Link = DrawableWipeIcon(Res.drawable.link_24px)
    val MacroOff = DrawableWipeIcon(Res.drawable.macro_off_24px)
    val MailOff = DrawableWipeIcon(Res.drawable.mail_off_24px)
    val Mail = DrawableWipeIcon(Res.drawable.mail_24px)
    val MatchCaseOff = DrawableWipeIcon(Res.drawable.match_case_off_24px)
    val MatchCase = DrawableWipeIcon(Res.drawable.match_case_24px)
    val MediaBluetoothOff = DrawableWipeIcon(Res.drawable.media_bluetooth_off_24px)
    val MediaBluetoothOn = DrawableWipeIcon(Res.drawable.media_bluetooth_on_24px)
    val MediaOutputOff = DrawableWipeIcon(Res.drawable.media_output_off_24px)
    val MediaOutput = DrawableWipeIcon(Res.drawable.media_output_24px)
    val MicExternalOff = DrawableWipeIcon(Res.drawable.mic_external_off_24px)
    val MicExternalOn = DrawableWipeIcon(Res.drawable.mic_external_on_24px)
    val MicOff = DrawableWipeIcon(Res.drawable.mic_off_24px)
    val Mic = DrawableWipeIcon(Res.drawable.mic_24px)
    val MobileHandLeftOff = DrawableWipeIcon(Res.drawable.mobile_hand_left_off_24px)
    val MobileHandLeft = DrawableWipeIcon(Res.drawable.mobile_hand_left_24px)
    val MobileHandOff = DrawableWipeIcon(Res.drawable.mobile_hand_off_24px)
    val MobileHand = DrawableWipeIcon(Res.drawable.mobile_hand_24px)
    val MobileOff = DrawableWipeIcon(Res.drawable.mobile_off_24px)
    val Mobile = DrawableWipeIcon(Res.drawable.mobile_24px)
    val MobileSoundOff = DrawableWipeIcon(Res.drawable.mobile_sound_off_24px)
    val MobileSound = DrawableWipeIcon(Res.drawable.mobile_sound_24px)
    val MobiledataOff = DrawableWipeIcon(Res.drawable.mobiledata_off_24px)
    val MobiledataArrows = DrawableWipeIcon(Res.drawable.mobiledata_arrows_24px)
    val ModeCoolOff = DrawableWipeIcon(Res.drawable.mode_cool_off_24px)
    val ModeCool = DrawableWipeIcon(Res.drawable.mode_cool_24px)
    val ModeFanOff = DrawableWipeIcon(Res.drawable.mode_fan_off_24px)
    val ModeFan = DrawableWipeIcon(Res.drawable.mode_fan_24px)
    val ModeHeatOff = DrawableWipeIcon(Res.drawable.mode_heat_off_24px)
    val ModeHeat = DrawableWipeIcon(Res.drawable.mode_heat_24px)
    val MouseLockOff = DrawableWipeIcon(Res.drawable.mouse_lock_off_24px)
    val MouseLock = DrawableWipeIcon(Res.drawable.mouse_lock_24px)
    val MovieOff = DrawableWipeIcon(Res.drawable.movie_off_24px)
    val Movie = DrawableWipeIcon(Res.drawable.movie_24px)
    val MusicOff = DrawableWipeIcon(Res.drawable.music_off_24px)
    val MusicNote = DrawableWipeIcon(Res.drawable.music_note_24px)
    val NearbyOff = DrawableWipeIcon(Res.drawable.nearby_off_24px)
    val Nearby = DrawableWipeIcon(Res.drawable.nearby_24px)
    val NfcOff = DrawableWipeIcon(Res.drawable.nfc_off_24px)
    val Nfc = DrawableWipeIcon(Res.drawable.nfc_24px)
    val NightSightAutoOff = DrawableWipeIcon(Res.drawable.night_sight_auto_off_24px)
    val NightSightAuto = DrawableWipeIcon(Res.drawable.night_sight_auto_24px)
    val NoiseControlOff = DrawableWipeIcon(Res.drawable.noise_control_off_24px)
    val NoiseControlOn = DrawableWipeIcon(Res.drawable.noise_control_on_24px)
    val NotificationAudioOff = DrawableWipeIcon(Res.drawable.notification_audio_off_24px)
    val NotificationAudio = DrawableWipeIcon(Res.drawable.notification_audio_24px)
    val NotificationsOff = DrawableWipeIcon(Res.drawable.notifications_off_24px)
    val Notifications = DrawableWipeIcon(Res.drawable.notifications_24px)
    val OfflinePinOff = DrawableWipeIcon(Res.drawable.offline_pin_off_24px)
    val OfflinePin = DrawableWipeIcon(Res.drawable.offline_pin_24px)
    val OpenInNewOff = DrawableWipeIcon(Res.drawable.open_in_new_off_24px)
    val OpenInNew = DrawableWipeIcon(Res.drawable.open_in_new_24px)
    val Password2Off = DrawableWipeIcon(Res.drawable.password_2_off_24px)
    val Password2 = DrawableWipeIcon(Res.drawable.password_2_24px)
    val PersonOff = DrawableWipeIcon(Res.drawable.person_off_24px)
    val Person = DrawableWipeIcon(Res.drawable.person_24px)
    val PersonalBagOff = DrawableWipeIcon(Res.drawable.personal_bag_off_24px)
    val PersonalBag = DrawableWipeIcon(Res.drawable.personal_bag_24px)
    val PianoOff = DrawableWipeIcon(Res.drawable.piano_off_24px)
    val Piano = DrawableWipeIcon(Res.drawable.piano_24px)
    val PictureInPictureOff = DrawableWipeIcon(Res.drawable.picture_in_picture_off_24px)
    val PictureInPicture = DrawableWipeIcon(Res.drawable.picture_in_picture_24px)
    val PillOff = DrawableWipeIcon(Res.drawable.pill_off_24px)
    val Pill = DrawableWipeIcon(Res.drawable.pill_24px)
    val PortableWifiOff = DrawableWipeIcon(Res.drawable.portable_wifi_off_24px)
    val PowerOff = DrawableWipeIcon(Res.drawable.power_off_24px)
    val Power = DrawableWipeIcon(Res.drawable.power_24px)
    val PreviewOff = DrawableWipeIcon(Res.drawable.preview_off_24px)
    val Preview = DrawableWipeIcon(Res.drawable.preview_24px)
    val PublicOff = DrawableWipeIcon(Res.drawable.public_off_24px)
    val Public = DrawableWipeIcon(Res.drawable.public_24px)
    val RawOff = DrawableWipeIcon(Res.drawable.raw_off_24px)
    val RawOn = DrawableWipeIcon(Res.drawable.raw_on_24px)
    val ReceiptLongOff = DrawableWipeIcon(Res.drawable.receipt_long_off_24px)
    val ReceiptLong = DrawableWipeIcon(Res.drawable.receipt_long_24px)
    val ReportOff = DrawableWipeIcon(Res.drawable.report_off_24px)
    val Report = DrawableWipeIcon(Res.drawable.report_24px)
    val RouterOff = DrawableWipeIcon(Res.drawable.router_off_24px)
    val Router = DrawableWipeIcon(Res.drawable.router_24px)
    val SafetyCheckOff = DrawableWipeIcon(Res.drawable.safety_check_off_24px)
    val SafetyCheck = DrawableWipeIcon(Res.drawable.safety_check_24px)
    val ScienceOff = DrawableWipeIcon(Res.drawable.science_off_24px)
    val Science = DrawableWipeIcon(Res.drawable.science_24px)
    val SelectWindowOff = DrawableWipeIcon(Res.drawable.select_window_off_24px)
    val SelectWindow = DrawableWipeIcon(Res.drawable.select_window_24px)
    val SensorsKrxOff = DrawableWipeIcon(Res.drawable.sensors_krx_off_24px)
    val SensorsKrx = DrawableWipeIcon(Res.drawable.sensors_krx_24px)
    val SensorsOff = DrawableWipeIcon(Res.drawable.sensors_off_24px)
    val Sensors = DrawableWipeIcon(Res.drawable.sensors_24px)
    val ShiftLockOff = DrawableWipeIcon(Res.drawable.shift_lock_off_24px)
    val ShiftLock = DrawableWipeIcon(Res.drawable.shift_lock_24px)
    val ShoppingCartOff = DrawableWipeIcon(Res.drawable.shopping_cart_off_24px)
    val ShoppingCart = DrawableWipeIcon(Res.drawable.shopping_cart_24px)
    val SignalCellularOff = DrawableWipeIcon(Res.drawable.signal_cellular_off_24px)
    val SignalWifiOff = DrawableWipeIcon(Res.drawable.signal_wifi_off_24px)
    val SmartCardReaderOff = DrawableWipeIcon(Res.drawable.smart_card_reader_off_24px)
    val SmartCardReader = DrawableWipeIcon(Res.drawable.smart_card_reader_24px)
    val SpeakerNotesOff = DrawableWipeIcon(Res.drawable.speaker_notes_off_24px)
    val SpeakerNotes = DrawableWipeIcon(Res.drawable.speaker_notes_24px)
    val StackOff = DrawableWipeIcon(Res.drawable.stack_off_24px)
    val Stack = DrawableWipeIcon(Res.drawable.stack_24px)
    val SubtitlesOff = DrawableWipeIcon(Res.drawable.subtitles_off_24px)
    val Subtitles = DrawableWipeIcon(Res.drawable.subtitles_24px)
    val SupervisedUserCircleOff = DrawableWipeIcon(Res.drawable.supervised_user_circle_off_24px)
    val SupervisedUserCircle = DrawableWipeIcon(Res.drawable.supervised_user_circle_24px)
    val SyncSavedLocallyOff = DrawableWipeIcon(Res.drawable.sync_saved_locally_off_24px)
    val SyncSavedLocally = DrawableWipeIcon(Res.drawable.sync_saved_locally_24px)
    val TamperDetectionOff = DrawableWipeIcon(Res.drawable.tamper_detection_off_24px)
    val TamperDetectionOn = DrawableWipeIcon(Res.drawable.tamper_detection_on_24px)
    val TextAdOff = DrawableWipeIcon(Res.drawable.text_ad_off_24px)
    val TextAd = DrawableWipeIcon(Res.drawable.text_ad_24px)
    val TimerOff = DrawableWipeIcon(Res.drawable.timer_off_24px)
    val Timer = DrawableWipeIcon(Res.drawable.timer_24px)
    val TouchpadMouseOff = DrawableWipeIcon(Res.drawable.touchpad_mouse_off_24px)
    val TouchpadMouse = DrawableWipeIcon(Res.drawable.touchpad_mouse_24px)
    val TvOff = DrawableWipeIcon(Res.drawable.tv_off_24px)
    val Tv = DrawableWipeIcon(Res.drawable.tv_24px)
    val UsbOff = DrawableWipeIcon(Res.drawable.usb_off_24px)
    val Usb = DrawableWipeIcon(Res.drawable.usb_24px)
    val VerifiedOff = DrawableWipeIcon(Res.drawable.verified_off_24px)
    val Verified = DrawableWipeIcon(Res.drawable.verified_24px)
    val VideoCameraFrontOff = DrawableWipeIcon(Res.drawable.video_camera_front_off_24px)
    val VideoCameraFront = DrawableWipeIcon(Res.drawable.video_camera_front_24px)
    val VideocamOff = DrawableWipeIcon(Res.drawable.videocam_off_24px)
    val Videocam = DrawableWipeIcon(Res.drawable.videocam_24px)
    val VideogameAssetOff = DrawableWipeIcon(Res.drawable.videogame_asset_off_24px)
    val VideogameAsset = DrawableWipeIcon(Res.drawable.videogame_asset_24px)
    val WatchOff = DrawableWipeIcon(Res.drawable.watch_off_24px)
    val Watch = DrawableWipeIcon(Res.drawable.watch_24px)
    val ViewInArOff = DrawableWipeIcon(Res.drawable.view_in_ar_off_24px)
    val ViewInAr = DrawableWipeIcon(Res.drawable.view_in_ar_24px)
    val VisibilityOff = DrawableWipeIcon(Res.drawable.visibility_off_24px)
    val Visibility = DrawableWipeIcon(Res.drawable.visibility_24px)
    val VoiceChatOff = DrawableWipeIcon(Res.drawable.voice_chat_off_24px)
    val VoiceChat = DrawableWipeIcon(Res.drawable.voice_chat_24px)
    val VoiceOverOff = DrawableWipeIcon(Res.drawable.voice_over_off_24px)
    val VoiceSelectionOff = DrawableWipeIcon(Res.drawable.voice_selection_off_24px)
    val VoiceSelection = DrawableWipeIcon(Res.drawable.voice_selection_24px)
    val VolumeOff = DrawableWipeIcon(Res.drawable.volume_off_24px)
    val VolumeUp = DrawableWipeIcon(Res.drawable.volume_up_24px)
    val VpnKeyOff = DrawableWipeIcon(Res.drawable.vpn_key_off_24px)
    val VpnKey = DrawableWipeIcon(Res.drawable.vpn_key_24px)
    val Vr180Create2dOff = DrawableWipeIcon(Res.drawable.vr180_create2d_off_24px)
    val Vr180Create2d = DrawableWipeIcon(Res.drawable.vr180_create2d_24px)
    val WarningOff = DrawableWipeIcon(Res.drawable.warning_off_24px)
    val Warning = DrawableWipeIcon(Res.drawable.warning_24px)
    val WebAssetOff = DrawableWipeIcon(Res.drawable.web_asset_off_24px)
    val WebAsset = DrawableWipeIcon(Res.drawable.web_asset_24px)
    val WifiOff = DrawableWipeIcon(Res.drawable.wifi_off_24px)
    val Wifi = DrawableWipeIcon(Res.drawable.wifi_24px)
    val WifiTetheringOff = DrawableWipeIcon(Res.drawable.wifi_tethering_off_24px)
    val WifiTethering = DrawableWipeIcon(Res.drawable.wifi_tethering_24px)
    val LocationOff = DrawableWipeIcon(Res.drawable.location_off_24px)
    val LocationOn = DrawableWipeIcon(Res.drawable.location_on_24px)
    val NoAccounts = DrawableWipeIcon(Res.drawable.no_accounts_24px)
    val NoEncryption = DrawableWipeIcon(Res.drawable.no_encryption_24px)
    val Lock = DrawableWipeIcon(Res.drawable.lock_24px)
    val NoFood = DrawableWipeIcon(Res.drawable.no_food_24px)
    val Fastfood = DrawableWipeIcon(Res.drawable.fastfood_24px)
    val NoLuggage = DrawableWipeIcon(Res.drawable.no_luggage_24px)
    val Luggage = DrawableWipeIcon(Res.drawable.luggage_24px)
    val NoMeals = DrawableWipeIcon(Res.drawable.no_meals_24px)
    val Restaurant = DrawableWipeIcon(Res.drawable.restaurant_24px)
    val NoMeetingRoom = DrawableWipeIcon(Res.drawable.no_meeting_room_24px)
    val MeetingRoom = DrawableWipeIcon(Res.drawable.meeting_room_24px)
    val NoPhotography = DrawableWipeIcon(Res.drawable.no_photography_24px)
    val PhotoCamera = DrawableWipeIcon(Res.drawable.photo_camera_24px)
    val NoStroller = DrawableWipeIcon(Res.drawable.no_stroller_24px)
    val Stroller = DrawableWipeIcon(Res.drawable.stroller_24px)
    val NoTransfer = DrawableWipeIcon(Res.drawable.no_transfer_24px)
    val DirectionsBus = DrawableWipeIcon(Res.drawable.directions_bus_24px)
    val Unpublished = DrawableWipeIcon(Res.drawable.unpublished_24px)
    val Unlicense = DrawableWipeIcon(Res.drawable.unlicense_24px)
    val Check = DrawableWipeIcon(Res.drawable.check_24px)
    val ContentCopy = DrawableWipeIcon(Res.drawable.content_copy_24px)
    val DarkMode = DrawableWipeIcon(Res.drawable.dark_mode_24px)
    val Lightbulb = DrawableWipeIcon(Res.drawable.lightbulb_24px)
    val LightOff = DrawableWipeIcon(Res.drawable.light_off_24px)
    val LightMode = DrawableWipeIcon(Res.drawable.light_mode_24px)
    val Pause = DrawableWipeIcon(Res.drawable.pause_24px)
    val Speed = DrawableWipeIcon(Res.drawable.speed_24px)
    val Stop = DrawableWipeIcon(Res.drawable.stop_24px)
    val NorthWest = DrawableWipeIcon(Res.drawable.north_west_24px)
    val NorthEast = DrawableWipeIcon(Res.drawable.north_east_24px)
    val SouthWest = DrawableWipeIcon(Res.drawable.south_west_24px)
    val SouthEast = DrawableWipeIcon(Res.drawable.south_east_24px)
    val KeyboardArrowDown = DrawableWipeIcon(Res.drawable.keyboard_arrow_down_24px)
    val KeyboardArrowLeft = DrawableWipeIcon(Res.drawable.keyboard_arrow_left_24px)
    val KeyboardArrowRight = DrawableWipeIcon(Res.drawable.keyboard_arrow_right_24px)
    val KeyboardArrowUp = DrawableWipeIcon(Res.drawable.keyboard_arrow_up_24px)
    val Headset = DrawableWipeIcon(Res.drawable.headset_mic_24px)
}

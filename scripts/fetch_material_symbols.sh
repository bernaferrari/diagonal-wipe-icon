#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
DRAWABLE_DIR="$ROOT_DIR/composeApp/src/commonMain/composeResources/drawable"
MAPPING_FILE="$ROOT_DIR/composeApp/src/commonMain/kotlin/com/bernaferrari/diagonalwipeicon/MaterialSymbolIcons.kt"

BASE_URL="https://raw.githubusercontent.com/google/material-design-icons/master/symbols/android"

REQUESTED_ICON_SETS=(
  # Disabled / explicit variants
  bid_landscape_disabled
  bluetooth_disabled
  closed_caption_disabled
  comments_disabled
  desktop_access_disabled
  domain_disabled
  hearing_aid_disabled
  hearing_aid_disabled_left
  hearing_disabled
  hourglass_disabled
  location_disabled
  near_me_disabled
  person_add_disabled
  phone_disabled
  play_disabled
  print_disabled
  sync_disabled
  update_disabled

  # Off variants
  account_circle_off
  ad_group_off
  ad_off
  adaptive_audio_mic_off
  alarm_off
  android_cell_4_bar_off
  android_cell_5_bar_off
  android_wifi_3_bar_off
  android_wifi_4_bar_off
  approval_delegation_off
  attach_file_off
  auto_stories_off
  backlight_high_off
  bar_chart_off
  bedtime_off
  blur_off
  chat_bubble_off
  cloud_off
  code_off
  contactless_off
  content_paste_off
  contrast_rtl_off
  conversion_path_off
  cookie_off
  credit_card_off
  database_off
  detection_and_zone_off
  developer_board_off
  devices_off
  directions_alt_off
  directions_off
  do_not_disturb_off
  domain_verification_off
  edit_off
  emergency_share_off
  encrypted_off
  enterprise_off
  explore_off
  extension_off
  face_retouching_off
  file_copy_off
  file_download_off
  file_save_off
  file_upload_off
  filter_alt_off
  filter_list_off
  fingerprint_off
  flash_off
  flashlight_off
  folder_off
  font_download_off
  format_paint_off
  format_quote_off
  frame_person_off
  graphic_eq_off
  grid_3x3_off
  grid_off
  group_off
  hangout_video_off
  hdr_off
  hdr_off_select
  hdr_plus_off
  headset_off
  hls_off
  hide_source
  invert_colors_off
  key_off
  label_off
  link_off
  macro_off
  mail_off
  match_case_off
  media_bluetooth_off
  media_output_off
  mic_external_off
  mic_off
  mobile_hand_left_off
  mobile_hand_off
  mobile_off
  mobile_sound_off
  mobiledata_off
  mode_cool_off
  mode_fan_off
  mode_heat_off
  money_off
  mouse_lock_off
  movie_off
  music_off
  nearby_off
  nfc_off
  night_sight_auto_off
  noise_control_off
  notification_audio_off
  notifications_off
  offline_pin_off
  open_in_new_off
  password_2_off
  person_off
  personal_bag_off
  piano_off
  picture_in_picture_off
  pill_off
  portable_wifi_off
  power_off
  preview_off
  public_off
  raw_off
  receipt_long_off
  report_off
  router_off
  safety_check_off
  science_off
  search_off
  select_window_off
  sensors_krx_off
  sensors_off
  share_off
  shift_lock_off
  shopping_cart_off
  signal_cellular_off
  signal_wifi_off
  smart_card_reader_off
  spatial_audio_off
  speaker_notes_off
  stack_off
  subtitles_off
  supervised_user_circle_off
  sync_saved_locally_off
  tamper_detection_off
  text_ad_off
  timer_off
  touchpad_mouse_off
  tv_off
  usb_off
  verified_off
  video_camera_front_off
  videocam_off
  videogame_asset_off
  watch_off
  view_in_ar_off
  visibility_off
  voice_chat_off
  voice_over_off
  voice_selection_off
  volume_off
  vpn_key_off
  vr180_create2d_off
  warning_off
  web_asset_off
  wifi_off
  wifi_tethering_off
  location_off

  # Explicit restriction/no-state
  no_accounts
  no_encryption
  no_food
  no_luggage
  no_meals
  no_meeting_room
  no_photography
  no_stroller
  no_transfer
  unpublished
  unlicense
)

IGNORE_SYMBOLS=(
  share
  share_off
  money
  money_off
  search
  search_off
  spatial_audio
  spatial_audio_off
  toggle
  toggle_on
  toggle_off
)

# Icons used in the app outside the disabled/off catalog.
UI_SYMBOLS=(
  check
  content_copy
  dark_mode
  lightbulb
  light_off
  light_mode
  pause
  play_arrow
  speed
  stop
  north_west
  north_east
  south_west
  south_east
  keyboard_arrow_down
  keyboard_arrow_left
  keyboard_arrow_right
  keyboard_arrow_up
)

# Fallback source mapping for symbols that are not available under the
# requested asset name in Material Symbols outlined.
#
# Format: requested_symbol:fallback_source
SYMBOL_FALLBACKS=(
  "headset:headset_mic"
  "hls_off:hls"
  "invert_colors_off:invert_colors"
  "key_off:key"
  "label_off:label"
)

to_pascal_case() {
  local name="$1"
  local result=""
  IFS='_' read -r -a parts <<< "$name"
  for part in "${parts[@]}"; do
    if [[ -z "$part" ]]; then
      continue
    fi
    first_char="${part:0:1}"
    remainder="${part:1}"
    capitalized="$(printf '%s%s' "$(printf '%s' "$first_char" | tr '[:lower:]' '[:upper:]')" "$remainder")"
    result+="$capitalized"
  done
  printf '%s\n' "$result"
}

cleanup_symbol_for_id() {
  local value="$1"
  printf '%s' "$value" | tr '[:upper:]' '[:lower:]'
}

SYMBOLS=()
EMITTED_SYMBOLS=()

add_symbol() {
  local value
  value="$(cleanup_symbol_for_id "$1")"
  for ignored in "${IGNORE_SYMBOLS[@]-}"; do
    if [[ "$value" == "$ignored" ]]; then
      return
    fi
  done
  if [[ -z "${SYMBOLS[*]-}" ]] || [[ ! " ${SYMBOLS[*]-} " == *" $value "* ]]; then
    SYMBOLS+=("$value")
  fi
}

resolve_no_state_source() {
  local value="$1"
  case "$value" in
    no_accounts)
      echo "account_circle"
      ;;
    no_encryption)
      echo "lock"
      ;;
    no_food)
      echo "fastfood"
      ;;
    no_meals)
      echo "restaurant"
      ;;
    no_photography)
      echo "photo_camera"
      ;;
    no_transfer)
      echo "directions_bus"
      ;;
    *)
      echo "${value#no_}"
      ;;
  esac
}

resolve_off_source() {
  local value="$1"
  case "$value" in
    do_not_disturb_off)
      echo "do_not_disturb_on"
      ;;
    contrast_rtl_off)
      echo "contrast"
      ;;
    music_off)
      echo "music_note"
      ;;
    headset_off)
      echo "headset_mic"
      ;;
    mobiledata_off)
      echo "mobiledata_arrows"
      ;;
    volume_off)
      echo "volume_up"
      ;;
    *)
      echo "${value%_off}"
      ;;
  esac
}

resolve_disabled_source() {
  local value="$1"
  case "$value" in
    comments_disabled)
      echo "comment"
      ;;
    desktop_access_disabled)
      echo "desktop_windows"
      ;;
    location_disabled)
      echo "location_searching"
      ;;
    phone_disabled)
      echo "phone_enabled"
      ;;
    play_disabled)
      echo "play_arrow"
      ;;
    *)
      echo "${value%_disabled}"
      ;;
  esac
}

symbol_exists_remote() {
  local value="$1"
  local url
  url="$BASE_URL/$value/materialsymbolsoutlined/${value}_24px.xml"
  curl -fsI --max-time 8 "$url" >/dev/null
}

resolve_source_with_on_fallback() {
  local value="$1"
  if symbol_exists_remote "$value"; then
    echo "$value"
    return
  fi

  if symbol_exists_remote "${value}_on"; then
    echo "${value}_on"
    return
  fi

  echo "$value"
}

normalize_vector_xml() {
  local file_path="$1"
  local normalized

  normalized="$(sed -E \
    -e 's#android:tint=\"[^\"]*\"##g' \
    -e 's#@android:color/white#\#FFFFFFFF#g' \
    -e 's#@android:color/black#\#FF000000#g' \
    -e 's!@android#([0-9A-Fa-f]{6,8})!#\1!g' \
    "$file_path")"
  printf '%s\n' "$normalized" > "$file_path"
}

is_fetched() {
  local value="$1"
  local emitted
  for emitted in "${EMITTED_SYMBOLS[@]-}"; do
    if [[ "$emitted" == "$value" ]]; then
      return 0
    fi
  done
  return 1
}

emit_symbol_property() {
  local property_name="$1"
  local resource_name="$2"

  if is_fetched "$property_name"; then
    return
  fi

  if [[ ! -f "$DRAWABLE_DIR/${resource_name}_24px.xml" ]]; then
    return
  fi

  echo "    val $(to_pascal_case "$property_name") = DrawableWipeIcon(Res.drawable.${resource_name}_24px)" >> "$MAPPING_FILE"
  EMITTED_SYMBOLS+=("$property_name")
}

for name in "${REQUESTED_ICON_SETS[@]}"; do
  local_source=""
  add_symbol "$name"
  if [[ "$name" == *_off ]]; then
    local_source="$(resolve_off_source "$name")"
    local_source="$(resolve_source_with_on_fallback "$local_source")"
    add_symbol "$local_source"
  elif [[ "$name" == *_disabled ]]; then
    local_source="$(resolve_disabled_source "$name")"
    local_source="$(resolve_source_with_on_fallback "$local_source")"
    add_symbol "$local_source"
  elif [[ "$name" == no_* ]]; then
    add_symbol "$(resolve_no_state_source "$name")"
  fi
done

for name in "${UI_SYMBOLS[@]}"; do
  add_symbol "$name"
done

for pair in "${SYMBOL_FALLBACKS[@]}"; do
  source="${pair#*:}"
  requested="${pair%:*}"
  add_symbol "$requested"
  add_symbol "$source"
done

mkdir -p "$DRAWABLE_DIR"

cat <<'MAPPING_HEADER' >"$MAPPING_FILE"
package com.bernaferrari.diagonalwipeicon

import diagonalwipeicon.composeapp.generated.resources.Res
import diagonalwipeicon.composeapp.generated.resources.*

/**
 * Material Symbols outlined drawables used for icon morphing and UI controls.
 */
object MaterialSymbolIcons {
MAPPING_HEADER

for name in "${SYMBOLS[@]}"; do
  snake_name="$(cleanup_symbol_for_id "$name")"
  url="$BASE_URL/$snake_name/materialsymbolsoutlined/${snake_name}_24px.xml"
  output="$DRAWABLE_DIR/${snake_name}_24px.xml"
  if curl -fSL "$url" -o "$output"; then
    normalize_vector_xml "$output"
    echo "Downloaded $snake_name -> $output"
    emit_symbol_property "$snake_name" "$snake_name"
  else
    echo "Missing $snake_name (skipped)"
  fi
done

for pair in "${SYMBOL_FALLBACKS[@]}"; do
  requested="${pair%:*}"
  source="${pair#*:}"
  emit_symbol_property "$requested" "$source"
done

cat <<'MAPPING_FOOTER' >>"$MAPPING_FILE"
}
MAPPING_FOOTER

echo "Requested ${#REQUESTED_ICON_SETS[@]} core symbols and ${#UI_SYMBOLS[@]} UI symbols."
echo "Generated ${#SYMBOLS[@]} symbol names"
echo "Generated ${#EMITTED_SYMBOLS[@]} properties in $MAPPING_FILE"
echo "Drawables are stored in $DRAWABLE_DIR"
echo "Done."

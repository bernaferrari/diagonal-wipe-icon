#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
LOG_FILE="/tmp/diagonal-wipe-icon-web.log"
APP_URL=""
SERVER_PID=""
MAX_RETRIES=24
SLEEP_SECONDS=0.5

cleanup() {
  if [[ -n "$SERVER_PID" ]] && kill -0 "$SERVER_PID" 2>/dev/null; then
    kill "$SERVER_PID"
    wait "$SERVER_PID" 2>/dev/null || true
  fi
}

trap cleanup EXIT INT TERM

open_chrome() {
  local url=$1
  if [[ "$(uname -s)" == "Darwin" ]]; then
    if command -v open >/dev/null 2>&1; then
      open -a "Google Chrome" "$url"
    fi
    return
  fi

  if command -v google-chrome >/dev/null 2>&1; then
    google-chrome "$url"
  elif command -v google-chrome-stable >/dev/null 2>&1; then
    google-chrome-stable "$url"
  elif command -v chromium-browser >/dev/null 2>&1; then
    chromium-browser "$url"
  elif command -v xdg-open >/dev/null 2>&1; then
    xdg-open "$url"
  fi
}

discover_url() {
  local candidate=""

  # Try parsing the URL from webpack logs first.
  candidate="$(grep -Eo 'Loopback: http://localhost:[0-9]+' "$LOG_FILE" 2>/dev/null | tail -n 1 | sed 's/Loopback: //')"
  if [[ -n "$candidate" ]]; then
    echo "$candidate"
    return 0
  fi

  # Fallback probes for common localhost ports, keep behavior unchanged on older setups.
  for port in 8081 8080 3000; do
    if curl --head --silent --fail "http://localhost:${port}/" >/dev/null 2>&1; then
      echo "http://localhost:${port}/"
      return 0
    fi
  done

  return 1
}

cd "$ROOT_DIR"
./gradlew :composeApp:jsBrowserDevelopmentRun -q >"$LOG_FILE" 2>&1 &
SERVER_PID=$!

for _ in $(seq 1 "$MAX_RETRIES"); do
  if APP_URL="$(discover_url)"; then
    break
  fi
  sleep "$SLEEP_SECONDS"
done

APP_URL="${APP_URL:-http://localhost:8081/}"

echo "Server started (pid=$SERVER_PID). Opening ${APP_URL} in Chrome..."
open_chrome "$APP_URL"

wait "$SERVER_PID"

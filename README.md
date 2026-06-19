# Charge Hotspot Automator

Android app that watches charger connection events and opens the fastest available Android network panel so an accessibility helper can toggle hotspot and return the user to Home.

> Note: Modern Android does not allow regular Play-style apps to directly enable/disable Wi-Fi hotspot. This app uses a user-enabled AccessibilityService to automate the Settings UI. Device-owner, OEM-signed, or rooted builds can later replace this with privileged APIs.

## Features

- Charger connected: requests hotspot ON.
- Charger disconnected: requests hotspot OFF.
- Optional return-to-Home after the switch.
- Foreground service for reliable quick execution.
- Battery optimization exemption shortcut.
- Manual ON/OFF test buttons.
- GitHub Actions APK build workflow.

## Build locally

```bash
gradle assembleDebug
```

The debug APK is written to `app/build/outputs/apk/debug/`.

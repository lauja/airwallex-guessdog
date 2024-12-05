package com.jasonlau.guessdog.ui

import app.cash.paparazzi.DeviceConfig

enum class UiTestDevices(val device: DeviceConfig) {
    NEXUS_5(device = DeviceConfig.NEXUS_5.copy(fontScale = 2f)),
    PIXEL_5(device = DeviceConfig.PIXEL_5)
}
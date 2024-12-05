package com.jasonlau.guessdog.ui

import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class GuessDogScreenUiTest(
    @TestParameter config: UiTestDevices,
) {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = config.device,
    )

    @Test
    fun snapshot_guessDogScreen() {
        paparazzi.snapshot {
            PreviewGuessDogScreen()
        }
    }

    @Test
    fun snapshot_resetAlertDialog() {
        paparazzi.snapshot {
            PreviewResetAlertDialog()
        }
    }

    @Test
    fun snapshot_gameAlertDialogExpert() {
        paparazzi.snapshot {
            PreviewGameOverAlertDialogExpert()
        }
    }

    @Test
    fun snapshot_gameAlertDialogNovice() {
        paparazzi.snapshot {
            PreviewGameOverAlertDialogNovice()
        }
    }
}
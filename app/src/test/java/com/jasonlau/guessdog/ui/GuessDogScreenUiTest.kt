package com.jasonlau.guessdog.ui

import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.jasonlau.guessdog.PreviewErrorPage
import com.jasonlau.guessdog.PreviewGuessDogScaffold
import com.jasonlau.guessdog.PreviewResetAlertDialog
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
    fun snapshot_guessDogScaffold() {
        paparazzi.snapshot {
            PreviewGuessDogScaffold()
        }
    }

    @Test
    fun snapshot_errorPage() {
        paparazzi.snapshot {
            PreviewErrorPage()
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
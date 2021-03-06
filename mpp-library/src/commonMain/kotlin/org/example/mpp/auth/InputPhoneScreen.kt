/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package org.example.mpp.auth

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.widgets.*
import dev.icerock.moko.widgets.core.Theme
import dev.icerock.moko.widgets.core.Value
import dev.icerock.moko.widgets.screen.*
import dev.icerock.moko.widgets.screen.navigation.NavigationBar
import dev.icerock.moko.widgets.screen.navigation.NavigationItem
import dev.icerock.moko.widgets.screen.navigation.Route
import dev.icerock.moko.widgets.screen.navigation.route
import dev.icerock.moko.widgets.style.view.WidgetSize
import org.example.library.MR
import org.example.mpp.*

class InputPhoneScreen (
    private val theme: Theme,
    private val submitButtons: ButtonWidget.Category,
    private val viewModelFactory: (
        EventsDispatcher<InputPhoneViewModel.EventsListener>
    ) -> InputPhoneViewModel,
    private val routeInputCode: Route<String>
) : WidgetScreen<Args.Empty>(), InputPhoneViewModel.EventsListener, NavigationItem {

    override val navigationBar: NavigationBar get() = NavigationBar.Normal("Input phone".desc())

    private val openUrlDialogHandler by registerDialogButtonsHandler(
        onPositivePressed = {
            showToast("positive from $it".desc())
        },
        onNegativePressed = {
            showToast("negative from $it".desc())
        }
    )

    private val phonePickerHandler by registerPhonePickerHandler(9) {
        showToast("picked $it".desc())
    }

    override fun createContentWidget() = with(theme) {
        val viewModel = getViewModel {
            viewModelFactory(createEventsDispatcher())
        }

        viewModel.eventsDispatcher.listen(this@InputPhoneScreen, this@InputPhoneScreen)

        constraint(size = WidgetSize.AsParent) {
            val nameInput = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = Ids.Name,
                label = const("Name"),
                field = viewModel.phoneField
            )

            val submitButton = +button(
                category = submitButtons,
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Submit".desc())),
                onTap = viewModel::onSubmitPressed
            )

            val githubButton = +button(
                size = WidgetSize.WrapContent,
                content = ButtonWidget.Content.Text(Value.data("GitHub".desc())),
                onTap = ::onGitHubPressed
            )

            val aboutButton = +button(
                size = WidgetSize.WrapContent,
                content = ButtonWidget.Content.Text(Value.data("About".desc())),
                onTap = ::onAboutPressed
            )

            constraints {
                nameInput centerYToCenterY root
                nameInput leftRightToLeftRight root offset 16

                submitButton bottomToBottom root.safeArea offset 16
                submitButton leftRightToLeftRight root offset 16

                githubButton rightToRight root offset 16
                githubButton topToTop root.safeArea offset 16

                aboutButton rightToLeft githubButton offset 8
                aboutButton topToTop githubButton
            }
        }
    }

    private fun onGitHubPressed() {
        showPhonePicker(phonePickerHandler)
    }

    private fun onAboutPressed() {
        showDialog(
            dialogId = 2,
            title = "Question 2".desc(),
            message = "No or yes?".desc(),
            positiveButton = "No".desc(),
            negativeButton = "Yes".desc(),
            buttonsHandler = openUrlDialogHandler
        )
    }
    override fun routeInputCode(token: String) {
        routeInputCode.route(token)
    }

    override fun showError(error: StringDesc) {
        showToast(error)
    }

    object Ids {
        object Name : InputWidget.Id
    }
}

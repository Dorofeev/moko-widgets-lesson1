package org.example.mpp.auth

import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
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

class InputCodeScreen(
    private val theme: Theme,
    private val submitButtons: ButtonWidget.Category,
    private val viewModelFactory: (
        eventsDispatcher: EventsDispatcher<InputCodeViewModel.EventsListener>,
        token: String
    ) -> InputCodeViewModel,
    private val routeMain: Route<Unit>
) : WidgetScreen<Args.Parcel<InputCodeScreen.Arg>>(), InputCodeViewModel.EventsListener, NavigationItem {

    override val navigationBar: NavigationBar get() = NavigationBar.Normal("Input code".desc())

    override fun createContentWidget() = with(theme) {
        val viewModel = getViewModel {
            viewModelFactory(createEventsDispatcher(), getArgument().token)
        }

        viewModel.eventsDispatcher.listen(this@InputCodeScreen, this@InputCodeScreen)


        constraint(size = WidgetSize.AsParent) {
            val codeInput = +input(
                size = WidgetSize.WidthAsParentHeightWrapContent,
                id = InputCodeScreen.Ids.Code,
                label = const("Code"),
                field = viewModel.codeField
            )

            val submitButton = +button(
                category = submitButtons,
                size = WidgetSize.WidthAsParentHeightWrapContent,
                content = ButtonWidget.Content.Text(Value.data("Submit".desc())),
                onTap = viewModel::onSubmitPressed
            )

            constraints {
                codeInput centerYToCenterY root
                codeInput leftRightToLeftRight root offset 16

                submitButton bottomToBottom root.safeArea offset 16
                submitButton leftRightToLeftRight root offset 16
            }
        }
    }

    object Ids {
        object Code : InputWidget.Id
    }

    override fun routeMain() {
        routeMain.route()
    }

    @Parcelize
    data class Arg(val token: String) : Parcelable
}
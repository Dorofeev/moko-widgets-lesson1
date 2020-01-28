package org.example.mpp.auth

import dev.icerock.moko.fields.FormField
import dev.icerock.moko.fields.liveBlock
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcherOwner
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.StringDesc

class InputCodeViewModel(
    override val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val token: String
) : ViewModel(), EventsDispatcherOwner<InputCodeViewModel.EventsListener> {

    val codeField: FormField<String, StringDesc> = FormField(
        initialValue = token,
        validation = liveBlock { null }
    )

    fun onSubmitPressed() {
        eventsDispatcher.dispatchEvent { routeMain() }
    }

    interface EventsListener {
        fun routeMain()
    }
}
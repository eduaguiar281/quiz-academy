package io.arcotech.quizAcademy.services.featuretoggles.timewindowfilter

import io.arcotech.quizAcademy.services.featuretoggles.defaultfilter.DefaultFeatureConfiguration
import java.time.LocalDateTime

class TimeWindowFeatureConfiguration(
    enabled: Boolean,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime) : DefaultFeatureConfiguration(enabled) {
    constructor(): this (false, LocalDateTime.now(), LocalDateTime.now().plusSeconds(1))
}
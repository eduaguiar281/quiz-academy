package io.arcotech.quizAcademy.services.featuretoggles.timewindowfilter

import io.arcotech.quizAcademy.services.featuretoggles.defaultfilter.DefaultFeatureFilter
import java.time.LocalDateTime

class TimeWindowFeatureFilter(featureName:String) :DefaultFeatureFilter(featureName, "") {

    private var configuration: TimeWindowFeatureConfiguration? = null

    override fun evaluate(): Boolean {
        configuration = readConfiguration(TimeWindowFeatureConfiguration::class.java)
        val now = LocalDateTime.now()
        return if (configuration == null)
            false
        else
            return configuration!!.enabled && (now >= configuration!!.startDateTime && now <= configuration!!.endDateTime)
    }
}
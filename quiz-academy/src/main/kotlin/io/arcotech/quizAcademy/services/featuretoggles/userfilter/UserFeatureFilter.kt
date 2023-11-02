package io.arcotech.quizAcademy.services.featuretoggles.userfilter

import io.arcotech.quizAcademy.services.featuretoggles.defaultfilter.DefaultFeatureFilter

class UserFeatureFilter (featureName: String, private val userId: Int) : DefaultFeatureFilter(featureName, "") {

    private var configuration: UserFilterConfiguration? = null

    override fun evaluate(): Boolean{
        configuration = readConfiguration(UserFilterConfiguration::class.java)
        return if (configuration == null)
            false
        else
            return configuration!!.enabled && configuration!!.users.contains(userId)
    }

}
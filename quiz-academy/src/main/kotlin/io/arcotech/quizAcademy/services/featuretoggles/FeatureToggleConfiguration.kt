package io.arcotech.quizAcademy.services.featuretoggles

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("feature-toggles")
class FeatureToggleConfiguration {

    val configuration = FeatureApplicationConfiguration()

    fun getKey(): String{
        return "${configuration.application}-${configuration.enviroment}-${configuration.profile}"
    }
    class FeatureApplicationConfiguration{
        var application: String? = null
        var enviroment: String? = null
        var profile: String? = null
    }
}
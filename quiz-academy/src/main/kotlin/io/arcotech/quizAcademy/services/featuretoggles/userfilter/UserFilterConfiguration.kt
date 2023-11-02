package io.arcotech.quizAcademy.services.featuretoggles.userfilter

import io.arcotech.quizAcademy.services.featuretoggles.defaultfilter.DefaultFeatureConfiguration


class UserFilterConfiguration constructor (enabled: Boolean, val users: ArrayList<Int>) : DefaultFeatureConfiguration(enabled) {
    constructor(): this (false, arrayListOf())
}
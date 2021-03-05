package tw.north27.coachingapp.base

import tw.north27.coachingapp.consts.modelModules
import tw.north27.coachingapp.consts.repoModules
import tw.north27.coachingapp.consts.utilModules
import tw.north27.coachingapp.consts.viewModules
import tw.north27.coachingapp.ext.startKoinModules
import tw.north27.coachingapp.firebase.FcmFirebaseMessagingService
import tw.north27.coachingapp.module.base.application.BaseApplication

class BaseCoachingApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoinModules(utilModules, modelModules, repoModules, viewModules)

    }

}
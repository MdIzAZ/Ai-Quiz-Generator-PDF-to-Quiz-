package org.example.app.ai.quiz.knowledge.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    config: KoinAppDeclaration? = null
) {
    startKoin {

        config?.invoke(this)
//        logger()
        modules(
            platformModule,
            sharedModule
        )
    }
}
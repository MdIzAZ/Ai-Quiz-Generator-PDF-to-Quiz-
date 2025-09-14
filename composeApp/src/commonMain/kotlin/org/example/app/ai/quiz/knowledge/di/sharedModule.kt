package org.example.app.ai.quiz.knowledge.di

import org.example.app.ai.quiz.knowledge.data.quiz_generator.QuizGenerator
import org.example.app.ai.quiz.knowledge.data.remote.createHttpClient
import org.example.app.ai.quiz.knowledge.data.repo.QuizRepoImp
import org.example.app.ai.quiz.knowledge.domain.repo.QuizRepo
import org.example.app.ai.quiz.knowledge.presentation.home_screen.HomeViewModel
import org.example.app.ai.quiz.knowledge.presentation.quiz_screen.QuizViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {


    //ktor
    single {
        createHttpClient()
    }

    singleOf(::QuizGenerator)

    //viewmodel
    viewModelOf(::HomeViewModel)
    viewModelOf(::QuizViewModel)

    //repository
    singleOf(::QuizRepoImp).bind<QuizRepo>()

}
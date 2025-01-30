package com.dreamsoftware.chicfit.di

import android.content.Context
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.chicfit.ui.screens.account.signin.SignInScreenSimpleErrorMapper
import com.dreamsoftware.chicfit.ui.screens.account.signup.SignUpScreenSimpleErrorMapper
import com.dreamsoftware.chicfit.ui.screens.chat.ChatSimpleErrorMapper
import com.dreamsoftware.chicfit.ui.screens.detail.ArtworkDetailScreenSimpleErrorMapper
import com.dreamsoftware.chicfit.ui.screens.home.HomeSimpleErrorMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    @Provides
    @ViewModelScoped
    @SignUpScreenErrorMapper
    fun provideSignUpScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        SignUpScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @SignInScreenErrorMapper
    fun provideSignInScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        SignInScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @HomeErrorMapper
    fun provideHomeSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        HomeSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @ChatErrorMapper
    fun provideChatSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        ChatSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @ArtworkDetailErrorMapper
    fun provideArtworkDetailErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        ArtworkDetailScreenSimpleErrorMapper(context = context)


    @Provides
    @ViewModelScoped
    @CreateArtworkErrorMapper
    fun provideCreateArtworkScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IBrownieErrorMapper =
        ArtworkDetailScreenSimpleErrorMapper(context = context)
}

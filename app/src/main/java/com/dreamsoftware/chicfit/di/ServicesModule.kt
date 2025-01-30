package com.dreamsoftware.chicfit.di

import android.content.Context
import com.dreamsoftware.chicfit.domain.service.ISoundPlayerService
import com.dreamsoftware.chicfit.domain.service.ITTSService
import com.dreamsoftware.chicfit.domain.service.ITranscriptionService
import com.dreamsoftware.chicfit.framework.sound.SoundPlayerServiceImpl
import com.dreamsoftware.chicfit.framework.transcription.TranscriptionServiceImpl
import com.dreamsoftware.chicfit.framework.tts.TTSServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {

    @Provides
    @Singleton
    fun provideTranscriptionService(@ApplicationContext context: Context): ITranscriptionService = TranscriptionServiceImpl(context)

    @Provides
    @Singleton
    fun provideTextToSpeechService(@ApplicationContext context: Context): ITTSService = TTSServiceImpl(context)

    @Provides
    @Singleton
    fun provideSoundPlayerService(@ApplicationContext context: Context): ISoundPlayerService = SoundPlayerServiceImpl(context)
}
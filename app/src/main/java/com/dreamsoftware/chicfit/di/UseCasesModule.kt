package com.dreamsoftware.chicfit.di

import com.dreamsoftware.chicfit.domain.model.AuthRequestBO
import com.dreamsoftware.chicfit.domain.model.SignUpBO
import com.dreamsoftware.chicfit.domain.repository.IImageRepository
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IMultiModalLanguageModelRepository
import com.dreamsoftware.chicfit.domain.repository.IPreferenceRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository
import com.dreamsoftware.chicfit.domain.service.ISoundPlayerService
import com.dreamsoftware.chicfit.domain.service.ITTSService
import com.dreamsoftware.chicfit.domain.service.ITranscriptionService
import com.dreamsoftware.chicfit.domain.usecase.AddOutfitMessageUseCase
import com.dreamsoftware.chicfit.domain.usecase.CreateArtworkUseCase
import com.dreamsoftware.chicfit.domain.usecase.DeleteArtworkByIdUseCase
import com.dreamsoftware.chicfit.domain.usecase.TranscribeUserQuestionUseCase
import com.dreamsoftware.chicfit.domain.usecase.EndUserSpeechCaptureUseCase
import com.dreamsoftware.chicfit.domain.usecase.GetAllArtworksByUserUseCase
import com.dreamsoftware.chicfit.domain.usecase.GetAssistantMutedStatusUseCase
import com.dreamsoftware.chicfit.domain.usecase.GetAuthenticateUserDetailUseCase
import com.dreamsoftware.chicfit.domain.usecase.GetArtworkByIdUseCase
import com.dreamsoftware.chicfit.domain.usecase.SearchArtworkUseCase
import com.dreamsoftware.chicfit.domain.usecase.SignInUseCase
import com.dreamsoftware.chicfit.domain.usecase.SignOffUseCase
import com.dreamsoftware.chicfit.domain.usecase.SignUpUseCase
import com.dreamsoftware.chicfit.domain.usecase.StopTextToSpeechUseCase
import com.dreamsoftware.chicfit.domain.usecase.TextToSpeechUseCase
import com.dreamsoftware.chicfit.domain.usecase.UpdateAssistantMutedStatusUseCase
import com.dreamsoftware.chicfit.domain.usecase.VerifyUserSessionUseCase
import com.dreamsoftware.chicfit.domain.validation.IBusinessEntityValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideVerifyUserSessionUseCase(
        userRepository: IUserRepository
    ): VerifyUserSessionUseCase =
        VerifyUserSessionUseCase(userRepository)

    @Provides
    @ViewModelScoped
    fun provideTranscribeUserQuestionUseCase(
        transcriptionService: ITranscriptionService
    ): TranscribeUserQuestionUseCase =
        TranscribeUserQuestionUseCase(transcriptionService)

    @Provides
    @ViewModelScoped
    fun provideEndUserSpeechCaptureUseCase(
        transcriptionService: ITranscriptionService
    ): EndUserSpeechCaptureUseCase =
        EndUserSpeechCaptureUseCase(transcriptionService)

    @Provides
    @ViewModelScoped
    fun provideSignInUseCase(
        userRepository: IUserRepository,
        preferenceRepository: IPreferenceRepository,
        validator: IBusinessEntityValidator<AuthRequestBO>
    ): SignInUseCase =
        SignInUseCase(
            userRepository = userRepository,
            preferenceRepository = preferenceRepository,
            validator = validator
        )

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(
        preferenceRepository: IPreferenceRepository,
        userRepository: IUserRepository,
        validator: IBusinessEntityValidator<SignUpBO>
    ): SignUpUseCase =
        SignUpUseCase(
            userRepository = userRepository,
            preferenceRepository = preferenceRepository,
            validator = validator
        )

    @Provides
    @ViewModelScoped
    fun provideSignOffUseCase(
        userRepository: IUserRepository,
        preferenceRepository: IPreferenceRepository,
    ): SignOffUseCase =
        SignOffUseCase(
            userRepository = userRepository,
            preferenceRepository = preferenceRepository
        )

    @Provides
    @ViewModelScoped
    fun provideCreateArtworkUseCase(
        userRepository: IUserRepository,
        imageRepository: IImageRepository,
        artworkRepository: IOutfitRepository,
        multiModalLanguageModelRepository: IMultiModalLanguageModelRepository
    ): CreateArtworkUseCase =
        CreateArtworkUseCase(
            userRepository = userRepository,
            imageRepository = imageRepository,
            outfitRepository = artworkRepository,
            multiModalLanguageModelRepository = multiModalLanguageModelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideDeleteArtworkByIdUseCase(
        userRepository: IUserRepository,
        imageRepository: IImageRepository,
        artworkRepository: IOutfitRepository
    ): DeleteArtworkByIdUseCase =
        DeleteArtworkByIdUseCase(
            userRepository = userRepository,
            imageRepository = imageRepository,
            artworkRepository = artworkRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetAllArtworkByUserUseCase(
        userRepository: IUserRepository,
        artworkRepository: IOutfitRepository
    ): GetAllArtworksByUserUseCase =
        GetAllArtworksByUserUseCase(
            userRepository = userRepository,
            artworkRepository = artworkRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetAuthenticateUserDetailUseCase(
        userRepository: IUserRepository
    ): GetAuthenticateUserDetailUseCase =
        GetAuthenticateUserDetailUseCase(
            userRepository = userRepository,
        )

    @Provides
    @ViewModelScoped
    fun provideGetArtworkByIdUseCase(
        userRepository: IUserRepository,
        artworkRepository: IOutfitRepository
    ): GetArtworkByIdUseCase =
        GetArtworkByIdUseCase(
            userRepository = userRepository,
            artworkRepository = artworkRepository
        )

    @Provides
    @ViewModelScoped
    fun provideTextToSpeechUseCase(
        ttsService: ITTSService
    ): TextToSpeechUseCase =
        TextToSpeechUseCase(
            ttsService = ttsService
        )

    @Provides
    @ViewModelScoped
    fun provideAddArtworkQuestionUseCase(
        userRepository: IUserRepository,
        artworkRepository: IOutfitRepository,
        multiModalLanguageModelRepository: IMultiModalLanguageModelRepository
    ): AddOutfitMessageUseCase =
        AddOutfitMessageUseCase(
            userRepository = userRepository,
            outfitRepository = artworkRepository,
            multiModalLanguageModelRepository = multiModalLanguageModelRepository
        )


    @Provides
    @ViewModelScoped
    fun provideStopTextToSpeechUseCase(
        ttsService: ITTSService
    ): StopTextToSpeechUseCase =
        StopTextToSpeechUseCase(
            ttsService = ttsService
        )


    @Provides
    @ViewModelScoped
    fun provideUpdateAssistantMutedStatusUseCase(
        preferencesRepository: IPreferenceRepository,
        soundPlayerService: ISoundPlayerService
    ): UpdateAssistantMutedStatusUseCase =
        UpdateAssistantMutedStatusUseCase(
            preferencesRepository = preferencesRepository,
            soundPlayerService = soundPlayerService
        )

    @Provides
    @ViewModelScoped
    fun provideGetAssistantMutedStatusUseCase(
        preferencesRepository: IPreferenceRepository
    ): GetAssistantMutedStatusUseCase =
        GetAssistantMutedStatusUseCase(
            preferencesRepository = preferencesRepository
        )

    @Provides
    @ViewModelScoped
    fun provideSearchArtworkUseCase(
        userRepository: IUserRepository,
        artworkRepository: IOutfitRepository,
    ): SearchArtworkUseCase =
        SearchArtworkUseCase(
            userRepository = userRepository,
            artworkRepository = artworkRepository
        )
}

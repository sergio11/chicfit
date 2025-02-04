package com.dreamsoftware.chicfit.di

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.local.preferences.datasource.IPreferencesDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.IAuthRemoteDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.IImageDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.IOutfitDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.IMultiModalLanguageModelDataSource
import com.dreamsoftware.chicfit.data.remote.dto.AddMessageDTO
import com.dreamsoftware.chicfit.data.remote.dto.AuthUserDTO
import com.dreamsoftware.chicfit.data.remote.dto.OutfitDTO
import com.dreamsoftware.chicfit.data.remote.dto.ResolveQuestionDTO
import com.dreamsoftware.chicfit.data.remote.dto.CreateOutfitDTO
import com.dreamsoftware.chicfit.data.repository.impl.IMultiModalLanguageModelRepositoryImpl
import com.dreamsoftware.chicfit.data.repository.impl.ImageRepositoryImpl
import com.dreamsoftware.chicfit.data.repository.impl.OutfitRepositoryImpl
import com.dreamsoftware.chicfit.data.repository.impl.PreferenceRepositoryImpl
import com.dreamsoftware.chicfit.data.repository.impl.UserRepositoryImpl
import com.dreamsoftware.chicfit.data.repository.mapper.AddOutfitMessageMapper
import com.dreamsoftware.chicfit.data.repository.mapper.AuthUserMapper
import com.dreamsoftware.chicfit.data.repository.mapper.OutfitMapper
import com.dreamsoftware.chicfit.data.repository.mapper.ResolveQuestionMapper
import com.dreamsoftware.chicfit.data.repository.mapper.CreateOutfitMapper
import com.dreamsoftware.chicfit.domain.model.AddMessageBO
import com.dreamsoftware.chicfit.domain.model.AuthUserBO
import com.dreamsoftware.chicfit.domain.model.OutfitBO
import com.dreamsoftware.chicfit.domain.model.ResolveQuestionBO
import com.dreamsoftware.chicfit.domain.model.CreateOutfitBO
import com.dreamsoftware.chicfit.domain.repository.IImageRepository
import com.dreamsoftware.chicfit.domain.repository.IOutfitRepository
import com.dreamsoftware.chicfit.domain.repository.IMultiModalLanguageModelRepository
import com.dreamsoftware.chicfit.domain.repository.IPreferenceRepository
import com.dreamsoftware.chicfit.domain.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthUserMapper(): IBrownieOneSideMapper<AuthUserDTO, AuthUserBO> = AuthUserMapper()

    @Provides
    @Singleton
    fun provideOutfitMapper(): IBrownieOneSideMapper<OutfitDTO, OutfitBO> = OutfitMapper()

    @Provides
    @Singleton
    fun provideSaveOutfitMapper(): IBrownieOneSideMapper<CreateOutfitBO, CreateOutfitDTO> = CreateOutfitMapper()

    @Provides
    @Singleton
    fun provideResolveQuestionMapper(): IBrownieOneSideMapper<ResolveQuestionBO, ResolveQuestionDTO> = ResolveQuestionMapper()

    @Provides
    @Singleton
    fun provideAddOutfitMessageMapper(): IBrownieOneSideMapper<AddMessageBO, AddMessageDTO> = AddOutfitMessageMapper()

    @Provides
    @Singleton
    fun provideUserRepository(
        authDataSource: IAuthRemoteDataSource,
        authUserMapper: IBrownieOneSideMapper<AuthUserDTO, AuthUserBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IUserRepository =
        UserRepositoryImpl(
            authDataSource,
            authUserMapper,
            dispatcher
        )

    @Provides
    @Singleton
    fun providePreferenceRepository(
        preferenceDataSource: IPreferencesDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IPreferenceRepository =
        PreferenceRepositoryImpl(
            preferenceDataSource,
            dispatcher
        )

    @Provides
    @Singleton
    fun provideOutfitRepository(
        outfitDataSource: IOutfitDataSource,
        saveOutfitMapper: IBrownieOneSideMapper<CreateOutfitBO, CreateOutfitDTO>,
        addOutfitMapper: IBrownieOneSideMapper<AddMessageBO, AddMessageDTO>,
        outfitMapper: IBrownieOneSideMapper<OutfitDTO, OutfitBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IOutfitRepository =
        OutfitRepositoryImpl(
            outfitDataSource,
            saveOutfitMapper,
            addOutfitMapper,
            outfitMapper,
            dispatcher
        )

    @Provides
    @Singleton
    fun provideMultiModalLanguageModelRepository(
        multiModalLanguageModelDataSource: IMultiModalLanguageModelDataSource,
        resolveQuestionMapper: IBrownieOneSideMapper<ResolveQuestionBO, ResolveQuestionDTO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IMultiModalLanguageModelRepository =
        IMultiModalLanguageModelRepositoryImpl(
            multiModalLanguageModelDataSource,
            resolveQuestionMapper,
            dispatcher
        )

    @Provides
    @Singleton
    fun provideImageRepository(
        imageDataSource: IImageDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IImageRepository =
        ImageRepositoryImpl(
            imageDataSource,
            dispatcher
        )
}

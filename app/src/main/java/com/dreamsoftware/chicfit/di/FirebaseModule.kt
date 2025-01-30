package com.dreamsoftware.chicfit.di

import com.dreamsoftware.brownie.utils.IBrownieOneSideMapper
import com.dreamsoftware.chicfit.data.remote.datasource.IAuthRemoteDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.IImageDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.IArtworkDataSource
import com.dreamsoftware.chicfit.data.remote.datasource.impl.AuthRemoteDataSourceImpl
import com.dreamsoftware.chicfit.data.remote.datasource.impl.ImageDataSourceImpl
import com.dreamsoftware.chicfit.data.remote.datasource.impl.ArtworkDataSourceImpl
import com.dreamsoftware.chicfit.data.remote.dto.AddArtworkMessageDTO
import com.dreamsoftware.chicfit.data.remote.dto.AuthUserDTO
import com.dreamsoftware.chicfit.data.remote.dto.CreateArtworkDTO
import com.dreamsoftware.chicfit.data.remote.dto.ArtworkDTO
import com.dreamsoftware.chicfit.data.remote.mapper.AddArtworkMessageRemoteMapper
import com.dreamsoftware.chicfit.data.remote.mapper.CreateArtworkRemoteMapper
import com.dreamsoftware.chicfit.data.remote.mapper.UserAuthenticatedMapper
import com.dreamsoftware.chicfit.data.remote.mapper.ArtworkRemoteMapper
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

// Dagger module for providing Firebase-related dependencies
@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    /**
     * Provides a singleton instance of UserAuthenticatedMapper.
     * @return a new instance of UserAuthenticatedMapper.
     */
    @Provides
    @Singleton
    fun provideUserAuthenticatedMapper(): IBrownieOneSideMapper<FirebaseUser, AuthUserDTO> = UserAuthenticatedMapper()

    @Provides
    @Singleton
    fun provideUserQuestionRemoteMapper(): IBrownieOneSideMapper<Map<String, Any?>, ArtworkDTO> = ArtworkRemoteMapper()

    @Provides
    @Singleton
    fun provideSaveUserQuestionRemoteMapper(): IBrownieOneSideMapper<CreateArtworkDTO, Map<String, Any?>> = CreateArtworkRemoteMapper()

    @Provides
    @Singleton
    fun provideAddArtworkMessageRemoteMapper(): IBrownieOneSideMapper<AddArtworkMessageDTO, List<Map<String, String>>> = AddArtworkMessageRemoteMapper()

    /**
     * Provides a singleton instance of FirebaseAuth.
     * @return the default instance of FirebaseAuth.
     */
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Provide Firebase Store
     */
    @Provides
    @Singleton
    fun provideFirebaseStore() = Firebase.firestore

    /**
     * Provide Firebase Storage
     */
    @Provides
    @Singleton
    fun provideFirebaseStorage() = Firebase.storage

    /**
     * Provides a singleton instance of IAuthDataSource.
     * @param userAuthenticatedMapper the IBrownieOneSideMapper<FirebaseUser, AuthUserDTO> instance.
     * @param firebaseAuth the FirebaseAuth instance.
     * @return a new instance of AuthDataSourceImpl implementing IAuthDataSource.
     */
    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        userAuthenticatedMapper: IBrownieOneSideMapper<FirebaseUser, AuthUserDTO>,
        firebaseAuth: FirebaseAuth,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IAuthRemoteDataSource = AuthRemoteDataSourceImpl(
        userAuthenticatedMapper,
        firebaseAuth,
        dispatcher
    )

    @Provides
    @Singleton
    fun provideUserPicturesDataSource(
        storage: FirebaseStorage,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IImageDataSource = ImageDataSourceImpl(
        storage,
        dispatcher
    )

    @Provides
    @Singleton
    fun provideArtworkDataSource(
        firestore: FirebaseFirestore,
        saveUserQuestionMapper: IBrownieOneSideMapper<CreateArtworkDTO, Map<String, Any?>>,
        addArtworkMessageMapper: IBrownieOneSideMapper<AddArtworkMessageDTO, List<Map<String, String>>>,
        userQuestionMapper: IBrownieOneSideMapper<Map<String, Any?>, ArtworkDTO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IArtworkDataSource = ArtworkDataSourceImpl(
        firestore,
        saveUserQuestionMapper,
        addArtworkMessageMapper,
        userQuestionMapper,
        dispatcher
    )
}
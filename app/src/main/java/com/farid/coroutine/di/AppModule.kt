package com.farid.coroutine.di

import android.content.Context
import com.farid.coroutine.data.local.AppDatabase
import com.farid.coroutine.data.local.MoviesDao
import com.farid.coroutine.data.remote.ApiService
import com.farid.coroutine.data.remote.RemoteDataSource
import com.farid.coroutine.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://www.omdbapi.com"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideHttpClient(logging: HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(1,TimeUnit.MINUTES)
            .callTimeout(1,TimeUnit.MINUTES)
            .writeTimeout(1,TimeUnit.MINUTES)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService) : RemoteDataSource = RemoteDataSource(apiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) : AppDatabase = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) : MoviesDao = db.moviesDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource,
                          localDataSource: MoviesDao) =
        Repository(remoteDataSource, localDataSource)

}
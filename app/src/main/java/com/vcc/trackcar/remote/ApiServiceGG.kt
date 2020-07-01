package com.vcc.trackcar.remote

import com.google.gson.GsonBuilder
import com.vcc.trackcar.model.PlacesResults
import com.vcc.trackcar.model.addBookCar.AddBookCarBody
import com.vcc.trackcar.model.addBookCar.AddBookCarRespon
import com.vcc.trackcar.model.api_matrix.MatrixRespon
import com.vcc.trackcar.model.auth.AuthBody
import com.vcc.trackcar.model.auth.AuthRespon
import com.vcc.trackcar.model.captainCarApproveRejectBookCar.CaptainCarApproveRejectBookCarBody
import com.vcc.trackcar.model.captainCarApproveRejectBookCar.CaptainCarApproveRejectBookCarRespon
import com.vcc.trackcar.model.closeBookCar.CloseBookCarBody
import com.vcc.trackcar.model.closeBookCar.CloseBookCarRespon
import com.vcc.trackcar.model.getListBookCar.GetListBookCarBody
import com.vcc.trackcar.model.getListBookCar.GetListBookCarRespon
import com.vcc.trackcar.model.getListDriverCar.GetListDriverCarBody
import com.vcc.trackcar.model.getListDriverCar.GetListDriverCarRespon
import com.vcc.trackcar.model.getListTypeCar.GetListTypeCarRespon
import com.vcc.trackcar.model.getListUser.GetListUserRespon
import com.vcc.trackcar.model.manageApproveRejectBookCar.ManageApproveRejectBookCarBody
import com.vcc.trackcar.model.manageApproveRejectBookCar.ManageApproveRejectBookCarRespon
import com.vcc.trackcar.model.managerCarApproveRejectBookCar.ManagerCarApproveRejectBookCarBody
import com.vcc.trackcar.model.managerCarApproveRejectBookCar.ManagerCarApproveRejectBookCarRespon
import com.vcc.trackcar.model.updateBookCar.UpdateBookCarBody
import com.vcc.trackcar.model.updateBookCar.UpdateBookCarRespon
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

object APIGG {
    const val BASE_URL = "https://maps.googleapis.com/"

    val service: AppRepository by lazy {
        val gson = GsonBuilder().setLenient().create()
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(makeOkHttpClient(true)).build().create<AppRepository>(AppRepository::class.java)
    }

    private fun makeOkHttpClient(isDebug: Boolean): OkHttpClient? {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain: Interceptor.Chain ->
                val request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")
                        .build()
                chain.proceed(request)
            }.addInterceptor(logging).build()
    }

    interface AppRepository {

        @GET("maps/api/distancematrix/json")
        fun getDistanceInfo(@QueryMap parameters: Map<String, String>): Single<MatrixRespon>


        //        @FormUrlEncoded
//        @POST("auth")
//        fun auth(@Field("username") username: String, @Field("password") password: String): Observable<UserProfile>
//
//        @GET("category")
//        fun getCategory(): Observable<String>

        @GET("/maps/api/directions/json?mode=driving&transit_routing_preference=less_driving")
        fun getDataFromGoogleApi(
            @Query("origin") origin: String?, @Query("destination") destination: String?, @Query("key") key: String?
        ): Single<PlacesResults>

        @POST("SysUserRestService/service/auth")
        fun auth(@Body authBody: AuthBody): Single<AuthRespon>

    }
}
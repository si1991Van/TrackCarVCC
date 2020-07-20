package com.vcc.trackcar.remote

import com.google.gson.GsonBuilder
import com.vcc.trackcar.model.CatVehicleReponseDTO
import com.vcc.trackcar.model.PlacesResults
import com.vcc.trackcar.model.addBookCar.AddBookCarBody
import com.vcc.trackcar.model.addBookCar.AddBookCarRespon
import com.vcc.trackcar.model.administrativeApproveRejectBookCar.AdministrativeApproveRejectBookCarBody
import com.vcc.trackcar.model.administrativeApproveRejectBookCar.AdministrativeApproveRejectBookCarRespon
import com.vcc.trackcar.model.auth.AuthBody
import com.vcc.trackcar.model.auth.AuthRespon
import com.vcc.trackcar.model.auth.ResultInfo
import com.vcc.trackcar.model.captainCarApproveRejectBookCar.CaptainCarApproveRejectBookCarBody
import com.vcc.trackcar.model.captainCarApproveRejectBookCar.CaptainCarApproveRejectBookCarRespon
import com.vcc.trackcar.model.closeBookCar.CloseBookCarBody
import com.vcc.trackcar.model.closeBookCar.CloseBookCarRespon
import com.vcc.trackcar.model.closeDriverBookCar.CloseDriverBookCarBody
import com.vcc.trackcar.model.closeDriverBookCar.CloseDriverBookCarRespon
import com.vcc.trackcar.model.closeManagerBookCar.CloseManagerBookCarBody
import com.vcc.trackcar.model.closeManagerBookCar.CloseManagerBookCarRespon
import com.vcc.trackcar.model.driverBoardApproveRejectBookCar.DriverBoardApproveRejectBookCarBody
import com.vcc.trackcar.model.driverBoardApproveRejectBookCar.DriverBoardApproveRejectBookCarRespon
import com.vcc.trackcar.model.getAppVersion.GetAppVersionRespon
import com.vcc.trackcar.model.getBookCarToken.GetBookCarTokenBody
import com.vcc.trackcar.model.getBookCarToken.GetBookCarTokenRespon
import com.vcc.trackcar.model.getDataDistrict.GetDataDistrictBody
import com.vcc.trackcar.model.getDataDistrict.GetDataDistrictRespon
import com.vcc.trackcar.model.getDataProvinceCity.GetDataProvinceCityRespon
import com.vcc.trackcar.model.getDataWard.GetDataWardBody
import com.vcc.trackcar.model.getDataWard.GetDataWardRespon
import com.vcc.trackcar.model.getHistoryCar.GetHistoryCarBody
import com.vcc.trackcar.model.getHistoryCar.GetHistoryCarRespon
import com.vcc.trackcar.model.getHistoryDetailCar.GetHistoryDetailCarBody
import com.vcc.trackcar.model.getHistoryDetailCar.GetHistoryDetailCarRespon
import com.vcc.trackcar.model.getListBookCar.GetListBookCarBody
import com.vcc.trackcar.model.getListBookCar.GetListBookCarRespon
import com.vcc.trackcar.model.getListCar.GetListCarBody
import com.vcc.trackcar.model.getListCar.GetListCarRespon
import com.vcc.trackcar.model.getListDriverCar.GetListDriverCarBody
import com.vcc.trackcar.model.getListDriverCar.GetListDriverCarRespon
import com.vcc.trackcar.model.getListManager.GetListManagerBody
import com.vcc.trackcar.model.getListManager.GetListManagerRespon
import com.vcc.trackcar.model.getListTypeCar.GetListTypeCarRespon
import com.vcc.trackcar.model.getListUser.GetListUserBody
import com.vcc.trackcar.model.getListUser.GetListUserRespon
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherBody
import com.vcc.trackcar.model.getListUserTogether.GetListUserTogetherRespon
import com.vcc.trackcar.model.manageApproveRejectBookCar.ManageApproveRejectBookCarBody
import com.vcc.trackcar.model.manageApproveRejectBookCar.ManageApproveRejectBookCarRespon
import com.vcc.trackcar.model.managerCarApproveRejectBookCar.ManagerCarApproveRejectBookCarBody
import com.vcc.trackcar.model.managerCarApproveRejectBookCar.ManagerCarApproveRejectBookCarRespon
import com.vcc.trackcar.model.request_body.BranchRequestBody
import com.vcc.trackcar.model.request_body.VehicleMonitoringRequestBody
import com.vcc.trackcar.model.response.BranchReponseDTO
import com.vcc.trackcar.model.response.TypeCarTruckReponseDTO
import com.vcc.trackcar.model.updateBookCar.UpdateBookCarBody
import com.vcc.trackcar.model.updateBookCar.UpdateBookCarRespon
import com.vcc.trackcar.model.updateLocation.UpdateLocationBody
import com.vcc.trackcar.model.updateLocation.UpdateLocationRespon
import com.vcc.trackcar.model.updateTokenUser.UpdateTokenUserBody
import com.vcc.trackcar.model.updateTokenUser.UpdateTokenUserRespon
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object API {
//        const val BASE_URL = "http://10.61.19.230:8866/oto-service/service/"
//        const val BASE_URL = "http://10.61.18.229:8084/oto-service/service/"
        const val BASE_URL = "http://192.168.1.100:8581/oto-service/service/"
//    const val BASE_URL = "https://qlotomobile.congtrinhviettel.com.vn/oto-service/service/"

    val service: AppRepository by lazy {
        val gson = GsonBuilder().setLenient().create()
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(makeOkHttpClient(true)).build().create<AppRepository>(AppRepository::class.java)
    }

    private fun makeOkHttpClient(isDebug: Boolean): OkHttpClient? {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>, authType: String
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>, authType: String
                ) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory = sslContext.socketFactory


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
                }.addInterceptor(logging)
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    interface AppRepository {
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

        @POST("BookCarRestService/service/getListTypeCar")
        fun getListTypeCar(): Single<GetListTypeCarRespon>

        @POST("BookCarRestService/service/getListUser")
        fun getListUser(@Body getListUserBody: GetListUserBody): Single<GetListUserRespon>

        @POST("BookCarRestService/service/addBookCar")
        fun addBookCar(@Body addBookCarBody: AddBookCarBody): Single<AddBookCarRespon>

        @POST("BookCarRestService/service/getListBookCar")
        fun getListBookCar(@Body getListBookCarBody: GetListBookCarBody): Single<GetListBookCarRespon>

        @POST("BookCarRestService/service/manageApproveRejectBookCar")
        fun manageApproveRejectBookCar(@Body managerApproveRejectBookCarBody: ManageApproveRejectBookCarBody): Single<ManageApproveRejectBookCarRespon>

        @POST("BookCarRestService/service/captainCarApproveRejectBookCar")
        fun captainCarApproveRejectBookCar(@Body captainCarApproveRejectBookCarBody: CaptainCarApproveRejectBookCarBody): Single<CaptainCarApproveRejectBookCarRespon>

        @POST("BookCarRestService/service/getListDriverCar")
        fun getListDriverCar(@Body getListDriverCarBody: GetListDriverCarBody): Single<GetListDriverCarRespon>

        @POST("BookCarRestService/service/getListCar")
        fun getListCar(@Body getListCarBody: GetListCarBody): Single<GetListCarRespon>

        @POST("BookCarRestService/service/managerCarApproveRejectBookCar")
        fun managerCarApproveRejectBookCar(@Body managerCarApproveRejectBookCarBody: ManagerCarApproveRejectBookCarBody): Single<ManagerCarApproveRejectBookCarRespon>

        @POST("BookCarRestService/service/updateBookCar")
        fun updateBookCar(@Body updateBookCarBody: UpdateBookCarBody): Single<UpdateBookCarRespon>

        @POST("BookCarRestService/service/closeBookCar")
        fun closeBookCar(@Body closeBookCarBody: CloseBookCarBody): Single<CloseBookCarRespon>

        @POST("BookCarRestService/service/closeDriverBookCar")
        fun closeDriverBookCar(@Body closeDriverBookCarBody: CloseDriverBookCarBody): Single<CloseDriverBookCarRespon>

        @POST("BookCarRestService/service/driverBoardApproveRejectBookCar")
        fun driverBoardApproveRejectBookCar(@Body driverBoardApproveRejectBookCarBody: DriverBoardApproveRejectBookCarBody): Single<DriverBoardApproveRejectBookCarRespon>

        @POST("BookCarRestService/service/administrativeApproveRejectBookCar")
        fun administrativeApproveRejectBookCar(@Body administrativeApproveRejectBookCarBody: AdministrativeApproveRejectBookCarBody): Single<AdministrativeApproveRejectBookCarRespon>

        @POST("BookCarRestService/service/updateTokenUser")
        fun updateTokenUser(@Body updateTokenUserBody: UpdateTokenUserBody): Single<UpdateTokenUserRespon>

        @POST("BookCarRestService/service/getBookCarToken")
        fun getBookCarToken(@Body getBookCarTokenBody: GetBookCarTokenBody): Single<GetBookCarTokenRespon>

        @POST("BookCarRestService/service/getListUserTogether")
        fun getListUserTogether(@Body getListUserTogetherBody: GetListUserTogetherBody): Single<GetListUserTogetherRespon>

        @POST("BookCarRestService/service/getHistoryCar")
        fun getHistoryCar(@Body getHistoryCarBody: GetHistoryCarBody): Single<GetHistoryCarRespon>

        @POST("BookCarRestService/service/getDataProvinceCity")
        fun getDataProvinceCity(): Single<GetDataProvinceCityRespon>

        @POST("BookCarRestService/service/getDataDistrict")
        fun getDataDistrict(@Body getDataDistrictBody: GetDataDistrictBody): Single<GetDataDistrictRespon>

        @POST("BookCarRestService/service/getDataWard")
        fun getDataWard(@Body getDataWardBody: GetDataWardBody): Single<GetDataWardRespon>

        @POST("BookCarRestService/service/closeManagerBookCar")
        fun closeManagerBookCar(@Body closeManagerBookCarBody: CloseManagerBookCarBody): Single<CloseManagerBookCarRespon>

        @POST("BookCarRestService/service/getHistoryDetailCar")
        fun getHistoryDetailCar(@Body getHistoryDetailCarBody: GetHistoryDetailCarBody): Single<GetHistoryDetailCarRespon>

        @POST("BookCarRestService/service/updateLocation")
        fun updateLocation(@Body updateLocationBody: UpdateLocationBody): Single<UpdateLocationRespon>

        @POST("BookCarRestService/service/getListManager")
        fun getListManager(@Body getListManagerBody: GetListManagerBody): Single<GetListManagerRespon>

        @POST("BookCarRestService/service/getAppVersion")
        fun getAppVersion(): Single<GetAppVersionRespon>

        //api danh gia.
        @POST("BookCarRestService/service/rateBookCar")
        fun rateBookCar(@Body updateBookCarBody: UpdateBookCarBody): Single<ResultInfo>
        //api mo lenh.
        @POST("BookCarRestService/service/extendBookCar")
        fun extentBookCar(@Body addBookCarBody: AddBookCarBody): Single<AddBookCarRespon>
        //api duyet tu choi yeu cau sua cua account PTGD chuyen trach
        @POST("BookCarRestService/service/viceManagerApproveRejectBookCar")
        fun viceManagerApproveRejectBookCar(@Body administrativeApproveRejectBookCarBody: AdministrativeApproveRejectBookCarBody): Single<AdministrativeApproveRejectBookCarRespon>
        // api lay ra danh sach bien xe theo sysGroupId.
        @POST("BookCarRestService/service/searchCatVehicle")
        fun searchCatVehicle(@Body requestBody: GetListManagerBody): Single<CatVehicleReponseDTO>
        // api lay danh sach don vi xe
        @POST("BookCarRestService/service/getListTypeCarTruck")
        fun getListTypeCarTruck(): Single<GetListTypeCarRespon>
        // api lay danh sach loai xe theo kieu 5 xe ban tai.
        @POST("BookCarRestService/service/getBranch")
        fun getBranch(@Body requestBody: BranchRequestBody): Single<BranchReponseDTO>
        //api tim kiem man hinh giam sat xe.
        @POST("BookCarRestService/service/getVehicleMonitoring")
        fun getVehicleMonitoring(@Body requestBody: VehicleMonitoringRequestBody): Single<GetHistoryCarRespon>



    }
}
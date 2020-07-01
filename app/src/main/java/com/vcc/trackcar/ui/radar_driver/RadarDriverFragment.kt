package com.vcc.trackcar.ui.radar_driver

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.arsy.maps_library.MapRadar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shreyaspatil.MaterialDialog.MaterialDialog
import com.tedpark.tedpermission.rx2.TedRx2Permission
import com.vcc.trackcar.MainActivity
import com.vcc.trackcar.R
import com.vcc.trackcar.model.getListDriverCar.BookCarDto
import com.vcc.trackcar.model.getListDriverCar.GetListDriverCarBody
import com.vcc.trackcar.model.getListDriverCar.GetListDriverCarRespon
import com.vcc.trackcar.model.getListDriverCar.LstBookCarDto
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.ui.base.CommonVCC
import com.vcc.trackcar.ui.home.custom_infowindow_maps.CustomInfoWindowGoogleMap
import com.vcc.trackcar.ui.radar_driver.custom_infowindow_maps.CustomInfoWindowGoogleMapRadar
import com.vcc.trackcar.ui.xep_xe_doi_truong_xe.select_driver_car.ListDriverCarFragment.Companion.EXTRA_SELECTED_CAR_LIST_DRIVER
import com.vcc.trackcar.utils.DistanceCalculator
import es.dmoral.toasty.Toasty
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RadarDriverFragment : Fragment(), OnMapReadyCallback,
    CustomInfoWindowGoogleMapRadar.OnItemClickDriverMaps {

    companion object {
        fun newInstance() = RadarDriverFragment()
    }

    private lateinit var viewModel: RadarDriverViewModel

    private var mMap: GoogleMap? = null
    private lateinit var mapRadar: MapRadar

    private lateinit var mainActivcity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mainActivcity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.radar_driver_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RadarDriverViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initView() {
        mainActivcity.showLoading()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mainActivcity.hideLoading()
        this.mMap = googleMap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity!!.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                initializeMap(mMap)
            }
        } else {
            initializeMap(mMap)
        }

        initData()
    }

    private fun initializeMap(mMap: GoogleMap?) {
        if (mMap != null) {
            viewModel.carDieuChuyen =
                arguments?.getSerializable(EXTRA_SELECTED_CAR_LIST_DRIVER) as LstBookCarDto
            var latLng =
                LatLng(viewModel.carDieuChuyen.latitudeCar, viewModel.carDieuChuyen.longtitudeCar)

            mMap.isMyLocationEnabled = true

            try {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            mapRadar = MapRadar(mMap, latLng, context)
            //mapRadar.withClockWiseAnticlockwise(true);
            mapRadar.withDistance(3000)
            mapRadar.withClockwiseAnticlockwiseDuration(2)
            //mapRadar.withOuterCircleFillColor(Color.parseColor("#12000000"));
            mapRadar.withOuterCircleStrokeColor(Color.parseColor("#fccd29"))
            //mapRadar.withRadarColors(Color.parseColor("#00000000"), Color.parseColor("#ff000000"));  //starts from transparent to fuly black
            mapRadar.withRadarColors(
                Color.parseColor("#00fccd29"), Color.parseColor("#fffccd29")
            ) //starts from transparent to fuly black
            //mapRadar.withOuterCircleStrokewidth(7);
            //mapRadar.withRadarSpeed(5);
            mapRadar.withOuterCircleTransparency(0.5f)
            mapRadar.withRadarTransparency(0.5f)
            mapRadar.startRadarAnimation()
        }
    }

    private fun initData() {
        fetchGetListDriverCar()
    }

    private fun fetchGetListDriverCar() {
        mainActivcity.showLoading()
        var body = GetListDriverCarBody().apply {
            bookCarDto = BookCarDto().apply {
                sysGroupId = CommonVCC.getUserLogin().sysGroupId
            }
        }

        API.service.getListDriverCar(body).subscribeOn(Schedulers.io()) //(*)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GetListDriverCarRespon> {
                override fun onSuccess(respon: GetListDriverCarRespon) {
                    mainActivcity.hideLoading()
                    if (respon.resultInfo.status == CommonVCC.RESULT_STATUS_OK) {
                        drawMakerDriver(respon.lstBookCarDto)

                        var numDriverNear = 0
                        for (driver in respon.lstBookCarDto) {
                            if (driver.latitudeDriver != null && DistanceCalculator.distance(
                                    LatLng(
                                        viewModel.carDieuChuyen.latitudeCar,
                                        viewModel.carDieuChuyen.longtitudeCar
                                    ), LatLng(driver.latitudeDriver, driver.longtitudeDriver)
                                ) <= CommonVCC.LIMIT_DISTANCE
                            ) {
                                numDriverNear++
                            }
                        }
                        Toasty.info(
                            activity!!, getString(
                                R.string.tim_thay_num_lai_xe,
                                numDriverNear,
                                CommonVCC.LIMIT_DISTANCE.toString()
                            ), Toast.LENGTH_LONG, true
                        ).show()
                    } else {
                        Toasty.error(
                            activity!!, respon.resultInfo.message, Toast.LENGTH_SHORT, true
                        ).show()
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    mainActivcity.hideLoading()
                    Toasty.error(
                        activity!!,
                        getString(R.string.error_server_search_driver),
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }

            })
    }

    private fun drawMakerDriver(lstBookCarDto: List<LstBookCarDto>) {
        val customInfoWindow =
            CustomInfoWindowGoogleMapRadar(activity, this, viewModel.carDieuChuyen)
        mMap!!.setInfoWindowAdapter(customInfoWindow)
        mMap!!.setOnInfoWindowClickListener { it ->
            var driver = it.tag as LstBookCarDto
            showDialogComfirm(driver)
        }

        for (itemDriver in lstBookCarDto) {
            if (itemDriver.latitudeDriver != null) {
                mMap!!.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            itemDriver.latitudeDriver, itemDriver.longtitudeDriver
                        )
                    ).icon(BitmapDescriptorFactory.fromResource(R.drawable.delivery_man)).title(
                        itemDriver.driverName
                    )
                ).tag = itemDriver
            }
        }
    }

    private fun showDialogComfirm(driver: LstBookCarDto) {
        val mDialog = MaterialDialog.Builder(activity!!).setTitle(getString(R.string.xac_nhan))
            .setMessage(getString(R.string.approve_comfirm_driver)).setCancelable(true)
            .setPositiveButton(getString(R.string.xac_nhan)) { dialogInterface, which ->
                viewModel.carDieuChuyen.apply {
                    driverId = driver.driverId
                    driverName = driver.driverName
                    driverCode = driver.driverCode
                    phoneNumberDriver = driver.phoneNumberDriver
                }
                dialogInterface.dismiss()
                mainActivcity.popBackStackFragment()
            }.setNegativeButton(getString(R.string.call_phone)) { dialogInterface, which ->
                TedRx2Permission.with(mainActivcity).setDeniedMessage(R.string.reject_permission)
                    .setPermissions(Manifest.permission.CALL_PHONE).request()
                    .subscribe { permissionResult ->
                        if (permissionResult.isGranted) {
                            val intent = Intent(Intent.ACTION_CALL)
                            intent.data = Uri.parse("tel:" + driver.phoneNumberDriver)
                            startActivity(intent)
                        }
                    }
                dialogInterface.dismiss()
            }.build()

        mDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (mapRadar.isAnimationRunning) {
                mapRadar.stopRadarAnimation()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    override fun clickSelectItemDriverMaps(driver: LstBookCarDto?) {
        if (driver != null) {
            Toasty.info(mainActivcity, driver.driverName, Toast.LENGTH_SHORT).show()
        }
    }

}

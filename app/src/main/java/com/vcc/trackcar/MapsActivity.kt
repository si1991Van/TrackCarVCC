package com.vcc.trackcar

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.vcc.trackcar.model.PlacesResults
import com.vcc.trackcar.remote.API
import com.vcc.trackcar.utils.showToastError
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    lateinit var mapFragment: SupportMapFragment
    lateinit var polylineList: List<LatLng>
    lateinit var marker: Marker
    var v: Float = 0.0f

    var lat: Double = 0.0
    var lng: Double = 0.0

    lateinit var handler: Handler

    lateinit var startPosition: LatLng
    lateinit var endPosition: LatLng

    var index = 0
    var next = 0

    var destination = ""

    lateinit var polylineOptions: PolylineOptions
    lateinit var blackPolylineOptions: PolylineOptions

    lateinit var blackPolyline: Polyline
    lateinit var greyPolyline: Polyline

    lateinit var myLocation: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment


        polylineList = ArrayList()
        imvSearch.setOnClickListener {
            destination = edtPlace.text.toString().replace(" ", "+")
            mapFragment.getMapAsync(this)
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            isTrafficEnabled = false
            isIndoorEnabled = false
            isBuildingsEnabled = false
            uiSettings.isZoomControlsEnabled = true
        }


        val myHome = LatLng(21.145191, 105.957895)
        mMap.addMarker(MarkerOptions().position(myHome).title("Bac Thuy' Home"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myHome, 18F))
        mMap.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.Builder().target(googleMap.cameraPosition.target)
                    .zoom(17F)
                    .bearing(30F)
                    .tilt(45F)
                    .build()
            )
        )

        var requestUrl = ""
        try {
            requestUrl = "/maps/api/directions/json?" +
                    "mode=driving&" +
                    "transit_routing_preference=less_driving&" +
                    "origin=" + myHome.latitude + "," + myHome.longitude + "&" +
                    "destination=" + destination + "&" +
                    "key=" + getResources().getString(R.string.google_maps_key)

            API.service.getDataFromGoogleApi(
                myHome.toString(),
                destination,
                getResources().getString(R.string.google_maps_key)
            )
                .subscribeOn(Schedulers.io()) //(*)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<PlacesResults> {
                    override fun onSuccess(respon: PlacesResults) {
                        for (item in respon.routes) {
                            var polyline = item.overviewPolyline.points
                            polylineList = decodePoly("a~l~Fjk~uOnzh@vlbBtc~@tsE`vnApw{A`dw@~w\\\\|tNtqf@l{Yd_Fblh@rxo@b}@xxSfytA\n" +
                                    "      blk@xxaBeJxlcBb~t@zbh@jc|Bx}C`rv@rw|@rlhA~dVzeo@vrSnc}Axf]fjz@xfFbw~@dz{A~d{A|zOxbrBbdUvpo@`\n" +
                                    "      cFp~xBc`Hk@nurDznmFfwMbwz@bbl@lq~@loPpxq@bw_@v|{CbtY~jGqeMb{iF|n\\\\~mbDzeVh_Wr|Efc\\\\x`Ij{kE}mAb\n" +
                                    "      ~uF{cNd}xBjp]fulBiwJpgg@|kHntyArpb@bijCk_Kv~eGyqTj_|@`uV`k|DcsNdwxAott@r}q@_gc@nu`CnvHx`k@dse\n" +
                                    "      @j|p@zpiAp|gEicy@`omFvaErfo@igQxnlApqGze~AsyRzrjAb__@ftyB}pIlo_BflmA~yQftNboWzoAlzp@mz`@|}_\n" +
                                    "      @fda@jakEitAn{fB_a]lexClshBtmqAdmY_hLxiZd~XtaBndgC")

                        }

//                        var tempListPos = ArrayList<LatLng>()
//                        tempListPos.add(LatLng(21.114614, 105.956690))
//                        polylineList = tempListPos

                        var builder = LatLngBounds.Builder()
                        for (latlng in polylineList)
                            builder.include(latlng)
                        var bounds = builder.build()
                        var mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2)
                        mMap.animateCamera(mCameraUpdate)

                        polylineOptions = PolylineOptions()
                            .apply {
                                color(Color.GRAY)
                                width(5F)
                                startCap(SquareCap())
                                endCap(SquareCap())
                                jointType(JointType.ROUND)
                                addAll(polylineList)
                            }
                        greyPolyline = mMap.addPolyline(polylineOptions)

                        blackPolylineOptions = PolylineOptions()
                            .apply {
                                color(Color.BLACK)
                                width(5F)
                                startCap(SquareCap())
                                endCap(SquareCap())
                                jointType(JointType.ROUND)
                                addAll(polylineList)
                            }
                        blackPolyline = mMap.addPolyline(blackPolylineOptions)

                        mMap.addMarker(MarkerOptions().position(polylineList.get(polylineList.size - 1)))

                        // Animator
                        var polylineAnimator = ValueAnimator.ofInt(0, 100)
                            .apply {
                                duration = 2000 // 2 sec
                                interpolator = LinearInterpolator()
                                addUpdateListener {
                                    var points = greyPolyline.points
                                    var percentValue = it.getAnimatedValue() as Int
                                    var size = points.size
                                    var newPoints = (size * (percentValue / 100)) as Int
                                    var p = points.subList(0, newPoints)
                                    blackPolyline.points = p
                                }
                                start()
                            }

                        // Add car marker
                        marker = mMap.addMarker(
                            MarkerOptions().position(myHome)
                                .flat(true)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
                        )

                        // Car moving
                        handler = Handler()
                        index = -1
                        next = 1
                        handler.postDelayed(object : Runnable {
                            override fun run() {
                                if (index < polylineList.size - 1) {
                                    index++
                                    next = index + 1
                                }
                                if (index < polylineList.size - 1) {
                                    startPosition = polylineList[index]
                                    endPosition = polylineList[next]
                                }

                                var valueAnimator = ValueAnimator.ofFloat(0F, 1F)
                                    .apply {
                                        duration = 3000
                                        interpolator = LinearInterpolator()
                                        addUpdateListener {
                                            v = it.animatedFraction
                                            lng = v * endPosition.longitude + (1 - v) * startPosition.longitude
                                            lat = v * endPosition.latitude + (1 - v) * startPosition.latitude

                                            var newPos = LatLng(lat, lng)
                                            marker.apply {
                                                position = newPos
                                                setAnchor(0.5f, 0.5f)
                                                rotation = getBearing(startPosition, newPos)
                                                mMap.moveCamera(
                                                    CameraUpdateFactory.newCameraPosition(
                                                        CameraPosition.Builder()
                                                            .target(newPos)
                                                            .zoom(15.5f)
                                                            .build()
                                                    )
                                                )
                                            }
                                            marker.position = newPos
                                            marker.setAnchor(0.5f, 0.5f)

                                        }
                                        start()
                                    }
                                handler.postDelayed(this, 3000)
                            }
                        }, 3000)

                    }

                    override fun onSubscribe(d: Disposable) {
//                        showToast("onSubscribe")
                    }

                    override fun onError(e: Throwable) {
                        showToastError("onError")
                    }

                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getBearing(startPosition: LatLng, newPos: LatLng): Float {
        var lat = Math.abs(startPosition.latitude - newPos.latitude)
        var lng = Math.abs(startPosition.longitude - newPos.longitude)

        if (startPosition.latitude < newPos.latitude && startPosition.longitude < newPos.longitude)
            return Math.toDegrees(Math.atan(lng / lat)) as Float
        else if (startPosition.latitude >= newPos.latitude && startPosition.longitude < newPos.longitude)
            return ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90) as Float
        else if (startPosition.latitude >= newPos.latitude && startPosition.longitude >= newPos.longitude)
            return (Math.toDegrees(Math.atan(lng / lat)) + 180) as Float
        else if (startPosition.latitude < newPos.latitude && startPosition.longitude >= newPos.longitude)
            return ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270) as Float
        return -1f
    }

    private fun decodePoly(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }

        return poly
    }
}

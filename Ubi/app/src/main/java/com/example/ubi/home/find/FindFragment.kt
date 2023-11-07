package com.example.ubi.home.find

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ubi.R
import com.example.ubi.databinding.FragmentFindBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.Label
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.mapwidget.InfoWindowOptions
import java.time.format.TextStyle


class FindFragment : Fragment() {
    val TAG = "FindFragment"

    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!
    private var latitude: Double = 35.663234224668415
    private var longitude = 128.4136357098649

    private lateinit var mapView: MapView
    private lateinit var kakaoMap:KakaoMap

    private val viewModel by activityViewModels<FindViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        binding.mapView.start(object : MapLifeCycleCallback(){
            override fun onMapDestroy() {
                //지도 API가 정상적으로 종료 될때 호출
                Log.d("지도","정상 종료")
            }

            override fun onMapError(error: Exception?) {
                //인증 실패 및 지도 사용 중 애러가 발생할떄
                Log.e("지도","애러 : ${error?.message.toString()}")
            }
        },
            object : KakaoMapReadyCallback(){
                override fun onMapReady(map: KakaoMap) {
                    kakaoMap = map

                    map.setOnLabelClickListener { kakaoMap, layer, label ->
                        val arr = label.labelId.split("|")
                        when (arr[0]) {
                            "article" -> {
//                                var adater = ViewPager2Adater(list, requireActivity())
//                                val board = binding.boardPager
//                                board.offscreenPageLimit=1
//                                board.getChildAt(0).overScrollMode=View.OVER_SCROLL_NEVER
//                                board.adapter = adater

                            }
                        }
                    }
                    viewModel.articleList.observe(viewLifecycleOwner) { list ->
//                        Log.d(TAG, "article list ${list?.size}")
//                        list.forEach {
//                            Log.d(TAG, "${it.title}")
//                            makeMarker("article|${it.id}", it.latitude, it.longitude, it.title)
//                        }
                    }
                    var cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(latitude, longitude))
                    kakaoMap.moveCamera(cameraUpdate)

                    Log.d("지도","성공")
//                    binding.addArticleButton.setOnClickListener {
////                        makeMarker("$test", latitude, longitude)
////                        Log.d(TAG, test.toString())
//                        findNavController().navigate(R.id.action_editFragment_to_findFragment2)
//                    }
                }

            })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = _binding?.mapView

//        viewModel.location.observe(viewLifecycleOwner) { location ->
//            if (location != null) {
//                Log.d(TAG, "${location.latitude} - ${location.longitude}")
//                latitude = location.latitude
//                longitude = location.longitude
//                viewModel.getPostList(latitude, longitude)
//            }
        }

//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            var locationManager =
//                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            viewModel.location.value =
//                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER, 10000, 10.0f, locationListener
//            )
//        }
//
//    }
//
//    fun makeMarker(id : String, latitude : Double, longitude : Double, text: String? = null): Label {
//        val styles = LabelStyles.from(id, LabelStyle.from(R.drawable.marker_spot).setZoomLevel(8).setTextStyles(30, Color.BLACK))
//        val options: LabelOptions = LabelOptions.from(LatLng.from(latitude, longitude))
//            .setStyles(styles)
//            .setClickable(true)
//        val label = kakaoMap.labelManager!!.layer!!.addLabel(options)
//        text?.let { label.changeText(it) }
//        return label
//    }
//
//
//
//
//    val locationListener = object : LocationListener {
//        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
//            super.onStatusChanged(provider, status, extras)
//            // provider의 상태가 변경될때마다 호출
//            // deprecated
//        }
//
//        override fun onLocationChanged(location: Location) {
//            // 위치 정보 전달 목적으로 호출(자동으로 호출)
//            viewModel.location.value = location
//            Log.d("Location", "Latitude : ${location.latitude}, Longitude : ${location.longitude}")
//        }
//
//        override fun onProviderEnabled(provider: String) {
//            super.onProviderEnabled(provider)
//            // provider가 사용 가능한 생태가 되는 순간 호출
//        }
//
//        override fun onProviderDisabled(provider: String) {
//            super.onProviderDisabled(provider)
//            // provider가 사용 불가능 상황이 되는 순간 호출
//        }
//    }
}
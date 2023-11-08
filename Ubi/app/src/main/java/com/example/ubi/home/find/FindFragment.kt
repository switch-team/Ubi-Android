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
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.ubi.R
import com.example.ubi.databinding.FragmentFindBinding
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


class FindFragment : Fragment() {
    val TAG = "FindFragment"

    private var _binding: FragmentFindBinding? = null
    private val binding get() = _binding!!

    private var latitude: Double = 35.663234224668415
    private var longitude = 128.4136357098649

    private lateinit var mapView: MapView
    private lateinit var kakaoMap: KakaoMap

    private val viewModel by activityViewModels<FindViewModel>()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFindBinding.inflate(inflater, container, false)
        mapView = binding.mapView
        binding.addArticleButton.setOnClickListener {
            findNavController().navigate(R.id.action_findFragment_to_createFragment)
            Log.d(TAG, "here is Find")
        }
        binding.mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                //지도 API가 정상적으로 종료 될때 호출
                Log.d("지도", "정상 종료")
            }


            override fun onMapError(error: Exception?) {
                //인증 실패 및 지도 사용 중 애러가 발생할떄
                Log.e("지도", "애러 : ${error?.message.toString()}")
            }
        },
            object : KakaoMapReadyCallback() {
                override fun onMapReady(map: KakaoMap) {
                    kakaoMap = map
                    makeLocation(latitude, longitude)

                    map.setOnLabelClickListener { kakaoMap, layer, label ->
                        val arr = label.styles.styleId.split("|")
                        Log.d(TAG, "onMarkerClick [${label.styles.styleId}]")
                        when (arr[0]) {
                            "article" -> {
                                // 서버에서 상세 내역 받기
                                viewModel.getArticle(arr[1])
                            }
                        }
                    }
                    viewModel.articleList.observe(viewLifecycleOwner) { list ->
                        list.forEach {
                            Log.d(TAG, "${it.title}")
                            makeMarker("article|${it.id}", it.latitude, it.longitude, it.title)
                        }
                    }
                    var cameraUpdate =
                        CameraUpdateFactory.newCenterPosition(LatLng.from(latitude, longitude))
                    kakaoMap.moveCamera(cameraUpdate)

                    Log.d("지도", "성공")


                    viewModel.articleInfo.observe(viewLifecycleOwner) { article ->
                        Log.d(TAG, "Select Article: ${article}")
                        val layoutInflater = LayoutInflater.from(requireContext())
                        val view = layoutInflater.inflate(R.layout.dialog_article, null)
                        val builder = AlertDialog.Builder(requireContext())
                            .setView(view)
                            .setPositiveButton("나가기"){di, i-> di.dismiss()}
                        val dialog = builder.create()
                        val textTitle = view.findViewById<TextView>(R.id.titleText)
                        val textContent = view.findViewById<TextView>(R.id.contentText)
                        val imgThumbnail = view.findViewById<ImageView>(R.id.imgView)
                        val defaultImage = R.drawable.not_img

                        textTitle.text = article.title
                        textContent.text = article.content
                        Glide.with(requireContext())
                            .load(article.thumbnailImage) // 불러올 이미지 url
                            .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
                            .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
                            .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                            .into(imgThumbnail) // 이미지를 넣을 뷰
                        Log.d(TAG, "${article.thumbnailImage}")
                        dialog.show()
                    }
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
        viewModel.location.observe(viewLifecycleOwner) { location ->
            if (location != null) {
                Log.d(TAG, "${location.latitude} - ${location.longitude}")
                latitude = location.latitude
                longitude = location.longitude
                viewModel.getPostList(latitude, longitude)

            }
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            var locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            viewModel.location.value =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 10000, 10.0f, locationListener
            )
        }

    }

    fun makeMarker(id: String, latitude: Double, longitude: Double, text: String? = null): Label {
        val styles = LabelStyles.from(
            id,
            LabelStyle.from(R.drawable.marker_spot).setZoomLevel(8).setTextStyles(10, Color.BLACK)
        )
        val options: LabelOptions = LabelOptions.from(LatLng.from(latitude, longitude))
            .setStyles(styles)
            .setClickable(true)
        val label = kakaoMap.labelManager!!.layer!!.addLabel(options)
        text?.let { label.changeText(it) }
        return label
    }
    fun makeLocation(latitude: Double, longitude: Double): Label {
        val styles = LabelStyles.from(
            "myLocation",
            LabelStyle.from(R.drawable.my_location).setZoomLevel(8).setAnchorPoint(0.5F, 0.5F).setApplyDpScale(false)
        )
        val options: LabelOptions = LabelOptions.from(LatLng.from(latitude, longitude))
            .setStyles(styles)
            .setClickable(false)
        return kakaoMap.labelManager!!.layer!!.addLabel(options)
    }
    fun updateLocation(latitude: Double, longitude: Double): Label{
        val myLocation = kakaoMap.labelManager!!.layer!!.getLabel("myLocation")
        myLocation.pathOptions.setPath(LatLng.from(latitude, longitude))
        return myLocation
    }

    private val locationListener = object : LocationListener {
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            super.onStatusChanged(provider, status, extras)
            // provider의 상태가 변경될때마다 호출
            // deprecated
        }

        override fun onLocationChanged(location: Location) {
            // 위치 정보 전달 목적으로 호출(자동으로 호출)
            viewModel.location.value = location
            Log.d("Location", "Latitude : ${location.latitude}, Longitude : ${location.longitude}")
        }

        override fun onProviderEnabled(provider: String) {
            super.onProviderEnabled(provider)
            // provider가 사용 가능한 생태가 되는 순간 호출
        }

        override fun onProviderDisabled(provider: String) {
            super.onProviderDisabled(provider)
            // provider가 사용 불가능 상황이 되는 순간 호출
        }
    }
}
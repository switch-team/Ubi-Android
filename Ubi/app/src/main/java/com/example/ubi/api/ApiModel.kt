package com.example.ubi.api

import android.devicelock.DeviceId
import com.example.ubi.api.request.LoginRequest
import com.example.ubi.api.response.ArticleResponse
import com.example.ubi.api.response.CheckBoardResponse
import com.example.ubi.api.response.FindUser
import com.example.ubi.api.response.FriendList
import com.example.ubi.api.response.GuidedResponse
import com.example.ubi.api.response.InviteList
import com.example.ubi.api.response.ProfileResponse
import com.example.ubi.api.response.RegisterResponse
import com.example.ubi.api.response.TokenResponse
import com.example.ubi.api.response.UserUUID
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

object ApiModel {
    interface AuthApi {
        @GET("/auth/check")
        fun loginCheck(
            @Header("Authorization") token:String
        ):Call<Unit>
        @POST("/auth/register")
        fun register(
            @Body request : RegisterResponse
        ):Call<Unit>
        @POST("/auth/login")
        fun login(
            @Body request: LoginRequest
        ): Call<GuidedResponse<TokenResponse>>
    }

    interface UserApi {
        @GET("/user/profile")
        fun profileFind():Call<GuidedResponse<ProfileResponse>>

        @GET("/user/find")
        fun findUser(@Query("id") id : String):Call<GuidedResponse<FindUser>>

        @Multipart
        @PATCH("/user/profile")
        fun correctionUser(
            @Part("data") correctionUserRequest: RequestBody, // request
            @Part("profileImage") image: RequestBody
        ):Call<Unit>

    }

    interface BoardApi{
        @GET("/article")
        fun getBoardItemList(
            @Query("latitude") latitude: Double,
            @Query("longitude") longitude: Double
        ):Call<GuidedResponse<List<ArticleResponse>>>

        @GET("/article/{id}")
        fun checkBoard(
            @Path(value = "id") id:String
        ):Call<GuidedResponse<CheckBoardResponse>>

        @Multipart
        @POST("/article")
        fun sendBoard(
            @Part("data") createArticleRequest: RequestBody, // request
            @Part("profileImage") image: RequestBody? = null, // can be null
        ): Call<Unit>

        @POST("/article/{id}/like")
        fun like(
            @Path(value = "id") id:Int
        ):Call<Unit>

        @GET("/article/my")
        fun getMyArticle():Call<GuidedResponse<ArticleResponse>>

        @DELETE("/board/{id}")
        fun deleteBoard(
            @Path(value = "id") id:Int
        ):Call<Unit>

    }
    interface Friend{
                                //친구 리스트 구하기	/friend	GET
        @GET("/friend")
        fun getFriendList(

        ):Call<GuidedResponse<List<FriendList>>>
                                //친구 초대	/friend/request	POST
        @POST("/friend/request")
        fun inviteFriend(
            @Body request: UserUUID
        ):Call<Unit>
                                //보넨  친구 초대 보기	/friend/pending	GET
        @GET("/friend/pending")
        fun inviteList(

        ):Call<GuidedResponse<List<InviteList>>>
                                //친구 초대 받기	/friend/accept	POST
        @POST("/friend/accept")
        fun acceptInvitation(
            @Body request: UserUUID
        ):Call<Unit>
                                //친구 삭제 / 친구 초대 거절	/friend/delete	DELETE
        @DELETE("/friend/delete")
        fun deleteInvitation(
            @Body request: UserUUID
        ):Call<Unit>
    }
}
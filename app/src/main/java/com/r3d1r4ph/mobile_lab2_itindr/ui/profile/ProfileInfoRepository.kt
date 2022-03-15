package com.r3d1r4ph.mobile_lab2_itindr.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.repository.auth.AuthDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.repository.topic.TopicDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileParams
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileResponse
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileService
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.avatar.AvatarService
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.topic.TopicResponse
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.topic.TopicService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MultipartBody

class ProfileInfoRepository(app: Application) {

    private val appDatabase = Database.getInstance(app)
    private val authDBRepository = AuthDBRepository(app)
    private val topicDBRepository = TopicDBRepository(app)
    private val profileService = ProfileService()
    private val topicService = TopicService()
    private val avatarService = AvatarService()

    fun observeTopics() = topicDBRepository.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    fun getProfile() = authDBRepository.getProfile().map {
        if (!it.isNullOrBlank()) {
            Json.decodeFromString(it)
        } else {
            ProfileData.EMPTY
        }
    }

    suspend fun updateTopics(topics: List<TopicResponse>) {
        appDatabase.withTransaction {
            topicDBRepository.deleteAll()
            topicDBRepository.insertAll(topics.map { it.toEntity() })
        }
    }

    suspend fun deleteAvatar() =
        avatarService.deleteAvatar()


    suspend fun postAvatar(multipartBodyPart: MultipartBody.Part) =
        avatarService.postAvatar(multipartBodyPart)


    suspend fun sendProfile(profileParams: ProfileParams) = profileService.patchProfile(
        profileParams = profileParams
    )

    suspend fun updateProfile(profileResponse: ProfileResponse) {
        Log.d("profile", Json.encodeToString(profileResponse.toDomain()))
        authDBRepository.updateProfile(Json.encodeToString(profileResponse.toDomain()))
    }

    suspend fun getTopics() = topicService.topic()
}
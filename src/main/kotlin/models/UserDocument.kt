package moe.rylie.akarihoshizaki.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDocument(
	val id: Int,
	val username: String,
	val usernameLowercase: String,
	val about: String?,
	val socialMedia: UserDocumentSocialMedia,
	val status: String?,
	val customBannerLocation: String?,
	val customPfpLocation: String?,
	val joinDate: Long,
	val lastSeen: Long,
)

@Serializable
data class UserDocumentSocialMedia(
	val discord: String?,
	val github: String?,
	val steam: String?,
	val twitch: String?,
	val twitter: String?,
	val youtube: String?,
)

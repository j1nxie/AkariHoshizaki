package moe.rylie.akarihoshizaki.models

import kotlinx.serialization.Serializable

@Serializable
data class KamaitachiResponse<T>(
	val success: Boolean,
	val description: String,
	val body: T? = null,
)

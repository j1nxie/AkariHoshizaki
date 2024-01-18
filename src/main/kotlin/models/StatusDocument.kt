package moe.rylie.akarihoshizaki.models

import kotlinx.serialization.Serializable

@Serializable
data class StatusDocument(
	val serverTime: Long,
	val startTime: Long,
	val whoami: Long?,
	val version: String,
	val permissions: Array<String>,
	val echo: String? = null,
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (javaClass != other?.javaClass) return false

		other as StatusDocument

		if (serverTime != other.serverTime) return false
		if (startTime != other.startTime) return false
		if (whoami != other.whoami) return false
		if (version != other.version) return false
		if (!permissions.contentEquals(other.permissions)) return false
		if (echo != other.echo) return false

		return true
	}

	override fun hashCode(): Int {
		var result = serverTime.hashCode()
		result = 31 * result + startTime.hashCode()
		result = 31 * result + (whoami?.hashCode() ?: 0)
		result = 31 * result + version.hashCode()
		result = 31 * result + permissions.contentHashCode()
		result = 31 * result + (echo?.hashCode() ?: 0)
		return result
	}
}

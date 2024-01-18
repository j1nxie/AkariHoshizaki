package moe.rylie.akarihoshizaki.extensions

import com.kotlindiscord.kord.extensions.DISCORD_RED
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.optionalString
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.rest.builder.message.create.embed
import io.ktor.client.call.*
import io.ktor.client.request.*
import moe.rylie.akarihoshizaki.client
import moe.rylie.akarihoshizaki.constants.BASE_URL
import moe.rylie.akarihoshizaki.models.KamaitachiResponse
import moe.rylie.akarihoshizaki.models.StatusDocument
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class StatusExtension : Extension() {
	override val name = "status"

	override suspend fun setup() {
		publicSlashCommand(::GetUserArgs) {
			name = "status"
			description = "get Kamaitachi's status."

			action {
				var url = if (arguments.echo != null) "$BASE_URL/status?echo=${arguments.echo}" else "$BASE_URL/status"
				val response: KamaitachiResponse<StatusDocument> = client.get(url).body()

				if (!response.success) {
					respond {
						embed {
							title = "An error has occurred."
							description = response.description
							color = DISCORD_RED
						}
					}
				} else {
					val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
					respond {
						embed {
							title = "Kamaitachi is live!"
							url = "https://kamaitachi.xyz"
							field {
								name = "Server time"
								value = (response.body?.serverTime?.let { it1 ->
									Instant.ofEpochMilli(it1).atZone(ZoneId.systemDefault())
										.toLocalDateTime().format(dateFormat)
								}.toString())
							}
							field {
								name = "Start time"
								value = (response.body?.startTime?.let { it1 ->
									Instant.ofEpochMilli(it1).atZone(ZoneId.systemDefault())
										.toLocalDateTime().format(dateFormat)
								}.toString())
							}
							field {
								name = "Version"
								value = response.body?.version.toString()
							}
							if (arguments.echo != null) {
								field {
									name = "Echo"
									value = response.body?.echo.toString()
								}
							}
						}
					}
				}
			}
		}
	}

	inner class GetUserArgs : Arguments() {
		val echo by optionalString {
			name = "echo"
			description = "optional string to test the API."
		}
	}
}

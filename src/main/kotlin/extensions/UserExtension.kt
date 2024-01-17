package moe.rylie.akarihoshizaki.extensions

import com.kotlindiscord.kord.extensions.DISCORD_RED
import com.kotlindiscord.kord.extensions.commands.Arguments
import com.kotlindiscord.kord.extensions.commands.converters.impl.string
import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.rest.builder.message.create.embed
import io.ktor.client.call.*
import io.ktor.client.request.*
import moe.rylie.akarihoshizaki.client
import moe.rylie.akarihoshizaki.constants.BASE_URL
import moe.rylie.akarihoshizaki.constants.PROXY_URL
import moe.rylie.akarihoshizaki.models.KamaitachiResponse
import moe.rylie.akarihoshizaki.models.UserDocument


class UserExtension : Extension() {
	override val name = "user"

	override suspend fun setup() {
		publicSlashCommand(::GetUserArgs) {
			name = "user"
			description = "get an user's profile."

			action {
				val response: KamaitachiResponse<UserDocument> =
					client.get("$BASE_URL/users/${arguments.username}").body()
				if (!response.success) {
					respond {
						embed {
							title = "An error has occurred."
							description = response.description
							color = DISCORD_RED
						}
					}
				} else {
					respond {
						embed {
							title = "${response.body?.username} [ID: ${response.body?.id}]"
							url = "https://kamaitachi.xyz/u/${response.body?.username}"
							thumbnail {
								url = "$PROXY_URL/pfp/${response.body?.id}.png"
							}
							image = "$PROXY_URL/banner/${response.body?.id}.png"
							description = if (response.body?.about == null) "No description." else response.body.about
							footer {
								text =
									if (response.body?.status == null) "No status." else response.body.status.toString()
							}
						}
					}
				}
			}
		}
	}

	inner class GetUserArgs : Arguments() {
		val username by string {
			name = "username"
			description = "user to get profile of."
		}
	}
}

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
import moe.rylie.akarihoshizaki.constants.CDN_URL
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
					val imageString = if (response.body?.customPfpLocation == null) {
						"$CDN_URL/users/default/pfp"
					} else {
						"$CDN_URL/users/${response.body.id}/pfp-${response.body.customPfpLocation}"
					}
					respond {
						embed {
							title = "${response.body?.username} [ID: ${response.body?.id}]"
							url = "https://kamaitachi.xyz/u/${response.body?.username}"
							// TODO: fix the image string - waiting for zk to fix the api ig?
							image = imageString
							description = response.body?.about
							footer {
								text = response.body?.status.toString()
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

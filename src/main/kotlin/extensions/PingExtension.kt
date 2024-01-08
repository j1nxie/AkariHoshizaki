package moe.rylie.akarihoshizaki.extensions

import com.kotlindiscord.kord.extensions.extensions.Extension
import com.kotlindiscord.kord.extensions.extensions.publicSlashCommand
import com.kotlindiscord.kord.extensions.types.respond
import dev.kord.rest.builder.message.create.embed
import moe.rylie.akarihoshizaki.constants.PRETTY_VERSION
import java.util.*
import kotlin.KotlinVersion.Companion.CURRENT

class PingExtension : Extension() {
	override val name = "ping"

	override suspend fun setup() {
		publicSlashCommand {
			name = "ping"
			description = "ping the bot for its status."

			action {
				respond {
					embed {
						title = "Akari Hoshizaki"
						description = "Running on Kotlin `$CURRENT` on `${System.getProperty("os.name")
							.lowercase(Locale.getDefault())}`."
						footer {
							text = PRETTY_VERSION
						}
					}
				}
			}
		}
	}
}

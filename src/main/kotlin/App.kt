package moe.rylie.akarihoshizaki

import com.kotlindiscord.kord.extensions.ExtensibleBot
import com.kotlindiscord.kord.extensions.utils.env
import dev.kord.common.entity.Snowflake
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import moe.rylie.akarihoshizaki.extensions.*

val TEST_SERVER_ID = Snowflake(
	env("TEST_SERVER").toLong()  // Get the test server ID from the env vars or a .env file
)
val client = HttpClient(CIO) {
	install(Auth) {
		bearer {
			loadTokens {
				BearerTokens(env("KAMAITACHI_TOKEN"), "")
			}

			refreshTokens {
				BearerTokens(env("KAMAITACHI_TOKEN"), "")
			}
		}
	}

	install(ContentNegotiation) {
		json(Json {
			ignoreUnknownKeys = true
			prettyPrint = true
			isLenient = true
		})
	}
}

private val TOKEN = env("DISCORD_TOKEN")   // Get the bot' token from the env vars or a .env file

suspend fun main() {
	val bot = ExtensibleBot(TOKEN) {
		chatCommands {
			defaultPrefix = "?"
			enabled = true

			prefix { default ->
				if (guildId == TEST_SERVER_ID) {
					// For the test server, we use ! as the command prefix
					"!"
				} else {
					// For other servers, we use the configured default prefix
					default
				}
			}
		}

		extensions {
			add(::PingExtension)
			add(::UserExtension)
		}
	}

	bot.start()
}

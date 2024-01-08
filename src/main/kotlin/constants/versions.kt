package moe.rylie.akarihoshizaki.constants

import net.swiftzer.semver.SemVer

// update this every version bump along with build.gradle.kts
const val BOT_VERSION = "1.0.0-SNAPSHOT"

val VERSION_NAMES = arrayOf(
	"O.N.G.E.K.I",
	"PLUS",
	"SUMMER",
	"SUMMER PLUS",
	"R.E.D.",
	"R.E.D. PLUS",
	"bright",
	"bright MEMORY"
)

fun getVersionName(): String {
	val version: SemVer = SemVer.parse(BOT_VERSION);

	return VERSION_NAMES[version.minor];
}

val PRETTY_VERSION = "$BOT_VERSION [${getVersionName()}]"

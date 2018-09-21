package me.calebbassham.scenariomanager.api

data class TimeSpan(val ticks: Long)

class TimeSpanParser : ScenarioSettingParser<TimeSpan> {

    private val expression = Regex("^(?:(?<hours>[0-9]+) ?h(?:ours?)?)? ?(?:(?<minutes>[0-9]+) ?m(?:inutes?)?)? ?(?:(?<seconds>[0-9]+) ?s(?:econds?)?)?\$", RegexOption.IGNORE_CASE)

    override fun parse(input: String): TimeSpan {
        if (input.isEmpty()) return TimeSpan(0)

        with(expression.matchEntire(input)) {
            val hours = this?.groups?.get("hours")?.value?.toLong() ?: 0
            val minutes = this?.groups?.get("minutes")?.value?.toLong() ?: 0
            val seconds = this?.groups?.get("seconds")?.value?.toLong() ?: 0

            return TimeSpan((hours * 60 * 60 * 20) + (minutes * 60 * 20) + (seconds * 20))
        }
    }

}
package port

interface MetricProvider {
    fun sendMetric(metric: Metric)
}

enum class Metric {
    REGISTRATION_CREATED,
    USER_CREATED,
    USER_BLOCKED,
    ;
}

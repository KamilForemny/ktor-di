package com.kamilf.adapters.metrics

import port.Metric
import port.MetricProvider

class SampleMetricProvider : MetricProvider {
    override fun sendMetric(metric: Metric) {
        println("Sending metric: $metric")
    }
}

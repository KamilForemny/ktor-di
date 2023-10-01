package com.kamilf

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ApplicationTest : StringSpec({

    "sample test" {
        "sample".length shouldBe 6
    }
})

package com.automattic.freshlypressed.ui.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*

class PostUtilsTest {
    @Test
    fun printsDateCorrectly() {
        val result = PostUtils.printDate(Date(1230000000000))

        assertThat(result).isEqualTo("Dec 23, 2008")
    }
}
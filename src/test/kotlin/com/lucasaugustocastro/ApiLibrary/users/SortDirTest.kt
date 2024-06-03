package com.lucasaugustocastro.ApiLibrary.users

import com.lucasaugustocastro.ApiLibrary.SortDir
import com.lucasaugustocastro.ApiLibrary.exception.BadRequestException
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class SortDirTest {
    @Test
    fun `findOrThrow throws BadReqeustException for invalid values`(){
        assertThrows<BadRequestException> {
            SortDir.findOrThrow("INVALID")
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["asc", "ASC", "aSc"])
    fun `findOrThrow finds ASC valid value ignoring case`(dir: String){
        val sortDir = SortDir.findOrThrow(dir)

        sortDir shouldBe SortDir.ASC

    }

    @ParameterizedTest
    @ValueSource(strings = ["desc", "DESC", "deSc"])
    fun `findOrThrow finds DESC valid value ignoring case`(dir: String){
        val sortDir = SortDir.findOrThrow(dir)

        sortDir shouldBe SortDir.DESC


    }
}
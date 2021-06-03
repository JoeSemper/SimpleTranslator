package com.joesemper.simpletranslator

import com.joesemper.model.data.AppState
import com.joesemper.model.data.DataModel
import com.joesemper.model.data.Meanings
import com.joesemper.model.data.Translation
import com.joesemper.repository.room.HistoryEntity
import com.joesemper.simpletranslator.utils.convertDataModelSuccessToEntity
import org.junit.Test
import org.junit.Assert.*

class SearchResultParserTest {

    @Test
    fun convertDataModelSuccessToEntity_AppStateError_ReturnNull() {
        val appState = AppState.Error(Throwable())

        assertNull(convertDataModelSuccessToEntity(appState))
    }

    @Test
    fun convertDataModelSuccessToEntity_AppStateNullData_ReturnNull() {
        val appState = AppState.Success(null)

        assertNull(convertDataModelSuccessToEntity(appState))
    }

    @Test
    fun convertDataModelSuccessToEntity_AppStateEmptyData_ReturnNull() {
        val meanings = Meanings(Translation(""), "")
        val dataModel = DataModel("", listOf(meanings))
        val appState = AppState.Success(listOf(dataModel))

        assertNull(convertDataModelSuccessToEntity(appState))
    }

    @Test
    fun convertDataModelSuccessToEntity_AppStateNotEmpty_isCorrect() {
        val meanings = Meanings(Translation("translation"), "url")
        val dataModel = DataModel("word", listOf(meanings))
        val appState = AppState.Success(listOf(dataModel))

        assertEquals("word", convertDataModelSuccessToEntity(appState)?.word)
    }


}
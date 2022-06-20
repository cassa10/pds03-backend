package ar.edu.unq.pds03backend.utils

import ar.edu.unq.pds03backend.model.QuoteState

object QuoteStateHelper {
    fun getPendingStates(): Set<QuoteState> = setOf(QuoteState.PENDING, QuoteState.AUTOAPPROVED)
    fun getAllStates(): Set<QuoteState> = QuoteState.values().toSet()
}
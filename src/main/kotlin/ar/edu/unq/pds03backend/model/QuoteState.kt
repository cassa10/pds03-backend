package ar.edu.unq.pds03backend.model

enum class QuoteState {
    PENDING {
        override fun accept(quoteRequest: QuoteRequest) {
            quoteRequest.state = APPROVED
            quoteRequest.student.addEnrolledCourse(quoteRequest.course)
        }
        override fun revoke(quoteRequest: QuoteRequest) {
            quoteRequest.state = REVOKED
        }
    },
    APPROVED {
        override fun rollbackToPending(quoteRequest: QuoteRequest) {
            quoteRequest.student.deleteEnrolledCourse(quoteRequest.course)
            quoteRequest.state = PENDING
        }
    },
    REVOKED {
        override fun rollbackToPending(quoteRequest: QuoteRequest) {
            quoteRequest.state = PENDING
        }
    };

    open fun accept(quoteRequest: QuoteRequest) {}
    open fun revoke(quoteRequest: QuoteRequest) {}
    open fun rollbackToPending(quoteRequest: QuoteRequest) {}
}
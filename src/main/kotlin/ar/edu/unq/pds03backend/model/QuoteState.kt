package ar.edu.unq.pds03backend.model

enum class QuoteState {
    PENDING {
        override fun accept(quoteRequest: QuoteRequest) {
            quoteRequest.state = APPROVED
            quoteRequest.student.addEnrolledCourse(quoteRequest.course)
        }
    },
    APPROVED {

    },
    REVOKED {

    };

    open fun accept(quoteRequest: QuoteRequest) {}
}
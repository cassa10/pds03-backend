package ar.edu.unq.pds03backend.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "news")
class New(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false) var title: String,
    @Column(nullable = false) var message: String,
    @Column(nullable = false, name = "image_source") var imageSource: String,
    @Column(nullable = false, name = "image_alt") var imageAlt: String,
    @Column(nullable = false, name = "created_on") val createdOn: LocalDateTime,
    @Column(nullable = false) var writer: String,
    @Column(nullable = false) var email: String,
){
    companion object {
        const val createdOnFieldName = "createdOn"
    }
}
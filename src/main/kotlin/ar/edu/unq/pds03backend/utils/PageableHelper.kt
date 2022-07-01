package ar.edu.unq.pds03backend.utils

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

object PageableHelper {
    fun <T> toPage(list: List<T>, pageable: Pageable): Page<T> {
        val start = pageable.offset
        val end = Math.min(start + pageable.pageSize, list.size.toLong())
        return PageImpl(list.subList(start.toInt(), end.toInt()), pageable, list.size.toLong())
    }
}

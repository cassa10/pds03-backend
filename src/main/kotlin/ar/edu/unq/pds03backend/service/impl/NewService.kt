package ar.edu.unq.pds03backend.service.impl

import ar.edu.unq.pds03backend.dto.news.NewRequestDTO
import ar.edu.unq.pds03backend.exception.NewNotFoundException
import ar.edu.unq.pds03backend.model.New
import ar.edu.unq.pds03backend.repository.INewRepository
import ar.edu.unq.pds03backend.service.INewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class NewService(
    @Autowired private val newRepository: INewRepository
) : INewService {
    override fun getAll(): List<New> =
        newRepository.findAll(Sort.by(Sort.Direction.DESC, New.createdOnFieldName))

    override fun getAll(pageable: Pageable): Page<New>  =
            newRepository.findAll(pageable)

    override fun get(id: Long): New =
        getNewsById(id)

    @Transactional
    override fun create(dto: NewRequestDTO) {
        newRepository.save(
            New(
                title = dto.title,
                imageSource = dto.imageSource,
                imageAlt = dto.imageAlt,
                message = dto.message,
                createdOn = LocalDateTime.now(),
            )
        )
    }

    @Transactional
    override fun update(id: Long, dto: NewRequestDTO) {
        val new = getNewsById(id)
        new.title = dto.title
        new.message = dto.message
        new.imageAlt = dto.imageAlt
        new.imageSource = dto.imageSource
        newRepository.save(new)
    }

    @Transactional
    override fun delete(id: Long) {
        newRepository.delete(getNewsById(id))
    }

    private fun getNewsById(id: Long): New {
        val maybeNew = newRepository.findById(id)
        if (maybeNew.isPresent.not()) throw NewNotFoundException()
        return maybeNew.get()
    }
}
package cz.sparko.sprintBoard.repository.entity

import org.springframework.data.annotation.{Id, Version}

abstract class MongoEntity[CoreEntity](@Id id: String = null,
                                       @Version version: Long = 0) {
    def toCoreEntity: CoreEntity
}

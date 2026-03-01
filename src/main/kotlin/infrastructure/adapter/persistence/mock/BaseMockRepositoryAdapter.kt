package infrastructure.adapter.persistence.mock
import domain.model.common.IHaveID
import domain.port.BaseRepositoryPort
import shared.exception.EntityNotInRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

/** Адаптеры - эти типо DAO, у меня ушло полчаса чтобы до этого допереть*/

abstract class BaseMockRepositoryAdapter<T: IHaveID<T>> : BaseRepositoryPort<T>{
    private val storage = ConcurrentHashMap<Long, T>()
    private val idGenerator = AtomicLong(1)

    override fun findAll(): List<T> {
        return storage.values.toList()
    }

    override fun findById(id: Long): T? {
        if (!storage.containsKey(id)) {
            throw EntityNotInRepository("Entity with id $id not found")
        }

        return storage[id]
    }

    override fun create(entity: T): T{
        val id = entity.id ?: idGenerator.getAndIncrement()
        val newEntity = entity.withId(id)
        storage[id] = newEntity
        return newEntity
    }

    override fun update(entity: T): T {
        val id = entity.id ?: throw IllegalArgumentException("Cannot update entity without ID")

        if (!storage.containsKey(id)) {
            throw EntityNotInRepository("Entity with id $id not found")
        }

        storage[id] = entity
        return entity
    }

    override fun deleteById(id: Long): Boolean{
        return storage.remove(id) != null
    }
}
package delphi.backend.nrxcodegeneration.foundation.service;

import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.foundation.error.exception.NotFoundException;

public class CrudService<Repository extends CrudRepository<Entity, ID>, Entity, ID> {
    private Repository repo;

    public CrudService(Repository repo) {
        this.repo = repo;
    }

    /**
     * Fetch all entities.
     *
     * @return Iterable
     */
    public Iterable<Entity> fetchAll() {
        return repo.findAll();
    }

    /**
     * Add entity.
     *
     * @param entity
     * @return Entity
     */
    public Entity add(Entity entity) {
        return repo.save(entity);
    }

    /**
     * Update entity.
     *
     * @param entity
     * @return Entity
     */
    public Entity update(Entity entity) {
        return repo.update(entity);
    }

    /**
     * Find entity by id.
     *
     * @param id
     * @return Entity
     * @throws NotFoundException
     */
    public Entity findById(ID id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Id not found."));
    }

    /**
     * Delete entity.
     *
     * @param entity
     */
    public void delete(Entity entity) {
        repo.delete(entity);
    }

    /**
     * Get instance of repository.
     *
     * @return Repository
     */
    public final Repository getRepository() {
        return repo;
    }
}

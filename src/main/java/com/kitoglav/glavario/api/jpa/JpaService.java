package com.kitoglav.glavario.api.jpa;

import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

@Getter
public abstract class JpaService<T, ID> {
    private final JpaRepository<T, ID> repository;

    public JpaService(JpaRepository<T, ID>  repository) {
        this.repository = repository;
    }

    public T save(T t) {
        return repository.save(t);
    }

    public Collection<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> find(ID id) {
        return repository.findById(id);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

}

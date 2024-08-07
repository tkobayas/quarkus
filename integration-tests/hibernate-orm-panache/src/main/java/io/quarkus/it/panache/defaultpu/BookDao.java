package io.quarkus.it.panache.defaultpu;

import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class BookDao implements PanacheRepositoryBase<Book, Integer> {
}

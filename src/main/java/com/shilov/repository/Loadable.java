package com.shilov.repository;

import com.shilov.common.exceptions.RepositoryException;

public interface Loadable {

    void load() throws RepositoryException;
    void save() throws RepositoryException;
}

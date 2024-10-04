package com.dslabb1.dslabb1.dao;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {
    protected final Connection connection;

    public BaseDAO(Connection connection) {
        this.connection = connection;
    }

    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }
}

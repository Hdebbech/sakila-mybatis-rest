package com.debbech.sakila.repositories.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class StringSplitTypeHandler implements TypeHandler<Set<String>> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, Set<String> strings, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Set<String> getResult(ResultSet resultSet, String s) throws SQLException {
        String values = resultSet.getString(s);
        if(values == null || values.isEmpty()){
            return new HashSet<>();
        }
        String[] splitted = values.split(",");
        return  Set.of(splitted);
    }

    @Override
    public Set<String> getResult(ResultSet resultSet, int i) throws SQLException {
        String values = resultSet.getString(i);
        if(values == null || values.isEmpty()){
            return new HashSet<>();
        }
        String[] splitted = values.split(",");
        return  Set.of(splitted);
    }

    @Override
    public Set<String> getResult(CallableStatement callableStatement, int i) throws SQLException {
        String values = callableStatement.getString(i);
        if(values == null || values.isEmpty()){
            return new HashSet<>();
        }
        String[] splitted = values.split(",");
        return  Set.of(splitted);
    }
}

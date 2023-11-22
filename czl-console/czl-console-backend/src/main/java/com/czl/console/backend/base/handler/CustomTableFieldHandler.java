package com.czl.console.backend.base.handler;

import com.czl.console.backend.utils.AesUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: CHEN ZHI LING
 * Date: 2023/11/22
 * Description:
 */
@Service
@SuppressWarnings("unchecked")
public class CustomTableFieldHandler<T> extends BaseTypeHandler<T> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, AesUtils.encryptByAes((String) parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String columnValue = rs.getString(columnName);
        return ObjectUtils.isEmpty(columnValue) ? (T) columnValue : (T) AesUtils.decryptByAes(columnValue);

    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String columnValue = rs.getString(columnIndex);
        return ObjectUtils.isEmpty(columnValue) ? (T)columnValue : (T) AesUtils.decryptByAes(columnValue);

    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String columnValue = cs.getString(columnIndex);
        return ObjectUtils.isEmpty(columnValue) ? (T)columnValue : (T) AesUtils.decryptByAes(columnValue);
    }
}

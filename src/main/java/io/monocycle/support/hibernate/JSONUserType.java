package io.monocycle.support.hibernate;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUserType implements UserType, DynamicParameterizedType {

	private static final int[] SQL_TYPES = { Types.CLOB };

	private static ObjectMapper objectMapper = new ObjectMapper();

	private Class<?> type;

	public void setParameterValues(Properties parameters) {

		ParameterType reader = (ParameterType) parameters.get(PARAMETER_TYPE);

		try {

			type = reader.getReturnedClass();

		} catch (Exception e) {
			throw new HibernateException("Failed to create enum type", e);
		}

	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
		
		String json = rs.getString(names[0]);
		
		if (!rs.wasNull()) {
			try {
				return objectMapper.readValue(json, type);
			} catch (IOException e) {
				throw new HibernateException(e);
			}
		}
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, SQL_TYPES[0]);
		} else {
			try {
				st.setString(index, (objectMapper.writeValueAsString(value)));
			} catch (JsonProcessingException e) {
				throw new HibernateException(e);
			}
		}
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return deepCopy(cached);
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {

		if (value == null) {
			return null;
		}

		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new HibernateException(e);
		}

	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) deepCopy(value);
	}

	@Override
	public boolean equals(Object object1, Object object2) throws HibernateException {
		if (object1 == null) {
			return (object2 == null);
		}

		return (object1.equals(object2));
	}

	@Override
	public int hashCode(Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return deepCopy(original);
	}

	@Override
	public Class<?> returnedClass() {
		return type;
	}

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

}
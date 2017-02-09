package br.com.visent.analise.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.visent.analise.annotation.SearchConfig;
import br.com.visent.analise.annotation.SearchType;
import br.com.visent.analise.entity.AbstractEntity;

public class GenericDAO {

	@PersistenceContext
	protected EntityManager em;
	
	public <E> E find(Class<E> clazz, Long id) {
		return em.find(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> findAll(Class<E> clazz) {
		return em.createQuery("from " + clazz.getName()).getResultList();
	}

	public void persist(Serializable entity) {
		em.persist(entity);
	}
	
	public void merge(Serializable entity) {
		em.merge(entity);
	}
	
	public void remove(Serializable entity) {
		em.remove(entity);
	}
	
	public <E> void remove(Class<E> clazz, Long id) {
		em.remove(em.getReference(clazz, id));
	}
	
	@SuppressWarnings("unchecked")
	public <E> List<E> findByFilter(E entity) {
		List<E> entidades = null;
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("from ").append(entity.getClass().getName()).append(" entity where 1=1");
		List<Field> fields = getClassFields(entity.getClass());
		for (Field field : fields) {
			String fieldName = getFieldMappedName(field);
			Object methodReturn = getGetterMethodReturn(entity, field);
			if (methodReturn != null) {

				if (methodReturn instanceof AbstractEntity) {
					createSubEntitiesClauses("entity."+fieldName, sb, methodReturn, field, params);
				} else {
					sb.append(" and entity.");
					sb.append(fieldName);
					createWhereConditions(sb, params, field, methodReturn);
				}
			}
		}

		try {
			Query query = em.createQuery(sb.toString());
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i + 1, params.get(i));
			}
			entidades = query.getResultList();
		} catch (Exception e) {
			throw new RuntimeException("Nao foi possivel buscar por filtro: "+entity.getClass().getSimpleName(),e);
		}
		
		return entidades;
	}

	@SuppressWarnings("rawtypes")
	private List<Field> getClassFields(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		if (clazz.getSuperclass() != Object.class) {
			fields.addAll(getClassFields(clazz.getSuperclass()));
		}
		fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		return fields;
	}

	private void createSubEntitiesClauses(String prefix, StringBuilder sb, Object methodReturn, Field field, List<Object> params) {
		List<Field> subfields = getClassFields(methodReturn.getClass());
		for (Field subfield : subfields) {
			String subfieldName = getFieldMappedName(subfield);
			Object submethodReturn = getGetterMethodReturn(methodReturn, subfield);
			if (submethodReturn != null) {
				if (submethodReturn instanceof AbstractEntity) {
					createSubEntitiesClauses(prefix + "." +subfieldName, sb, submethodReturn, subfield, params);
				} else {
					sb.append(" and ");
					sb.append(prefix);
					sb.append(".");
					sb.append(subfieldName);
					createWhereConditions(sb, params, subfield, submethodReturn);
				}
			}
		}
	}

	private Object getGetterMethodReturn(Object entity, Field field) {
		String fieldName = field.getName();
		String getterName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
		try {
			Method getterMethod = entity.getClass().getMethod(getterName, new Class[0]);
			return getterMethod.invoke(entity, new Object[0]);
		} catch (Exception e) {
			return null;
		} 	
	}

	private void createWhereConditions(StringBuilder sb, List<Object> params, Field field, Object methodReturn) {
		SearchConfig annotation = field.getAnnotation(SearchConfig.class);
		if (annotation != null) {
			sb.append(annotation.type().value());
			sb.append("?");
			if (annotation.type() == SearchType.LIKE) {
				params.add("%" + methodReturn + "%");
			} else if(annotation.type() == SearchType.LIKE_END){
				params.add(methodReturn + "%");
			} else if(annotation.type() == SearchType.LIKE_BEGIN){
				params.add("%" + methodReturn);
			} else {
				params.add(methodReturn);
			}
		} else {
			sb.append(" = ?");
			params.add(methodReturn);
		}
	}
	
	private String getFieldMappedName(Field field) {
		SearchConfig annotation = field.getAnnotation(SearchConfig.class);
		if (annotation != null && !annotation.mappedName().isEmpty()) {
			return annotation.mappedName();
		}
		return field.getName();
	}
	
}

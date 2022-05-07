package me.itstautvydas.debugstick.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import me.itstautvydas.debugstick.helper.MultiMap;

public class Reflection {

	@SuppressWarnings("unchecked")
	public static <T> T getField(Class<?> clazz, String field, Object instance, Class<T> retur) {
		try {
			Method method = clazz.getDeclaredMethod(field);
			method.setAccessible(true);
			return (T)method.invoke(instance);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Class<?> getClass(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public static Class<?> getClass(String name, boolean printerror) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			if (printerror)
				e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(String clazz, Class<T> retur, MultiMap<Class<?>, Object> parameters, Object def) {
		try {
			Class<?> c = Class.forName(clazz);
			return (T)c.getConstructor(getParameterTypes(parameters)).newInstance(getParameterObjects(parameters));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			if (def != null)
				return (T)def;
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getMethod(Object instance, String method, Class<T> clazz, @Nullable MultiMap<Class<?>, Object> parameters, Object def) {
		try {
			if (parameters != null) {
				Class<?>[] parameterTypes = getParameterTypes(parameters);
				Object[] args = getParameterObjects(parameters);
				
				return (T)instance.getClass().getDeclaredMethod(method, parameterTypes).invoke(instance, args);
			} else {
				return (T)instance.getClass().getDeclaredMethod(method).invoke(instance);
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			if (def != null)
				return (T)def;
		}
		
		return null;
	}
	
	//private
	private static Class<?>[] getParameterTypes(MultiMap<Class<?>, Object> parameters) {
		return parameters.getKeys().toArray(new Class<?>[] {});
	}
	
	private static Object[] getParameterObjects(MultiMap<Class<?>, Object> parameters) {
		return parameters.getValues().toArray(new Object[] {});
	}
}

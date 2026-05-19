package server;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OperationRegistry implements OperationExecutor {
    private final Object target;
    private final Map<String, Method> operations;

    public OperationRegistry(Object target) {
        this.target = target;
        this.operations = Collections.unmodifiableMap(registrar(target));
    }

    @Override
    public Double ejecutar(String operacion, double a, double b) {
        if (operacion == null) {
            return null;
        }
        Method method = operations.get(normalizar(operacion));
        if (method == null) {
            return null;
        }
        try {
            Object result = method.invoke(target, a, b);
            return (Double) result;
        } catch (ReflectiveOperationException ex) {
            return null;
        }
    }

    public Map<String, Method> getOperations() {
        return operations;
    }

    private static Map<String, Method> registrar(Object target) {
        Map<String, Method> map = new HashMap<>();
        for (Method method : target.getClass().getDeclaredMethods()) {
            Operacion annotation = method.getAnnotation(Operacion.class);
            if (annotation == null) {
                continue;
            }
            if (!isSignatureValid(method)) {
                continue;
            }
            for (String alias : annotation.value()) {
                if (alias == null || alias.trim().isEmpty()) {
                    continue;
                }
                map.putIfAbsent(normalizar(alias), method);
            }
        }
        return map;
    }

    private static boolean isSignatureValid(Method method) {
        Class<?>[] params = method.getParameterTypes();
        if (params.length != 2 || params[0] != double.class || params[1] != double.class) {
            return false;
        }
        return Double.class.equals(method.getReturnType());
    }

    private static String normalizar(String operacion) {
        return operacion.trim().toUpperCase();
    }
}


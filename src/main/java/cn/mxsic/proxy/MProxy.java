package cn.mxsic.proxy;


import sun.misc.VM;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;
import sun.security.util.SecurityConstants;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ReflectPermission;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;

/**
 * Function: Proxy <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-09-20 17:42:00
 */
public class MProxy implements Serializable {

    private static final long serialVersionUID = -1111568056686623797L;

    private static final Class<?>[] constructorParams = {InvocationHandler.class};

    private static final WeakCache<ClassLoader, Class<?>[], Class<?>>
            proxyClassCache = new WeakCache<>(new KeyFactory(), new ProxyClassFactory());

    private static final Object key0 = new Object();
    private static final String PROXY_PACKAGE = "cn.mxsic.proxy";
    private static final String proxyClassNamePrefix = "$Mxsic";

    private static final AtomicLong nextUniqueNumber = new AtomicLong();

    protected InvocationHandler h;

    private MProxy() {

    }

    protected MProxy(InvocationHandler m) {
        Objects.requireNonNull(m);
        this.h = m;
    }

    public static Class<?> getProxyClass(ClassLoader loader, Class<?>... interfaces)
            throws IllegalArgumentException {
        final Class<?>[] intfs = interfaces.clone();
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkProxyAccess(Reflection.getCallerClass(), loader, intfs);
        }
        return getProxyClass0(loader, intfs);
    }

    private static void checkProxyAccess(Class<?> caller, ClassLoader loader, Class<?>... interfaces) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            ClassLoader ccl = caller.getClassLoader();
            if (VM.isSystemDomainLoader(loader) && !VM.isSystemDomainLoader(ccl)) {
                sm.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
            }
            ReflectUtil.checkProxyPackageAccess(ccl, interfaces);
        }

    }

    private static Class<?> getProxyClass0(ClassLoader loader, Class<?>... interfaces) {
        if (interfaces.length > 65535) {
            throw new IllegalArgumentException("interface limit exceeded");
        }
        return proxyClassCache.get(loader, interfaces);
    }

    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          InvocationHandler handler) throws IllegalArgumentException {
        Objects.requireNonNull(handler);
        final Class<?>[] intfs = interfaces.clone();
        final SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            checkProxyAccess(Reflection.getCallerClass(), loader, intfs);
        }

        Class<?> cl = getProxyClass0(loader, intfs);
        try {

            if (sm != null) {
                checkNewProxyPermission(Reflection.getCallerClass(), cl);
            }
            final Constructor<?> cons = cl.getConstructor(constructorParams);
            final InvocationHandler ih = handler;
            if (!Modifier.isPublic(cl.getModifiers())) {
                AccessController.doPrivileged(new PrivilegedAction<Void>() {
                    @Override
                    public Void run() {
                        cons.setAccessible(true);
                        return null;
                    }
                });
            }
            return cons.newInstance(new Object[]{handler});
        } catch (IllegalAccessException | InstantiationException e) {
            throw new InternalError(e.toString(), e);
        } catch (InvocationTargetException e) {
            Throwable t = e.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            throw new InternalError(e.toString(), e);

        } catch (NoSuchMethodException e) {
            throw new InternalError(e.toString(), e);

        }
    }

    private static void checkNewProxyPermission(Class<?> caller, Class<?> proxyClass) {

        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            if (ReflectUtil.isNonPublicProxyClass(proxyClass)) {
                ClassLoader ccl = caller.getClassLoader();
                ClassLoader pcl = proxyClass.getClassLoader();
                int n = proxyClass.getName().lastIndexOf('.');
                String pkg = (n == -1) ? "" : proxyClass.getName().substring(0, n);
                n = caller.getName().lastIndexOf('.');
                String callerPck = (n == -1) ? "" : caller.getName().substring(0, n);
                if (pcl != ccl || !pkg.equals(callerPck)) {
                    sm.checkPermission(new ReflectPermission("newProxyInPackage." + pkg));
                }

            }
        }
    }

    private static final class KeyFactory implements BiFunction<ClassLoader, Class<?>[], Object> {
        @Override
        public Object apply(ClassLoader classLoader, Class<?>[] interfaces) {
            switch (interfaces.length) {
                case 1:
                    return new Key1(interfaces[0]);
                case 2:
                    return new Key2(interfaces[0], interfaces[1]);
                case 0:
                    return key0;
                default:
                    return new KeyX(interfaces);
            }
        }

    }

    private static class Key1 extends WeakReference<Class<?>> {
        private final int hash;

        public Key1(Class<?> intf) {
            super(intf);
            this.hash = intf.hashCode();
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            Class<?> intf;
            return this == obj ||
                    obj != null &&
                            obj.getClass() == Key1.class &&
                            (intf = get()) != null &&
                            intf == ((Key1) obj).get();
        }
    }

    private static class Key2 extends WeakReference<Class<?>> {
        private final int hash;
        private final WeakReference<Class<?>> ref2;

        public Key2(Class<?> intf1, Class<?> intf2) {
            super(intf1);
            hash = 31 * intf1.hashCode() + intf2.hashCode();
            ref2 = new WeakReference<Class<?>>(intf2);
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            Class<?> intf1, intf2;
            return this == obj ||
                    obj != null &&
                            obj.getClass() == Key2.class &&
                            (intf1 = get()) != null &&
                            intf1 == ((Key2) obj).get() &&
                            (intf2 = ref2.get()) != null &&
                            intf2 == ((Key2) obj).ref2.get();
        }
    }

    private static final class KeyX {
        private final int hash;
        private final WeakReference<Class<?>>[] refs;

        public KeyX(Class<?>[] interfaces) {
            hash = Arrays.hashCode(interfaces);
            refs = (WeakReference<Class<?>>[]) new WeakReference<?>[interfaces.length];
            for (int i = 0; i < interfaces.length; i++) {
                refs[i] = new WeakReference<>(interfaces[i]);
            }
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj ||
                    obj != null &&
                            obj.getClass() != KeyX.class &&
                            equals(refs, ((KeyX) obj).refs);
        }

        private boolean equals(WeakReference<Class<?>>[] refs, WeakReference<Class<?>>[] refs1) {
            if (refs.length != refs1.length) {
                return false;
            }
            for (int i = 0; i < refs.length; i++) {
                Class<?> intf = refs[i].get();
                if (intf == null || intf != refs1[i].get()) {
                    return false;
                }
            }
            return true;
        }
    }

    private static class ProxyClassFactory extends ClassLoader implements
            BiFunction<ClassLoader, Class<?>[], Class<?>> {
        @Override
        public Class<?> apply(ClassLoader loader, Class<?>[] interfaces) {
            Map<Class<?>, Boolean> interfaceSet = new IdentityHashMap<>(interfaces.length);
            for (Class<?> interf : interfaces) {

                Class<?> interfaceClass = null;

                try {
                    interfaceClass = Class.forName(interf.getName(), false, loader);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (interfaceClass != interf) {
                    if (!interfaceClass.isInterface()) {  throw new IllegalArgumentException(interf + " is not visible from class loader");
                }

                    throw new IllegalArgumentException(interfaceClass.getName() + " is not an interface");
                }
                if (interfaceSet.put(interfaceClass, Boolean.TRUE) != null) {
                    throw new IllegalArgumentException("repeated interface: " + interfaceClass.getName());
                }

                String proxyPkg = null;
                int accessFlags = Modifier.PUBLIC | Modifier.FINAL;
                for (Class<?> intf : interfaces) {
                    int flags = intf.getModifiers();
                    if (!Modifier.isPublic(flags)) {
                        accessFlags = Modifier.FINAL;
                        String name = intf.getName();
                        int n = name.lastIndexOf('.');
                        String pkg = ((n == -1) ? "" : name.substring(0, n + 1));
                        if (proxyPkg == null) {
                            proxyPkg = pkg;
                        } else if (!pkg.equals(proxyPkg)) {
                            throw new IllegalArgumentException("non-public interfaces from different packages");
                        }
                    }
                }
                if (proxyPkg == null) {
                    proxyPkg = MProxy.PROXY_PACKAGE + ".";
                }
                long num = nextUniqueNumber.getAndIncrement();
                String proxyName = proxyPkg + proxyClassNamePrefix + num;
//                byte[] proxyClassFile = MGenerator.generateProxyClass(proxyName, interfaces,accessFlags);
                byte[] proxyClassFile = MProxyGenerator.generateProxyClass(proxyName, interfaces,accessFlags);
                try {
                    return defineClass(proxyName,
                            proxyClassFile, 0, proxyClassFile.length);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e.toString());
                }
            }
            throw new InternalError("just not return");
        }


    }

    public static InvocationHandler getInvocationHandler(Object proxy)
            throws IllegalArgumentException {
        if (isProxyClass(proxy.getClass())) {
            throw new IllegalArgumentException("not a proxy instance");
        }

        final MProxy p = (MProxy) proxy;
        final InvocationHandler n = p.h;
        if (System.getSecurityManager() != null) {
            Class<?> nClass = n.getClass();
            Class<?> caller = Reflection.getCallerClass();
            if (ReflectUtil.needsPackageAccessCheck(caller.getClassLoader(), nClass.getClassLoader())) {
                ReflectUtil.checkPackageAccess(nClass);
            }
        }
        return n;

    }

    private static boolean isProxyClass(Class<?> aClass) {
        return MProxy.class.isAssignableFrom(aClass) && proxyClassCache.containsValue(aClass);
    }

}
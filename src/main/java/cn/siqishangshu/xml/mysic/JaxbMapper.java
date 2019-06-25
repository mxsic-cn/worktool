package cn.siqishangshu.xml.mysic;

/**
 * Created by siqishangshu on 17/10/13.
 *
 * this mapper only for citic convert between xml object
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class JaxbMapper {

    private static final String ENCODING = "GBK";

    private static ConcurrentMap<Class, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class, JAXBContext>();

    /**
     * Java Object->Xml without encoding.
     */
    public static String objectToXml(Object object) {
        return objectToXml(object, ENCODING);
    }

    /**
     * Java Object->Xml with encoding.
     */
    public static String objectToXml(Object object, String encoding){
        Class clazz = Reflections.getUserClass(object);
        try {
            StringWriter writer = new StringWriter();
            createMarshaller(clazz, encoding).marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Java Collection->Xml without encoding, 特别支持Root Element是Collection的情形.
     */
    public static String collectionToXml(Collection<?> object, String rootName, Class clazz) {
        return collectionToXml(object, rootName, clazz, ENCODING);
    }

    /**
     * Java Collection->Xml with encoding, 特别支持Root Element是Collection的情形.
     */
    public static String collectionToXml(Collection<?> object, String objectName, Class clazz, String encoding) {
        try {
            CollectionWrapper wrapper = new CollectionWrapper();
            wrapper.collection = object;

            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(objectName), CollectionWrapper.class, wrapper);

            StringWriter writer = new StringWriter();
            createMarshaller(clazz, encoding).marshal(wrapperElement, writer);

            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Xml->Java Object.
     */
    public static <T> T xmlToObject(String xml, Class<T> clazz) {
        try {
            StringReader reader = new StringReader(xml);
            return (T) createUnmarshaller(clazz).unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建Marshaller并设定encoding(可为null).
     * 线程不安全，需要每次创建或pooling。
     */
    public static Marshaller createMarshaller(Class clazz, String encoding) {
        try {
            JAXBContext jaxbContext = getJaxbContext(clazz);

            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if (StringUtils.isNotBlank(encoding)) {
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }

            return marshaller;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建UnMarshaller.
     * 线程不安全，需要每次创建或pooling。
     */
    public static Unmarshaller createUnmarshaller(Class clazz) {
        try {
            JAXBContext jaxbContext = getJaxbContext(clazz);
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static JAXBContext getJaxbContext(Class clazz) {
        Validate.notNull(clazz, "'clazz' must not be null");
        JAXBContext jaxbContext = jaxbContexts.get(clazz);
        if (jaxbContext == null) {
            try {
                jaxbContext = JAXBContext.newInstance(clazz, CollectionWrapper.class);
                jaxbContexts.putIfAbsent(clazz, jaxbContext);
            } catch (JAXBException ex) {
                throw new RuntimeException("Could not instantiate JAXBContext for class [" + clazz + "]: " + ex.getMessage(), ex);
            }
        }
        return jaxbContext;
    }

    /**
     * 封装Root Element 是 Collection的情况. 
     */
    public static class CollectionWrapper {

        @XmlAnyElement
        protected Collection<?> collection;
    }

}
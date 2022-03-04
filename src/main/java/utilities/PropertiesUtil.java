package utilities;

import java.io.InputStreamReader;
import java.util.*;

import javax.management.RuntimeErrorException;

public class PropertiesUtil {
	private static Properties pps;

    private PropertiesUtil() {

    }

    /**
     * This is to load properties from specified property file into {@link Properties}.
     * <br> If there is exception, throw run time exception with message 'cannot load properties file ' + &#60;file name&#62;
     *
     * @param fileName property file name
     * @return Properties object with items in property file
     */
    public static Properties loadProperties(String fileName) {

        Properties pps = new Properties();
        try { 
            pps.load(PropertiesUtil.class.getResourceAsStream(fileName));
        } catch (Exception e) {
            throw new RuntimeException("cannot load properties file " + fileName);
        }

        return pps;

    }

    /**
     * This is to load properties from specified property file into {@link Properties}.
     * <br> If there is exception, throw run time exception with message 'cannot load properties file ' + &#60;file name&#62;
     *
     * @param fileName property file name
     * @return Properties object with items in property file
     */
    public static Properties loadProperties(String fileName, String charSetName) {

        Properties pps = new Properties();
        try {
            pps.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName), charSetName));
        } catch (Exception e) {
        		throw new RuntimeException("cannot load properties file " + fileName);
        }

        return pps;

    }

    /**
     * This is to load properties from specified property file into {@link Map}.
     *
     * @param fileName property file name
     * @return Map with items in property file - format: propertyName1:propertyValue1; propertyName2:propertyValue2
     */
    public static Map<String, String> loadPropertiesAsMap(String fileName) {
        Properties pps = loadProperties(fileName);

        Map<String, String> result = new HashMap<>();

        Set<Object> nameSet = pps.keySet();
        for (Iterator<Object> it = nameSet.iterator(); it.hasNext(); ) {
            String name = (String) it.next();
            String value = pps.getProperty(name);

            result.put(name, value);

        }
        return result;
    }

    /**
     * This is to get properties String value
     *
     * @param pps properties to get value from
     * @param key the key of properties
     * @return String value
     */
    public static String getStringValue(Properties pps, String key) {
        Object value = pps.get(key);
        if (value == null) {
            return null;
        }

        return value.toString();
    }

    public static String getValue(Properties pps, String ppsKeyOrActuralValue) {
        String value = getStringValue(pps, ppsKeyOrActuralValue);

        if (value == null) {
            value = ppsKeyOrActuralValue;
        }
        return value;
    }

    /**
     * This is to get properties
     *
     * @return
     */
    public static Properties getProperties() {
        return PropertiesUtil.pps;
    }

    /**
     * This is to set properties
     *
     * @param pps the new properties want to set
     */
    public static void setProperties(Properties pps) {
        PropertiesUtil.pps = pps;
    }
}

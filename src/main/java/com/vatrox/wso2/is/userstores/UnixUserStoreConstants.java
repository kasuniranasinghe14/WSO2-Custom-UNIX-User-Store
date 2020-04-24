package com.vatrox.wso2.is.userstores;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.api.Property;
import org.wso2.carbon.user.core.UserCoreConstants;
import org.wso2.carbon.user.core.UserStoreConfigConstants;

import java.util.ArrayList;
import java.util.Properties;

public class UnixUserStoreConstants {

    private static Log log = LogFactory.getLog(UnixUserStoreConstants.class);

    public static final ArrayList<Property> CUSTOM_UM_MANDATORY_PROPERTIES = new ArrayList<Property>();
    public static final ArrayList<Property> CUSTOM_UM_OPTIONAL_PROPERTIES = new ArrayList<Property>();
    public static final ArrayList<Property> CUSTOM_UM_ADVANCED_PROPERTIES = new ArrayList<Property>();
    public static final String HOSTNAME = "hostname";
    public static final String PORT = "port";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String SUDO = "sudo";
    public static final String ERROR_READ_ONLY_MODE = "User store is operating in read only mode. Cannot write into the user store.";

    private static Properties properties = new Properties();

    static {
        log.info("Starting UserStoreConstants");

        setMandatoryProperty(HOSTNAME, "Hostname", "localhost", "Hostname", false);
        setMandatoryProperty(PORT, "Port", "22", "Port", false);
        setMandatoryProperty(USERNAME, "Admin Username", "user", "Admin Username", false);
        setMandatoryProperty(PASSWORD, "Password", "", "Password", true);
        setMandatoryProperty(SUDO, "Sudo User", "false", "Sudo User", false);
        setOptionalProperty(UserStoreConfigConstants.disabled, "Disable UserStore", "false", "Whether user store is disabled", false);
        setAdvancedProperty(UserCoreConstants.RealmConfig.PROPERTY_READ_ONLY,"Read Only","false", "Read Only UserStore",false);
    }

    private static void setOptionalProperty(String name, String displayName, String value, String description,
                                            boolean encrypt) {
        String propertyDescription = displayName + "#" + description;
        if (encrypt) {
            propertyDescription += "#encrypt";
        }
        Property property = new Property(name, value, propertyDescription, null);
        CUSTOM_UM_OPTIONAL_PROPERTIES.add(property);

    }

    private static void setMandatoryProperty(String name, String displayName, String value, String description,
                                             boolean encrypt) {
        String propertyDescription = displayName + "#" + description;
        if (encrypt) {
            propertyDescription += "#encrypt";
        }
        Property property = new Property(name, value, propertyDescription, null);
        CUSTOM_UM_MANDATORY_PROPERTIES.add(property);

    }

    private static void setAdvancedProperty(String name, String displayName, String value, String description,
                                            boolean encrypt) {
        String propertyDescription = displayName + "#" + description;
        if (encrypt) {
            propertyDescription += "#encrypt";
        }
        Property property = new Property(name, value, propertyDescription, null);
        CUSTOM_UM_ADVANCED_PROPERTIES.add(property);
    }

    public static String getProperty(String key) {
        String property = properties.getProperty(key);
        if (log.isDebugEnabled()) {
            log.debug("getProperty " + property);
        }
        return property;
    }
}

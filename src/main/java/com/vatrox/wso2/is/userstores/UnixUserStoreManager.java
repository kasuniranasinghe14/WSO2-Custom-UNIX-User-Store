package com.vatrox.wso2.is.userstores;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.api.Properties;
import org.wso2.carbon.user.api.Property;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.core.UserCoreConstants;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.carbon.user.core.common.AbstractUserStoreManager;
import org.wso2.carbon.user.core.common.RoleContext;
import org.wso2.carbon.user.core.model.UserClaimSearchEntry;
import org.wso2.carbon.user.core.profile.ProfileConfigurationManager;
import org.wso2.carbon.user.core.tenant.Tenant;
import org.wso2.carbon.user.core.util.UserCoreUtil;
import org.wso2.carbon.utils.Secret;

import com.jcraft.jsch.JSchException;

public class UnixUserStoreManager extends AbstractUserStoreManager {

    private static Log log = LogFactory.getLog(UnixUserStoreManager.class);

    public UnixUserStoreManager() {
    }

    public UnixUserStoreManager(RealmConfiguration realmConfig, Map<String, Object> properties,
                                ClaimManager claimManager, ProfileConfigurationManager profileManager, UserRealm realm, Integer tenantId,
                                boolean skipInitData) throws UserStoreException {
        this(realmConfig, tenantId);
        UnixUserStoreUtils.setConfig(realmConfig);
        this.claimManager = claimManager;
        this.userRealm = realm;
        if (log.isDebugEnabled()) {
            log.debug("Started 2:" + System.currentTimeMillis());
        }
    }

    public UnixUserStoreManager(RealmConfiguration realmConfig, Map<String, Object> properties,
                                ClaimManager claimManager, ProfileConfigurationManager profileManager, UserRealm realm, Integer tenantId)
            throws UserStoreException {
        this(realmConfig, properties, claimManager, profileManager, realm, tenantId, false);
        UnixUserStoreUtils.setConfig(realmConfig);
        if (log.isDebugEnabled()) {
            log.debug("Started 3:" + System.currentTimeMillis());
        }
    }

    public UnixUserStoreManager(RealmConfiguration realmConfig, int tenantId) throws UserStoreException {
        this.realmConfig = realmConfig;
        this.tenantId = tenantId;
        UnixUserStoreUtils.setConfig(realmConfig);
        if (log.isDebugEnabled()) {
            log.debug("Started 4:" + System.currentTimeMillis());
        }
    }

    //Added this because if not, there is an error with an “UM_DOMAIN_ID”;
    // SQL statement: INSERT INTO UM_UUID_DOMAIN_MAPPER (UM_USER_ID, UM_DOMAIN_ID, UM_TENANT_ID) VALUES (?, (SELECT UM_DOMAIN_ID FROM UM_DOMAIN WHERE UM_DOMAIN_NAME = ? AND UM_TENANT_ID = ?), ?) [23502-199]
//    @Override
//    public boolean isUniqueUserIdEnabled() {
//        return false;
//    }

    // I added this because the original is not implemented!
//    @Override
//    protected String doGetUserNameFromUserIDWithID(String userID) throws UserStoreException {
//        String methodName = new Throwable().getStackTrace()[0].getMethodName();
//        if (log.isDebugEnabled()) {
//            log.debug(this.getClass() + ": "+ methodName);
//        }
//        return userID;
//    }
//
//    @Override
//    protected List<User> doListUsersWithID(String filter, int maxItemLimit)
//            throws UserStoreException {
//        List<User> lst = new LinkedList<User>();
//        String methodName = new Throwable().getStackTrace()[0].getMethodName();
//        if (log.isDebugEnabled()) {
//            log.debug(this.getClass() + ": "+ methodName);
//        }
//        return null;
//    }

    @Override
    public Properties getDefaultUserStoreProperties() {
        Properties properties = new Properties();
        properties.setMandatoryProperties(UnixUserStoreConstants.CUSTOM_UM_MANDATORY_PROPERTIES
                .toArray(new Property[UnixUserStoreConstants.CUSTOM_UM_MANDATORY_PROPERTIES.size()]));
        properties.setOptionalProperties(UnixUserStoreConstants.CUSTOM_UM_OPTIONAL_PROPERTIES
                .toArray(new Property[UnixUserStoreConstants.CUSTOM_UM_OPTIONAL_PROPERTIES.size()]));
        properties.setAdvancedProperties(UnixUserStoreConstants.CUSTOM_UM_ADVANCED_PROPERTIES
                .toArray(new Property[UnixUserStoreConstants.CUSTOM_UM_ADVANCED_PROPERTIES.size()]));
        if (log.isDebugEnabled()) {
            log.debug("get Default properties: ");
            for (Property property : properties.getOptionalProperties()) {
                    log.debug("Optional UserStoreProperty [" + property.getName() + "]=[" + property.getValue() + "]");
            }
            for (Property property : properties.getMandatoryProperties()) {
                    log.debug("Mandatory UserStoreProperty [" + property.getName() + "]=[" + property.getValue() + "]");
            }
            for (Property property : properties.getAdvancedProperties()) {
                    log.debug("Advance UserStoreProperty [" + property.getName() + "]=[" + property.getValue() + "]");
            }
        }
        return properties;
    }

    @Override
    public String[] getAllProfileNames() throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    public String[] getProfileNames(String arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    public Map<String, String> getProperties(Tenant arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    public RealmConfiguration getRealmConfiguration() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return this.realmConfig;
    }

    @Override
    public int getTenantId() throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return this.tenantId;
    }

    @Override
    public int getTenantId(String username) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return this.tenantId;
    }

    @Override
    public int getUserId(String arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return 0;
    }

    @Override
    public boolean isBulkImportSupported() throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return false;
    }

    @Override
    public boolean isReadOnly() throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        if ("true".equalsIgnoreCase(realmConfig.getUserStoreProperty(UserCoreConstants.RealmConfig.PROPERTY_READ_ONLY))) {
            return true;
        }
        return false;

    }

    @Override
    public void addRememberMe(String arg0, String arg1) throws org.wso2.carbon.user.api.UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    public Map<String, String> getProperties(org.wso2.carbon.user.api.Tenant tenant)
            throws org.wso2.carbon.user.api.UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return getProperties((Tenant) tenant);
    }

    @Override
    public boolean isMultipleProfilesAllowed() {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return false;
    }

    @Override
    public boolean isValidRememberMeToken(String arg0, String arg1) throws org.wso2.carbon.user.api.UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return false;
    }

    @Override
    protected RoleContext createRoleContext(String arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    protected void doAddRole(String arg0, String[] arg1, boolean arg2) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected void doAddUser(String arg0, Object arg1, String[] arg2, Map<String, String> arg3, String arg4,
                             boolean arg5) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected boolean doAuthenticate(String arg0, Object arg1) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return false;
    }

    @Override
    protected boolean doCheckExistingRole(String arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return false;
    }

    @Override
    protected boolean doCheckExistingUser(String arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return true;
    }

    @Override
    public boolean doCheckIsUserInRole(String arg0, String arg1) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return false;
    }

    @Override
    protected void doDeleteRole(String arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected void doDeleteUser(String arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected void doSetUserClaimValue(String userName, String claimURI, String claimValue, String profileName) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
    }

    @Override
    protected void doSetUserClaimValues(String userName, Map<String, String> claims, String profileName) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
    }

    @Override
    protected void doDeleteUserClaimValue(String arg0, String arg1, String arg2) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected void doDeleteUserClaimValues(String arg0, String[] arg1, String arg2) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected String[] doGetDisplayNamesForInternalRole(String[] arg0) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    protected String[] doGetExternalRoleListOfUser(String arg0, String arg1) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    protected String[] doGetRoleNames(String arg0, int arg1) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    protected String[] doGetSharedRoleListOfUser(String arg0, String arg1, String arg2) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    protected String[] doGetSharedRoleNames(String arg0, String arg1, int arg2) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    protected String[] doGetUserListOfRole(String arg0, String arg1) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    protected String[] doListUsers(String filter, int maxItemLimit) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        List<String> lst = new LinkedList<String>();
        String[] users = new String[0];
        String[] response = new String[0];
        String command = "dscl . list /Users";
        try {
            String result = UnixUserStoreUtils.runCommand(command);
            response = result.split("\r\n");
            //int i = 0;
            //String domainName = realmConfig.getUserStoreProperty(UserStoreConfigConstants.DOMAIN_NAME);
            String domainName = realmConfig.getUserStoreProperty(UserCoreConstants.RealmConfig.PROPERTY_DOMAIN_NAME);
            for (String value : response) {
                value = UserCoreUtil.addDomainToName(value, domainName);
                lst.add(value);
                //response[i] = domainName + "/" + value;
                //i += 1;
            }
            if (lst.size() > 0) {
                users = lst.toArray(new String[lst.size()]);
                Arrays.sort(users);
            }
        } catch (JSchException e) {
            String msg = "Error at doUpdateCredentialByAdmin:" + command;
            if (log.isDebugEnabled()) {
                log.debug(msg, e);
            }
            throw new UserStoreException(methodName + " Failure", e);
        }
        return users;
    }

    @Override
    protected void doUpdateCredential(String userName, Object newCredential, Object oldCredential)
            throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
    }

    @Override
    protected void doUpdateCredentialByAdmin(String username, Object newCredential) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        String command = "sudo security set-keychain-password -o newpassword -p "+ String.copyValueOf(((Secret) newCredential).getChars())
                + " /Users/testuser/Library/Keychains/login.keychain";
        try {
            String result = UnixUserStoreUtils.runCommand(command);
            if (log.isDebugEnabled()) {
                log.debug("doUpdateCredentialByAdmin:" + result);
            }
        } catch (JSchException e) {
            String msg = "Error at doUpdateCredentialByAdmin:" + command;
            if (log.isDebugEnabled()) {
                log.debug(msg, e);
            }
            throw new UserStoreException("doUpdateCredentialByAdmin Failure", e);
        }
    }

    @Override
    protected void doUpdateRoleListOfUser(String arg0, String[] arg1, String[] arg2) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected void doUpdateRoleName(String arg0, String arg1) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected void doUpdateUserListOfRole(String arg0, String[] arg1, String[] arg2) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

    }

    @Override
    protected String getClaimAtrribute(String claimURI, String identifier, String domainName) throws org.wso2.carbon.user.api.UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }

        if (claimURI.equalsIgnoreCase("http://wso2.org/claims/userid") || claimURI.equalsIgnoreCase("http://wso2.org/claims/username")){
            return "scimId";
        }
        return null;
    }

    @Override
    protected List<String> getMappingAttributeList(List<String> claimList) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    public UserClaimSearchEntry[] getUsersClaimValues(String[] userNames, String[] claims, String profileName) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    protected String[] getUserListFromProperties(String property, String value, String profileName) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName + " property=" + property + " value=" + value + " profileName=" + profileName);
        }
        if (profileName == null) {
            profileName = UserCoreConstants.DEFAULT_PROFILE;
        }
        if (value == null) {
            throw new IllegalArgumentException(methodName + " Filter value cannot be null");
        }
        String[] response = new String[1];
        String command = "";
        if (property.equalsIgnoreCase("uid") || property.equalsIgnoreCase("scimId") ) {
            command = "cut -d: -f1 /etc/passwd | grep " + value + " | sed -n -e 'H;${x;s/\\n/,/g;s/^,//;p;}'";
        } else {
            return response;
        }
        List<String> lst = new LinkedList<String>();
        try {
            String result = UnixUserStoreUtils.runCommand(command).trim();
            response[0] = new String(value);

            if (log.isDebugEnabled()) {
                log.debug("UnixUserStoreManager: " + methodName + " result=" + result + " response=" + response[0]);
            }
            //if (result.equals(value)) {


            //}
            //if (log.isDebugEnabled()) {
            //    log.debug("UnixUserStoreManager: " + methodName + " out if");
            //}
            //String domainName = realmConfig.getUserStoreProperty(UserStoreConfigConstants.DOMAIN_NAME);
            //    response[i] = domainName + "/" + value;
        } catch (JSchException e) {
            String msg = "Error at "+ methodName + ":" + command;
            if (log.isDebugEnabled()) {
                log.debug(msg, e);
            }
            throw new UserStoreException(methodName + " Failure", e);
        }
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName + " out response=" + response[0] + " cantidad=" + response.length);
        }
        return response;
    }


    @Override
    protected String[] doGetInternalRoleListOfUser(String userName, String filter) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        return null;
    }

    @Override
    public String[] getRoleListOfUser(String userName) throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName);
        }
        String[] roles = new String[0];
        return roles;
    }

    @Override
    protected Map<String, String> getUserPropertyValues(String userName, String[] propertyNames, String profileName)
            throws UserStoreException {
        String methodName = new Throwable().getStackTrace()[0].getMethodName();
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager: " + methodName + " userName:" + userName+ " propertyNames:" + Arrays.toString(propertyNames));
        }
        if (profileName == null) {
            profileName = UserCoreConstants.DEFAULT_PROFILE;
        }
        Map<String, String> map = new HashMap<String, String>();
        String value = "";
        for (String s: propertyNames)
        {
            switch (s) {
                case "userId": case "externalid":
                    value = userName;
                    break;
                case "scimId": case "uid":
                    value = realmConfig.getUserStoreProperty(UserCoreConstants.RealmConfig.PROPERTY_DOMAIN_NAME) + "/" + userName;
                    break;
                case "resourceType":
                    value = "Users";
                    break;
                case "im":
                    value = "im";
                    break;
                case "modified":
                    value = "modified";
                    break;
                case "location":
                    value = "location";
                    break;
                default:
                    value = "";
                    break;
            }
            map.put(s, value);
        }
        return map;
    }

}

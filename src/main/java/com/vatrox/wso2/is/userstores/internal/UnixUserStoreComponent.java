package com.vatrox.wso2.is.userstores.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
//import org.osgi.service.component.annotations.*;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;
import com.vatrox.wso2.is.userstores.UnixUserStoreManager;


//@Component(name="custom.user.store.manager.dscomponent", immediate = true)

/**
 * @scr.component name="custom.user.store.manager.dscomponent" immediate=true
 * @scr.reference name="user.realmservice.default"
 * interface="org.wso2.carbon.user.core.service.RealmService"
 * cardinality="1..1" policy="dynamic" bind="setRealmService"
 * unbind="unsetRealmService"
 */
public class UnixUserStoreComponent {
    private static Log log = LogFactory.getLog(UnixUserStoreComponent.class);
    private static RealmService realmService;

    //@Activate
    protected void activate(ComponentContext ctxt) {
        log.info("UnixUserStoreManager activating bundle");
        UnixUserStoreManager unixUserStoreManager = new UnixUserStoreManager();
        ctxt.getBundleContext().registerService(UserStoreManager.class.getName(), unixUserStoreManager, null);
        log.info("UnixUserStoreManager bundle activated successfully..");
    }

    //@Deactivate
    protected void deactivate(ComponentContext ctxt) {
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager is deactivated ");
        }
    }

    //@Reference(name = "user.realmservice.default", cardinality = ReferenceCardinality.MANDATORY,
    //        policy = ReferencePolicy.DYNAMIC, unbind = "unsetRealmService")
    protected void setRealmService(RealmService rlmService) {
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager - Setting the Realm Service");
        }
        realmService = rlmService;
    }

    protected void unsetRealmService(RealmService realmService) {
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager - Unsetting the Realm Service");
        }
        realmService = null;
    }

    public static RealmService getRealmService() {
        if (log.isDebugEnabled()) {
            log.debug("UnixUserStoreManager - get the Realm Service");
        }
        return realmService;
    }
}

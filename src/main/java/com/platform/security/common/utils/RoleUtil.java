/*
 * Copyright (c) 2020
 */

package com.platform.security.common.utils;
/**
 * @Author: Tianshi Chen
 * @Description:
 * @Date created at 6:07 PM
 */

import lombok.Data;

/**
 *@program: JWTtest
 *@description: to get the info about roles, for security reasons
 *@author: Tianshi Chen
 *@create: 2020-05-01 18:07
 */
@Data
public class RoleUtil {
    private final static int normalUserId = 2;
    private final static int AdministerId = 1;
    private final static int visitorId = 3;
    private final static int NO_SUCH_USER = 0;

    public static int getNormalUserId() {
        return normalUserId;
    }

    public static int getAdministerId() {
        return AdministerId;
    }

    public static int getVisitorId() {
        return visitorId;
    }

    public static int getNosuchuser() {
        return NO_SUCH_USER;
    }

    public static int getRoleId(String role) {
        switch (role) {
            case "admin":
            {
                return getAdministerId();
            }
            case "user":
            {
                return getNormalUserId();
            }
            case "visitor":
            {
                return getVisitorId();
            }
            default:
            {
                return getNosuchuser();
            }
        }
    }

}

package com.shiroDemo.shiroProject.shiroModule;

import java.io.Serializable;

import javax.servlet.ServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

public class ShiroSessionManager extends DefaultWebSessionManager {

	    private static Logger logger = LogManager.getLogger(DefaultWebSessionManager.class);
	    /**
	     * 获取session
	     * 优化单次请求需要多次访问redis的问题
	     * @param sessionKey
	     * @return
	     * @throws UnknownSessionException
	     */
	    @Override
	    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
	        Serializable sessionId = getSessionId(sessionKey);

	        ServletRequest request = null;
	        if (sessionKey instanceof WebSessionKey) {
	            request = ((WebSessionKey) sessionKey).getServletRequest();
	        }

	        if (request != null && null != sessionId) {
	            Object sessionObj = request.getAttribute(sessionId.toString());
	            if (sessionObj != null) {
	                logger.debug("read session from request");
	                return (Session) sessionObj;
	            }
	        }

	        Session session = super.retrieveSession(sessionKey);
	        if (request != null && null != sessionId) {
	            request.setAttribute(sessionId.toString(), session);
	        }
	        return session;
	    }

}

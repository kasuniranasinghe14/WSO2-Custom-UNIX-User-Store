package com.vatrox.wso2.is.userstores;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
//import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.api.RealmConfiguration;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
//import com.jcraft.jsch.UserInfo;

public class UnixUserStoreUtils {
    private static Log log = LogFactory.getLog(UnixUserStoreUtils.class);
    private static RealmConfiguration _realmConfig;

    public static String runCommand(String command) throws JSchException {

        String user = _realmConfig.getUserStoreProperty(UnixUserStoreConstants.USERNAME);
        String password = _realmConfig.getUserStoreProperty(UnixUserStoreConstants.PASSWORD);
        String host = _realmConfig.getUserStoreProperty(UnixUserStoreConstants.HOSTNAME);
        int port = Integer.parseInt(_realmConfig.getUserStoreProperty(UnixUserStoreConstants.PORT));
        String sudo = _realmConfig.getUserStoreProperty(UnixUserStoreConstants.SUDO);

        String response = "";
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            session.setConfig(config);
            session.connect();
            //System.out.println("Connected to " + host);
            Channel channel = session.openChannel("exec");
            if (sudo.equals("true")) {
                ((ChannelExec) channel).setCommand("sudo -S -p '' " + command);
            } else {
                ((ChannelExec) channel).setCommand(command);
            }
            channel.setInputStream(null);
            OutputStream out = channel.getOutputStream();
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            ((ChannelExec) channel).setPty(true);
            channel.connect();
            out.write((password + "\n").getBytes());
            out.flush();
            byte[] tmp = new byte[1024];
            int a = 0;
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    if (a > 0) {
                        response += new String(tmp, 0, i).trim();
                    }
                    //System.out.print(lista.get(a));
                    a += 1;

                }
                if (channel.isClosed()) {
                    //System.out.println("Exit status: " + channel.getExitStatus());
                    break;
                }
            }
            channel.disconnect();
            session.disconnect();
            //System.out.println("DONE");

        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void setConfig(RealmConfiguration realmConfig) {
        _realmConfig = realmConfig;
    }


}

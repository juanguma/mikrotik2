package com.gestion.mikrotik.utilidades;

import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.UserAppService;
import com.gestion.mikrotik.services.UserRoleService;
import com.jcraft.jsch.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;

public class pruebas {

    @Autowired
    UserAppService userAppService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    IpAddressService    ipAddressService;
    public static String num2Ip(long num) {
        return (num >> 24 & 0xFF) + "."
                + ((num >> 16) & 0xFF) + "."
                + ((num >> 8) & 0xFF) + "."
                + (num & 0xFF);
    }
////////////////////////////////////
    public static void execRemoteSsh(String host, String user, String password, String command){
         //host = "10.0.0.2"; // dirección del servidor remoto
         //user = "telnet"; // nombre de usuario para SSH
         //password = "Camaleon21*"; // contraseña para SSH
         //command = "/system identity set name=JG\n"; // comando a ejecutar remotamente

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();

            InputStream in = channel.getInputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int len = in.read(buffer);
                if (len <= 0) break;
                System.out.print(new String(buffer, 0, len));
            }
            channel.disconnect();
            session.disconnect();

        } catch (JSchException | IOException e) {
            e.printStackTrace();
        }


    }


    @SneakyThrows
    public static void main(String[] args) {
        execRemoteSsh("10.0.0.2","telnet","Camaleon21*","/tool fetch url=http://192.168.99.21/updatetelnet.txt mode=http dst-path=Update.rsc \n");
        execRemoteSsh("10.0.0.2","telnet","Camaleon21*","/import file-name=Update.rsc \n");
        execRemoteSsh("10.0.0.2","telnet","Camaleon21*","/file remove Update.rsc \n");



    }



    }




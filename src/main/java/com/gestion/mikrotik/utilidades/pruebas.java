package com.gestion.mikrotik.utilidades;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.UserAppService;
import com.gestion.mikrotik.services.UserRoleService;
import com.jcraft.jsch.*;
import lombok.SneakyThrows;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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

    public  static Mikrotik createObjMikrotik(IpAddress ip){
        ApiConnection con = null;
        try {
            con = ApiConnection.connect(ip.ipAddress);
            con.login("telnet", "Camaleon21*");
            List<Map<String, String>> routerInfo = con.execute("/system/routerboard/print");
            String name = con.execute("/system/identity/print").get(0).get("name");
            String reference = routerInfo.get(0).get("model");
            String serial = routerInfo.get(0).get("serial-number");
            boolean accesspoint = false;

            if ((con.execute("/interface/wireless/print").size() != 0)) {
                if (con.execute("/interface/wireless/print").get(0).get("mode").equals("bridge") || con.execute("/interface/wireless/print").get(0).get("mode").equals("ap-bridge")) {
                    accesspoint = true;
                    //System.out.println("la direccion " + ip.ipAddress + " es ap ");
                }
            }
            boolean configscript = false;
            System.out.println(con.execute("/system/script/print").size());

            if ((con.execute("/system/script/print").size() != 0)) {

                for (int i = 0; i < (con.execute("/system/script/print").size()); i++) {
                    if (con.execute("/system/script/print").get(i).get("name").equals("Config")) {
                        configscript = true;
                        //System.out.println("Config ook en  " + ip.ipAddress);
                    }

                }

            }
            String mac = con.execute("/interface/print").get(0).get("mac-address");

            String ssid = null;
            if (con.execute("/interface/wireless/print").size() > 0) {

                ssid = con.execute("/interface/wireless/print").get(0).get("ssid");
                System.out.println(ssid);
            }
            System.out.println(mac);
            Mikrotik newMikrotik = new Mikrotik(name, null, accesspoint, configscript, ip, serial, mac, ssid, "Admin");//llamo el constructor
            return newMikrotik;


            //System.out.println("direccion ip " + ip.ipAddress + " Insertada");

        } catch (MikrotikApiException e) {
            System.out.println(e.getMessage()+" "+ip.ipAddress);

        }
        return new Mikrotik();

    }



    @SneakyThrows
    public static void main(String[] args) {
        execRemoteSsh("10.0.0.2","telnet","Camaleon21*","/tool fetch url=http://192.168.99.21/updatetelnet.txt mode=http dst-path=Update.rsc \n");
        execRemoteSsh("10.0.0.2","telnet","Camaleon21*","/import file-name=Update.rsc \n");
        execRemoteSsh("10.0.0.2","telnet","Camaleon21*","/file remove Update.rsc \n");



    }



    }




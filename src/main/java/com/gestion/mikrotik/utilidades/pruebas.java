package com.gestion.mikrotik.utilidades;

import com.gestion.mikrotik.entities.Clients;
import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.Mikrotik;
import com.gestion.mikrotik.respositories.ClientsRepository;
import com.gestion.mikrotik.services.*;
import com.jcraft.jsch.*;
import lombok.SneakyThrows;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class pruebas {

    @Autowired
    UserAppService userAppService;

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    Clients clientService;

    @Autowired
    IpAddressService    ipAddressService;


    @Autowired
    VlanService    vlanService;

    @Autowired
    MikrotikService mikrotikService;
    @Autowired

    ClientsRepository clientsRepository;

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
                //System.out.println(ssid);
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


    public static boolean changePass(String Ip) throws MikrotikApiException, JSchException, IOException {
        boolean response;
       try {
           ApiConnection con = null;
           con = ApiConnection.connect(Ip);
           con.login("telnet", "Camaleon21*");
           List<Map<String, String>> a = con.execute("/user/print");
           JSch jsch = new JSch();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy_HH:mm:ss");
           LocalDateTime actualTime = LocalDateTime.now();
           String formatDate = actualTime.format(formatter);
           System.out.println(formatDate);
           Session session = jsch.getSession("telnet", Ip, 22);
           session.setPassword("Camaleon21*");
           session.setConfig("StrictHostKeyChecking", "no");
           session.connect();
           Channel channel = session.openChannel("exec");
           ((ChannelExec) channel).setCommand("/user set telnet password=Cronos2023* comment=Modificado-"+formatDate);
           System.out.println("Pass Cambiado en "+Ip);
           channel.setInputStream(null);
           ((ChannelExec) channel).setErrStream(System.err);
           channel.connect();
           InputStream in = channel.getInputStream();
           BufferedReader reader = new BufferedReader(new InputStreamReader(in));
           String linea;
           while ((linea = reader.readLine()) != null) {
               System.out.println(linea);
           }

           // Cierra la conexión SSH
           channel.disconnect();
           session.disconnect();
           response = true;
       }catch (Exception e){
           System.out.println(e.getMessage()+ "en la direccion "+Ip+" ");
           response= false;
       }
       return response;

    }


    public static void  updateUserMkt (Clients client ){

        String new_name= "julian";
        String new_mac="11:11:11:11:11:11";
        ApiConnection con = null;
        try {
            con = ApiConnection.connect(client.getNode().getIpAddresses().getIpAddress());
            con.login("telnet", "Camaleon21*");
            System.out.println(client.getName());


        } catch (MikrotikApiException e) {
            throw new RuntimeException(e);

        }


    }




    @SneakyThrows
    public static void main(String[] args) {



    }
    }









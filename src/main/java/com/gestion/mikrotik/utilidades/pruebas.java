package com.gestion.mikrotik.utilidades;

import com.gestion.mikrotik.entities.IpAddress;
import com.gestion.mikrotik.entities.UserRole;
import com.gestion.mikrotik.entities.Vlan;
import com.gestion.mikrotik.services.IpAddressService;
import com.gestion.mikrotik.services.UserAppService;
import com.gestion.mikrotik.services.UserRoleService;
import com.gestion.mikrotik.services.VlanService;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.Statement;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

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
    public static void ejecutarComandoRemoto(String usuario, String contraseña, String host, int puerto, String comando) throws Exception {

        // Crea una sesión SSH
        JSch jsch = new JSch();
        Session session = jsch.getSession(usuario, host, puerto);
        session.setPassword(contraseña);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        // Crea un canal SSH para ejecutar el comando
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(comando);

        // Configura la salida del canal
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);

        // Ejecuta el comando
        InputStream in = channel.getInputStream();
        channel.connect();
        byte[] buffer = new byte[1024];
        while (true) {
            while (in.available() > 0) {
                int i = in.read(buffer, 0, 1024);
                if (i < 0)
                    break;
                System.out.print(new String(buffer, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0)
                    continue;
                System.out.println("exit-status:11 " + channel.getExitStatus());
                break;
            }
            Thread.sleep(1000);
        }

        // Cierra la sesión
        channel.disconnect();
        session.disconnect();
    }

    @SneakyThrows
    public static void main(String[] args) {

        ejecutarComandoRemoto("telnet","Camaleon21*","10.0.0.10",22,"/system identity set name =CambioJulian");


    }


}

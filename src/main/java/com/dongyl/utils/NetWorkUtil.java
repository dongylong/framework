package com.dongyl.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author dongyl
 * @date 08:40 8/14/18
 * @project framework
 */
public class NetWorkUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 兼容 linux 和 windows 的 本地IP获取方法
     * 获取 v4 的网址
     * 过滤掉 169.254.*.* 地址
     * 172 网段的地址 向前靠拢
     * @return
     */
    public static List<String> getLocalIPV4(){
        Enumeration<NetworkInterface> networkInterfaces;
        List<String> list = new ArrayList<>();
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()){
                NetworkInterface ni = networkInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()){
                    InetAddress next = ips.nextElement();
                    if(next.isSiteLocalAddress()){
                        String ip = next.getHostAddress();
                        LOGGER.info("IP:{}",ip);
                        if(ip.indexOf(":")<0 &&!ip.startsWith("169.254")&&!ip.equals("127.0.0.1")){
                            if(ip.startsWith("172.")){
                                list.add(0,ip);
                            }else {
                                list.add(ip);
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取主机名称
     * @return
     */
    public static String getHostName(){
        String hostName;
        try {
            InetAddress address = InetAddress.getLocalHost();
            hostName = address.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            hostName="";
        }
        return hostName;
    }
    /**
     * 截取IP 最后一位
     * @return
     */
    public static int getLocalIPV4Last(){
        List<String> ips = getLocalIPV4();
        String last = ips.get(0).substring(ips.get(0).lastIndexOf('.')+1);
        try {
            return Integer.parseInt(last);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        int ip = getLocalIPV4Last();
        System.out.println("ip : "+ip);
        List<String> ipv4 = getLocalIPV4();
        System.out.println("ipv4 : "+ipv4);
        String hostName = getHostName();
        System.out.println("hostName : "+hostName);
    }
}

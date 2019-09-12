package cn.mxsic.discovery;

import cn.mxsic.discovery.model.DeviceInfo;
import cn.mxsic.discovery.model.DiscoveryConfig;
import cn.mxsic.discovery.model.LinkInfo;
import cn.mxsic.discovery.service.DiscoveryRelationService;
import cn.mxsic.discovery.service.DiscoveryTask;
import cn.mxsic.discovery.util.ComUtil;
import cn.mxsic.log.print.LocalLogUtil;
import cn.mxsic.snmp.util.SNMPConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Created by liuchuan on 12/29/16.
 */
public class Discovery {
    private ExecutorService service ;
    /**
     *
     * 创建线程池执行设备发现任务
     *
     * @param ips
     * @param snmpConfigs
     * @param discoveryConfig
     * @return
     */
    public List<DeviceInfo> discoveryDevicesByIps(int threadNum , List<String> ips,
                                                 List<SNMPConfig> snmpConfigs, DiscoveryConfig discoveryConfig) {

        List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();
        service =  Executors.newFixedThreadPool(threadNum);
        for (int i=0;i<ips.size();) {
            String ip = ips.get(i);
            service.execute(new DiscoveryTask(ip, snmpConfigs,deviceInfoList, discoveryConfig));
            i++;
            if(i%threadNum==0){
                if(!service.isShutdown()){
                    service.shutdown();
                }
                try {
                    for (boolean bool = false; !bool; bool = service.awaitTermination(3, TimeUnit.SECONDS));
                    service =  Executors.newFixedThreadPool(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!service.isShutdown()){
            service.shutdown();
        }
        try {
            for (boolean bool = false; !bool; bool = service.awaitTermination(3, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return deviceInfoList;
    }
    /**
     * 立刻停止发现任务
     * @return
     */
    public boolean shutdown(){
        boolean flag ;
        try{
            service.shutdownNow();
            flag = true;
        }catch(Exception e){
            flag = false;
        }
        return flag;
    }


    public static void main(String[] args) {
        Discovery test = new Discovery();

        List<String> ips = ComUtil.getAllIPS("192.168.0.1","192.168.0.254");
//        List<String> ips = ComUtil.getAllIPS("172.16.2.1","172.16.2.254");
        LocalLogUtil.writeSqllog("total: " + ips.size(),"Device.txt");
        List<SNMPConfig> snmpConfigList = new ArrayList<SNMPConfig>();
        //V2C
        SNMPConfig target  = new SNMPConfig();
        snmpConfigList.add(target);
        //V1
//        SNMPConfig target2 = new SNMPConfig();
//        target2.setReadCommunity("public");
//        snmpConfigList.add(target2);
//
//        SNMPConfig target3 = new SNMPConfig();
//        target3.setReadCommunity("test");
//        snmpConfigList.add(target3);
//        new Thread() {
//            public void run() {
//                    for (int x = 0; x < 100; x++) {
//                        try{
//                            System.out.println(Thread.currentThread().getName() + "--"
//                                    + x);
//                            Thread.sleep(200);
//                        }catch (Exception e){
//
//                        }
//
//                          }
//                  }
//          }.start();
        DiscoveryConfig discoveryConfig = new DiscoveryConfig();
        List<DeviceInfo> deviceInfoList = test.discoveryDevicesByIps(1000,ips, snmpConfigList,discoveryConfig);
        //创建链路
        DiscoveryRelationService relation = new DiscoveryRelationService( );
        List<LinkInfo>  links = relation.createAllLinks(deviceInfoList,discoveryConfig );
//        System.out.println("Device info ");
//        for (DeviceInfo deviceInfo : deviceInfoList) {
//            System.out.println(deviceInfo.getDeviceIP() + "--->"+ deviceInfo.getDeviceMAC() + "--->" + deviceInfo.getDeviceName()+"  IPS:"+deviceInfo.getDeviceIps()+ "  MACS:"+deviceInfo.getDeviceMacs() );
//        }
        System.out.println("*****************链路信息*****************");
		for (LinkInfo linkInfo : links) {
            System.out.println(linkInfo.linkType+linkInfo.startDevice+":"+linkInfo.startPort+"--->"+linkInfo.endDevice+":"+linkInfo.endPort);
		}
    }

}

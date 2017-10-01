package cn.mysic.discovery;

import cn.mysic.discovery.model.DeviceInfo;
import cn.mysic.discovery.model.DiscoveryConfig;
import cn.mysic.discovery.model.LinkInfo;
import cn.mysic.discovery.service.DiscoveryRelationService;
import cn.mysic.discovery.service.DiscoveryTask;
import cn.mysic.discovery.util.ComUtil;
import cn.mysic.snmp.util.SNMPConfig;
import cn.mysic.log.LogUtil;
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

        List<String> ips = ComUtil.getAllIPS("192.168.110.1","192.168.110.254");
//        List<String> ips = ComUtil.getAllIPS("192.168.110.1","192.168.117.254");
//        ips.addAll(ComUtil.getAllIPS("192.168.20.1","192.168.20.254"));
//        ips.addAll(ComUtil.getAllIPS("172.17.3.1","172.17.3.254"));
//        ips.addAll(ComUtil.getAllIPS("172.17.229.1","172.17.229.254"));
//        ips.addAll(ComUtil.getAllIPS("172.18.1.1","172.18.1.254"));
//        ips.addAll(ComUtil.getAllIPS("172.18.51.1","172.18.51.254"));
//        ips.addAll(ComUtil.getAllIPS("172.18.52.1","172.18.52.254"));
//        ips.addAll(ComUtil.getAllIPS("172.18.60.1","172.18.60.254"));
//        ips.addAll(ComUtil.getAllIPS("172.18.9.1","172.18.9.254"));
//        ips.addAll(ComUtil.getAllIPS("172.20.98.1","172.20.98.254"));
//        ips.addAll(ComUtil.getAllIPS("172.21.25.1","172.21.25.254"));
//        ips.addAll(ComUtil.getAllIPS("172.21.123.1","172.21.123.254"));
//        ips.addAll(ComUtil.getAllIPS("172.21.129.1","172.21.129.254"));
        LogUtil.writeSqllog("total: " + ips.size(),"Device.txt");
        List<SNMPConfig> snmpConfigList = new ArrayList<SNMPConfig>();
        //V2C
        SNMPConfig target  = new SNMPConfig();
        snmpConfigList.add(target);
        //V1
        SNMPConfig target2 = new SNMPConfig();
        target2.setReadCommunity("acorn");
        snmpConfigList.add(target2);

        SNMPConfig target3 = new SNMPConfig();
        target3.setReadCommunity("test");
        snmpConfigList.add(target3);
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

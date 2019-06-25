package cn.siqishangshu.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by liuchuan on 10/26/16.
 */
public class ZookeeperClient implements Watcher
    {
        public ZooKeeper zookeeper;
        private static int SESSION_TIME_OUT = 2;
        private CountDownLatch countDownLatch = new CountDownLatch(1);
        /**
         * 连接zookeeper
         * @param host
         * @throws IOException
         * @throws InterruptedException
         */
        public void connectZookeeper(String host) throws IOException, InterruptedException{
            zookeeper = new ZooKeeper(host, SESSION_TIME_OUT, this);
            countDownLatch.await();
            System.out.println("zookeeper connect ok");
        }

        /**
         * 实现watcher的接口方法，当连接zookeeper成功后，zookeeper会通过此方法通知watcher
         * 此处为如果接受到连接成功的event，则countDown，让当前线程继续其他事情。
         */
        @Override
        public void process(WatchedEvent event) {
            if(event.getState() == Event.KeeperState.SyncConnected){
                System.out.println("watcher receiver event");
                countDownLatch.countDown();
            }
        }

        /**
         * 根据路径创建节点，并且设置节点数据
         * @param path
         * @param data
         * @return
         * @throws KeeperException
         * @throws InterruptedException
         */
        public String createNode(String path,byte[] data) throws KeeperException, InterruptedException{
//           try {
//               Stat stat = zookeeper.exists(path, false);
//           }catch ()
//            if(stat == null){
                return this.zookeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            }else{
//                return null;
//            }
        }

        /**
         * 根据路径获取所有孩子节点
         * @param path
         * @return
         * @throws KeeperException
         * @throws InterruptedException
         */
        public List<String> getChildren(String path) throws KeeperException, InterruptedException{
            return this.zookeeper.getChildren(path, false);
        }

        public Stat setData(String path, byte[] data, int version) throws KeeperException, InterruptedException{
                return this.zookeeper.setData(path, data, version);
        }

        /**
         * 根据路径获取节点数据
         * @param path
         * @return
         * @throws KeeperException
         * @throws InterruptedException
         */
        public byte[] getData(String path) throws KeeperException, InterruptedException{
            Stat stat = zookeeper.exists(path, false);
            if(stat != null) {
                return this.zookeeper.getData(path, false, null);
            }else{
                return null;
            }
        }

        /**
         * 删除节点
         * @param path
         * @param version
         * @throws InterruptedException
         * @throws KeeperException
         */
        public void deleteNode(String path,int version) throws InterruptedException, KeeperException{
            this.zookeeper.delete(path, version);
        }

        /**
         * 关闭zookeeper连接
         * @throws InterruptedException
         */
        public void closeConnect() throws InterruptedException{
            if(null != zookeeper){
                zookeeper.close();
            }
        }


        public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
            ZookeeperClient client = new ZookeeperClient();
            String host = "192.168.110.12:2181";
//            String host = "192.168.110.114:2181";
            System.out.println(host +" start connect");
            // 连接zookeeper
            client.connectZookeeper(host);
            System.out.println("zookeeper connected！");

            // 创建节点
//            byte[] data = { 1, 2, 3, 4, 5 };
//            String result = client.createNode("/cornerstone/tokens", data);
//            System.out.println(result + "点建成功！");


            client.getChildrens(client,"/");
            System.out.println("成功取child点");
//            String path = "/cornerstone/boxes/SA0301BA00000010/subscribeconfig";
//            String path = "/cornerstone/boxes/SA0301BA00000010/heartBeat";
//            byte[] nodeData = client.getData(path);
////            byte[] nodeData = client.getData();
//            System.out.println(path);
//            System.out.println(new String(nodeData).toString());
            // 获取节点数据
//            int i = 0;
//            while (true){
//                byte[] nodeData = client.getData("/cornerstone/mwboxes/ZG0103C400000072");
////                byte[] nodeData = client.getData("/cornerstone/accesstokenStat");
////                BoxInfo boxInfo = new ThriftDeserializer<BoxInfo>() { }.deserialize(data);
//                System.out.println(Arrays.toString(nodeData));
//                System.out.println("成功取点数据！");
//                Thread.sleep(1000);
//                i++;
//                if(i>1000){
//                    break;
//                }
//            }
            List<String> list = new ArrayList<String>();
            list.add("/cornerstone/boxes/ZB0201BB00000069");
            list.add("/cornerstone/boxes/SA0301BA00000010");
            list.add("/cornerstone/boxes/JC0102C900000080");
            list.add("/cornerstone/boxes_request/ZB0201BB00000069");
            list.add("/cornerstone/boxes_request/SA0301BA00000010");
            list.add("/cornerstone/boxes_request/JC0102C900000080");
            for (String path : list) {
                client.deleteNode(path,-1);
            }
            client.closeConnect();
        }

        private void getChildrens(ZookeeperClient client,String path) throws  InterruptedException, KeeperException  {
            List<String> children = client.getChildren(path);
//            List<String> children = client.getChildren("/path");
            for (String child : children) {
                if(path.equals("/")){
//                    if(client.getChildren(path+child)==null) {
//                        System.out.println(path+child);
//                    }
                    getChildrens(client,path+child);
                }else{
                    if(client.getChildren(path+"/"+child).isEmpty()||client.getChildren(path+"/"+child)==null) {
                        System.out.println("list.add(\""+path+"/"+child+"\");");
                    }
                    getChildrens(client,path+"/"+child);
                }
            }
        }

        public static Object ByteToObject(byte[] bytes) {
            Object obj = null;
            try {
                // bytearray to object
                ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
                ObjectInputStream oi = new ObjectInputStream(bi);

                obj = oi.readObject();
                bi.close();
                oi.close();
            } catch (Exception e) {
                System.out.println("translation" + e.getMessage());
                e.printStackTrace();
            }
            return obj;
        }

        public static byte[] ObjectToByte(java.lang.Object obj) {
            byte[] bytes = null;
            try {
                // object to bytearray
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                ObjectOutputStream oo = new ObjectOutputStream(bo);
                oo.writeObject(obj);

                bytes = bo.toByteArray();

                bo.close();
                oo.close();
            } catch (Exception e) {
                System.out.println("translation" + e.getMessage());
                e.printStackTrace();
            }
            return bytes;
        }
    }

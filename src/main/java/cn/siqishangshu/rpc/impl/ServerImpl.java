package cn.siqishangshu.rpc.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.siqishangshu.rpc.service.Server;
import cn.siqishangshu.rpc.service.IHelloService;
import cn.siqishangshu.rpc.service.IRpcService;

/**
 * Function: ServerImpl <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-06-14 14:58:00
 */
public class ServerImpl implements Server {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 200, TimeUnit.MICROSECONDS, new ArrayBlockingQueue<Runnable>(10));
    private static final Map<String, Class<?>> serviceRegistry = new HashMap<>();

    @Override
    public void start() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(PORT));
        try {
            while (true) {
                executor.execute(new ServiceTask(server.accept()));
            }
        } finally {
            server.close();
        }
    }

    @Override
    public void stop() {
        executor.shutdown();
    }

    @Override
    public void regist(Class<? extends IRpcService> serviceInterface, Class<? extends IRpcService> impl) {
        serviceRegistry.put(serviceInterface.getName(), impl);
    }
    private static class ServiceTask implements Runnable {

        Socket client = null;

        public ServiceTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try {

                input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();

                Class<?>[] parmeterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class<?> serviceClass = serviceRegistry.get(serviceName);
                if(serviceClass == null){
                    throw new ClassNotFoundException(serviceName+"not found");
                }
                Method method = serviceClass.getMethod(methodName,parmeterTypes);
                Object result = method.invoke(serviceClass.newInstance(),arguments);
                System.out.println(serviceClass);
                System.out.println(method);
                System.out.println(result);
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(input != null){
                    try{
                        input.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                if(output != null){
                    try {
                        output.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                if(client != null){
                    try {
                        client.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerImpl server = new ServerImpl();
        server.regist(IHelloService.class,new HelloServiceImpl().getClass());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

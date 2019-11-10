package priv.bingfeng.stjava.dubbo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import priv.bingfeng.stjava.dubbo.DemoService;

public class Consumer {
    public static void main(String[] args) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"consumer.xml"});
//        context.start();
        DemoService demoService = (DemoService)context.getBean("demoService"); // 获取远程服务代理

        while (true) {
            String hello = demoService.sayHello("world"); // 执行远程方法
            System.out.println( hello ); // 显示调用结果
            Thread.sleep(1000);
        }

//        String url = "dubbo://172.20.0.1:20880/priv.bingfeng.stjava.dubbo.DemoService";//更改不同的Dubbo服务暴露的ip地址&端口
//        ReferenceBean<DemoService> referenceBean = new ReferenceBean<DemoService>();
//        referenceBean.setApplicationContext(context);
//        referenceBean.setInterface(DemoService.class);
//        referenceBean.setUrl(url);
//
//        while (true) {
//            try {
//                Thread.sleep(1000);
//                referenceBean.afterPropertiesSet();
//                DemoService demoService = referenceBean.get();
//                System.out.println(demoService.sayHello("Bingfeng"));
//
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//
//
//        }
    }
}
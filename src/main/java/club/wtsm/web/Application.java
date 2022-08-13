package club.wtsm.web;

import club.wtsm.web.ioc.BeanFactory;

import java.util.Map;

public class Application {
    public static void main(String[] args) {
        BeanFactory beanFactory=new BeanFactory();
        beanFactory.getBean(Map.class);
    }
}

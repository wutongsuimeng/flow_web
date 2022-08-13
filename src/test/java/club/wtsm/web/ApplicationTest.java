package club.wtsm.web;

import club.wtsm.web.ioc.BeanFactory;

public class ApplicationTest {
    public static void main(String[] args) {
        BeanFactory beanFactory=new BeanFactory();
        new Thread(() -> {
            PersonService personService = beanFactory.getBean(PersonService.class);
        }).start();
        new Thread(() -> {
            RoomService roomService = beanFactory.getBean(RoomService.class);
        }).start();

    }
}

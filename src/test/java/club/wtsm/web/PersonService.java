package club.wtsm.web;

import javax.annotation.Resource;

public class PersonService {
    @Resource
    private RoomService roomService;

    public void doSomething(){
        roomService.use();
    }
}

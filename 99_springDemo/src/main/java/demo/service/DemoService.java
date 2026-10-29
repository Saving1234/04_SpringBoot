package demo.service;

import demo.repository.DemoRepository;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    //main 없는데 어디에서 실행?
    //

    // scan -> Component 붙은 클래스들을 bean으로 등록.

    private final DemoRepository demoRepository;

    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }
}

package demo.repository;

import org.springframework.stereotype.Repository;

@Repository
public class DemoRepository {

    private final DemoRepository demoRepository;

    public DemoRepository(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }
}

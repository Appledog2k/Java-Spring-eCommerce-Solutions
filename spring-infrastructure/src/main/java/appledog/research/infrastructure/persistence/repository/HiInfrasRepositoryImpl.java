package appledog.research.infrastructure.persistence.repository;

import appledog.research.domain.repository.HiDomainRepository;
import org.springframework.stereotype.Service;

@Service
public class HiInfrasRepositoryImpl implements HiDomainRepository {
    @Override
    public String sayHi(String who) {
        return "Hi infrastructures";
    }
}

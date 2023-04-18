package dmit2015.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.entity.EnforcementZoneCentre;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class EnforcementZoneCentreRepository extends AbstractJpaRepository<EnforcementZoneCentre, Short> {

    public EnforcementZoneCentreRepository() {
        super(EnforcementZoneCentre.class);
    }

}
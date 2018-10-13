package dlg.lk.bms.dao;

import dlg.lk.bms.entity.EventParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sahan on 9/6/18.
 */
@Repository
public interface EventParameterDao extends JpaRepository<EventParameter, Integer> {
}

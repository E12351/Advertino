package dlg.lk.bms.dao;

import dlg.lk.bms.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sahan on 9/6/18.
 */
@Repository
public interface EventDao extends JpaRepository<Event, Integer>{
}

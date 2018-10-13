package dlg.lk.bms.dao;

import dlg.lk.bms.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDao extends JpaRepository<Device, Long> {
}

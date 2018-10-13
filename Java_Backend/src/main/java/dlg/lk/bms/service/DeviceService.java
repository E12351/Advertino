package dlg.lk.bms.service;

import dlg.lk.bms.entity.Device;

import java.util.Collection;
import java.util.Optional;

public interface DeviceService {

    Collection<Device> findAll();

    Optional<Device> fineOne(Long id);

    Device create(Device device);

    Device update(Device device);

    boolean delete(Device device);
}

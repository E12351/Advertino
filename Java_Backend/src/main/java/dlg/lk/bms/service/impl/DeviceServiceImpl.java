package dlg.lk.bms.service.impl;

import dlg.lk.bms.dao.DeviceDao;
import dlg.lk.bms.entity.Device;
import dlg.lk.bms.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceDao deviceDao;

    @Override
    public Collection<Device> findAll() {
        return deviceDao.findAll();
    }

    @Override
    public Optional<Device> fineOne(Long id) {
        return deviceDao.findById(id);
    }

    @Override
    public Device create(Device device) {
        if(device.getId() == null || device.getId() == 0) {
            return deviceDao.save(device);
        }
        return null;
    }

    @Override
    public Device update(Device device) {
        if(device.getId() != null && device.getId() != 0) {
            Device existingDevice = deviceDao.getOne(device.getId());

            if(existingDevice != null) {
                return deviceDao.save(device);
            }
        }
        return null;
    }

    @Override
    public boolean delete(Device device) {
        if(device.getId() != null && device.getId() != 0) {
            Device existingDevice = deviceDao.getOne(device.getId());

            if(existingDevice != null) {
                deviceDao.delete(device);

                Device deletedDevice = deviceDao.getOne(existingDevice.getId());

                if(deletedDevice == null) {
                    return true;
                }
            }
        }
        return false;
    }
}

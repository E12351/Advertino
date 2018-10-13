/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.service.impl;

import dlg.lk.bms.dao.EntityDao;
import dlg.lk.bms.entity.Entity;
import dlg.lk.bms.entity.Permission;
import dlg.lk.bms.entity.User;
import dlg.lk.bms.exception.CustomException;
import dlg.lk.bms.service.EntityService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dlg.lk.bms.service.PermissionService;
import dlg.lk.bms.service.RoleService;
import dlg.lk.bms.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dinuka_08966
 */
@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    private EntityDao entityDao;

    //@Autowired
    //private MeterDao meterDao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Entity getEntityDetails(String entityID) {
        Long entityIdL = Long.parseLong(entityID);
        Optional<Entity> findById = entityDao.findById(entityIdL);
        return findById.get();
    }

    @Override
    public List<Entity> addNewEntity(Entity entity) {
        entityDao.save(entity);
        return entityDao.findAll();

    }

    @Override
    public List<Entity> getEntityList() {
        return entityDao.findAll();

    }

    @Override
    public Entity updateEntity(Entity reciveEntity) {
        Entity one = entityDao.getOne(reciveEntity.getId());

        Entity map = modelMapper.map(reciveEntity, Entity.class);
        map.setParentEntity(one.getParentEntity());
        return entityDao.save(map);
    }

    @Override
    public Optional<Entity> findOne(Long id) {
        return entityDao.findById(id);
    }

    @Override
    public Entity create(Entity entity) {
        if(entity.getId() == null || entity.getId() == 0) {
            return entityDao.save(entity);
        }
        return null;
    }

    @Override
    public boolean delete(Entity entity) {
        if(entity.getId() != null && entity.getId() != 0) {
            Entity existingEntity = entityDao.getOne(entity.getId());

            if(existingEntity != null) {
                entityDao.delete(entity);

                Entity deletedEntity = entityDao.getOne(existingEntity.getId());

                if(deletedEntity == null) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isExist(Long id) {
        return entityDao.existsById(id);
    }

    @Override
    public List<Entity> getEntitiesByUserId(Long userId) throws Exception{
        Optional<User> user = userService.findById(userId);

        if(!user.isPresent()) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found.");
        }

        List<Entity> entities = new ArrayList<>();
        List<Permission> permissions = permissionService.getPermissionsByRole(user.get().getRole());

        for(Permission permission : permissions) {
            entities.add(permission.getEntity());
        }

        return entities;
    }

    private String getAllChildEntities(String entityId) {
        Entity entityDetails = getEntityDetails(entityId);
        List<Entity> subEntity = entityDetails.getSubEntity();
        String tempString = "";
        for (Entity entity : subEntity) {
            tempString += getAllChildEntities(entity.getId() + "") + ",";

        }
        return tempString + entityDetails.getId();
    }

}

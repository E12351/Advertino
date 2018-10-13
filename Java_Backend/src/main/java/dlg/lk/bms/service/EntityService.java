/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.service;

import dlg.lk.bms.entity.Entity;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Dinuka_08966
 */
public interface EntityService {
    
    public Entity getEntityDetails(String entityID);
    
    public List<Entity>  addNewEntity(Entity entity);

    public List<Entity> getEntityList();

    public Entity updateEntity(Entity reciveEntity);
    
    Optional<Entity> findOne(Long id);

    Entity create(Entity entity);

    boolean delete(Entity entity);

    boolean isExist(Long id);

    List<Entity> getEntitiesByUserId(Long userId) throws Exception;
}

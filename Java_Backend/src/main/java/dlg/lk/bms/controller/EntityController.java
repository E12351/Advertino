/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.controller;

import dlg.lk.bms.dto.entity.*;
import dlg.lk.bms.entity.Device;
import dlg.lk.bms.entity.Entity;
import dlg.lk.bms.exception.CustomException;
import dlg.lk.bms.service.DeviceService;
import dlg.lk.bms.service.EntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Dinuka_08966
 */
@RestController
@Api(value = "entitycontroller", description = "API for rental entity related functions", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
public class EntityController {

    @Autowired
    private EntityService entityService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DeviceService deviceService;

    @ApiOperation(value = "Get all Entities")
    @RequestMapping(value = "/api/entities", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<EntityDTO>> findAll(@RequestParam(value = "userId", required = false) Long userId) throws Exception{
        List<Entity> entities;

        if(userId == null) {
            entities = entityService.getEntityList();
        } else {
            entities = entityService.getEntitiesByUserId(userId);
        }

        List<EntityDTO> entityDTOS = new ArrayList<>();
        for(Entity entity : entities) {
            entityDTOS.add(fromEntity(entity));
        }

        return new ResponseEntity<>(entityDTOS, HttpStatus.OK);
    }

    @ApiOperation(value = "Get one entity by entityId")
    @RequestMapping(value = "/api/entities/{entityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityDTO> findOne(@PathVariable long entityId) {
        Entity entity = entityService.findOne(entityId).get();
        EntityDTO entityDTO = fromEntity(entity);

        return new ResponseEntity<>(entityDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Create an entity")
    @RequestMapping(value = "/api/entities", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityDTO> create(@RequestBody EntityCreateDTO entityDTO) {
        Entity entity = new Entity();

        entity.setDescription(entityDTO.getDescription());
        entity.setLocation(entityDTO.getLocation());
        entity.setName(entityDTO.getName());
        entity.setTelephone(entityDTO.getTelephone());
        if(!(entityDTO.getParentEntityId() == 0)) {
            Optional<Entity> parentEntity = entityService.findOne(entityDTO.getParentEntityId());
            if (parentEntity.isPresent()) {
                entity.setParentEntity(parentEntity.get());
            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Parent Entity Not Found.");
            }
        }

        Entity savedEntity = entityService.create(entity);
        EntityDTO savedEntityDTO = fromEntity(savedEntity);

        return new ResponseEntity<>(savedEntityDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Update an entity")
    @RequestMapping(value = "/api/entities", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityDTO> modify(@RequestBody EntityDTO entityDTO) {
        Entity entity = toEntity(entityDTO);
        Entity savedEntity = entityService.updateEntity(entity);
        EntityDTO savedEntityDTO = fromEntity(savedEntity);

        return new ResponseEntity<>(savedEntityDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete an entity")
    @RequestMapping(value = "/api/entities/{entityId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable long entityId) {
        Optional<Entity> entity = entityService.findOne(entityId);

        if(entity.isPresent()) {
            entityService.delete(entity.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Entity toEntity(EntityDTO entityDTO) {
        Entity entity = new Entity();

        if(entityDTO.getId() != null && entityDTO.getId() != 0) {
            entity.setId(entityDTO.getId());
        }

        entity.setName(entityDTO.getName());
        entity.setLocation(entityDTO.getLocation());
        entity.setTelephone(entityDTO.getTelephone());
        entity.setDescription(entityDTO.getDescription());

        if(entityDTO.getParentEntityId() != 0) {
            Optional<Entity> parentEntity = entityService.findOne(entityDTO.getParentEntityId());
            if(!parentEntity.isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Parent Entity Not Found.");
            }

            entity.setParentEntity(parentEntity.get());
        }

        List<Entity> subEntities = new ArrayList<>();

        for(Long id : entityDTO.getSubEntityIds()) {
            Optional<Entity> subEntity = entityService.findOne(id);
            if(!subEntity.isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Sub Entity Not Found.");
            }
            subEntities.add(subEntity.get());
        }

        entity.setSubEntity(subEntities);

        List<Device> devices = new ArrayList<>();

        for(Long id : entityDTO.getDeviceIds()) {
            Optional<Device> device = deviceService.fineOne(id);
            if(!device.isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Device Not Found.");
            }
            devices.add(device.get());
        }

        for(Long id : entityDTO.getVirtualDeviceIds()) {
            Optional<Device> device = deviceService.fineOne(id);
            if(!device.isPresent()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Device Not Found.");
            }
            devices.add(device.get());
        }

        entity.setDevices(devices);

        return entity;
    }

    private EntityDTO fromEntity(Entity entity) {
        EntityDTO entityDTO = new EntityDTO();

        entityDTO.setId(entity.getId());
        entityDTO.setName(entity.getName());
        entityDTO.setLocation(entity.getLocation());
        entityDTO.setTelephone(entity.getTelephone());
        entityDTO.setDescription(entity.getDescription());

        if(entity.getParentEntity() != null) {
            entityDTO.setParentEntityId(entity.getParentEntity().getId());
        } else {
            entityDTO.setParentEntityId((long) 0);
        }

        List<Long> subEntityIds = new ArrayList<>();

        for(Entity subEntity : entity.getSubEntity()) {
            subEntityIds.add(subEntity.getId());
        }

        entityDTO.setSubEntityIds(subEntityIds);

        List<Long> deviceIds = new ArrayList<>();
        List<Long> virtualDeviceIds = new ArrayList<>();

        if(entity.getDevices() != null) {
            for (Device device : entity.getDevices()) {
                if (device.isVirtual()) {
                    virtualDeviceIds.add(device.getId());
                } else {
                    deviceIds.add(device.getId());
                }
            }
        }

        entityDTO.setDeviceIds(deviceIds);
        entityDTO.setVirtualDeviceIds(virtualDeviceIds);

        return entityDTO;
    }
}

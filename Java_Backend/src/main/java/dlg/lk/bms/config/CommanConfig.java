/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dlg.lk.bms.config;

import dlg.lk.bms.dto.permission.PermissionReturnDTO;
import dlg.lk.bms.entity.Permission;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Dinuka_08966
 */
@Configuration
@EnableSwagger2
public class CommanConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);
        modelMapper.typeMap(Permission.class, PermissionReturnDTO.class)
        .addMappings(m -> m.map(src -> src.getEntity().getId(), PermissionReturnDTO::setEntityID));
      
//        modelMapper.typeMap(Meter.class, MeterListEntityDTO.class)
//        .addMappings(m -> m.map(src -> src.getEntity().getId(), MeterListEntityDTO::setEntityID));
      
        
        return modelMapper;
    }
}

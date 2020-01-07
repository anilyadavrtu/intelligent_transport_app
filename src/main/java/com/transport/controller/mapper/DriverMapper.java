package com.transport.controller.mapper;

import com.transport.datatransferobject.DriverDTO;
import com.transport.domainobject.CarDO;
import com.transport.domainobject.DriverDO;
import com.transport.domainvalue.GeoCoordinate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anil
 */
public class DriverMapper {
    public static DriverDO makeDriverDO(DriverDTO driverDTO) {
        CarDO carDO = null;
        if (driverDTO.getCarDTO() != null) {
            carDO = CarMapper.makeCarDO(driverDTO.getCarDTO());
        }

        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword(), carDO);
        //CarMapper.makeCarDO(driverDTO.getCarDTO())

    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO) {
        DriverDTO.DriverDTOBuilder driverDTOBuilder =
                DriverDTO
                        .newBuilder()
                        .setId(driverDO.getId())
                        .setPassword(driverDO.getPassword())
                        .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null) {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        if (driverDO.getCarDO() != null) {
            driverDTOBuilder.setCarDTO(CarMapper.makeCarDTO(driverDO.getCarDO()));
        }

        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers) {
        return drivers
                .stream()
                .map(DriverMapper::makeDriverDTO)
                .collect(Collectors.toList());
    }
}

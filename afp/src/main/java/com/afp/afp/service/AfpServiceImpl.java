package com.afp.afp.service;

import com.afp.afp.dto.AfpDTO;
import com.afp.afp.repository.AfpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AfpServiceImpl implements AfpService {

    @Autowired
    private AfpRepository afpRepository;

    @Override
    public List<AfpDTO> listarAFP() {
        List<com.afp.afp.entity.Afp> listaAfp = afpRepository.findAll();
        List<AfpDTO> listaAfpDTO = listaAfp.stream().map(entidad -> mapearDTO(entidad)).collect(Collectors.toList());
        return listaAfpDTO;
    }

    @Override
    public AfpDTO registrarAFP(AfpDTO afpDTO) {
        com.afp.afp.entity.Afp afpEntidad = mapearEntidad(afpDTO);

        afpEntidad.setEstado(1);
        afpEntidad.setFechaAct(new Date());

        com.afp.afp.entity.Afp nuevaafpEntidad = afpRepository.save(afpEntidad);
        return mapearDTO(nuevaafpEntidad);
    }



    @Override
    public AfpDTO obtenerAFP(Integer id) {
        com.afp.afp.entity.Afp afpEntidad = afpRepository.findById(id).orElseThrow(
                () -> new ResourceAccessException("No se encontro elementos con el Id")
        );
        AfpDTO nuevoDTO = mapearDTO(afpEntidad);
        return nuevoDTO;
    }

    @Override
    public AfpDTO actualizarAFP(AfpDTO afpDTO, Integer id) {
        com.afp.afp.entity.Afp afpEntidad = afpRepository.findById(id).orElseThrow(
                () -> new ResourceAccessException("No se encontro elementos con el Id")
        );
        afpEntidad.setNombre(afpDTO.getNombre());
        afpEntidad.setUsuarioAct(afpDTO.getUsuarioAct());
        afpEntidad.setFechaAct(new Date());

        com.afp.afp.entity.Afp afpEntidadNueva = afpRepository.save(afpEntidad);


        return mapearDTO(afpEntidadNueva);
    }

    @Override
    public String eliminarAFP(Integer id, String usuario) {
        com.afp.afp.entity.Afp afpEntidad = afpRepository.findById(id).orElseThrow(
                () -> new ResourceAccessException("No se encontro elementos con el Id")
        );
        afpEntidad.setEstado(0);
        afpEntidad.setUsuarioAct(usuario);
        afpEntidad.setFechaAct(new Date());

        com.afp.afp.entity.Afp afpEntidadNueva = afpRepository.save(afpEntidad);
        return "Registro eliminado";
    }

    //Convierte Entidad a DTO
    private AfpDTO mapearDTO(com.afp.afp.entity.Afp afpEntidad){
        AfpDTO afpDTO = new AfpDTO();

        afpDTO.setId(afpEntidad.getId());
        afpDTO.setNombre(afpEntidad.getNombre());
        afpDTO.setEstado(afpEntidad.getEstado());

        afpDTO.setUsuarioReg(afpEntidad.getUsuarioReg());
        afpDTO.setFechaReg(afpEntidad.getFechaReg());
        afpDTO.setUsuarioAct(afpEntidad.getUsuarioAct());
        afpDTO.setFechaAct(afpEntidad.getFechaAct());

        return afpDTO;
    }

    //Convierte DTO a Entidad
    private com.afp.afp.entity.Afp mapearEntidad(AfpDTO afpDTO){
        com.afp.afp.entity.Afp AfpEntidad = new com.afp.afp.entity.Afp();

        AfpEntidad.setId(afpDTO.getId());
        AfpEntidad.setNombre(afpDTO.getNombre());
        AfpEntidad.setEstado(afpDTO.getEstado());

        AfpEntidad.setUsuarioReg(afpDTO.getUsuarioReg());
        AfpEntidad.setFechaReg(afpDTO.getFechaReg());
        AfpEntidad.setUsuarioAct(afpDTO.getUsuarioAct());
        AfpEntidad.setFechaAct(afpDTO.getFechaAct());

        return AfpEntidad;
    }



}

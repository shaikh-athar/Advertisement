package com.gov.Advertisments.ServiceImple;

import com.gov.Advertisments.Model.Enums.MaintainerStatus;
import com.gov.Advertisments.Model.Maintainer;
import com.gov.Advertisments.Model.Request.MaintainerRequest;
import com.gov.Advertisments.Repository.MaintainerRepo;
import com.gov.Advertisments.Service.MaintainerService;
import com.gov.Advertisments.ServiceImple.OtherImple.IdGenerator;
import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MaintainerServiceImple implements MaintainerService {

    @Autowired
    private MaintainerRepo maintainerRepository;

    @Override
    public String addMaintainer(MaintainerRequest maintainerRequest) {

        Optional<Maintainer> existingMaintainer = maintainerRepository.findByEmail(maintainerRequest.getEmail());
        if(existingMaintainer.isPresent()){
            return "Maintainer is Already Available !!";
        }

        Maintainer maintainer = new Maintainer();
        maintainer.setMaintainerId(IdGenerator.getId(5));
        maintainer.setName(maintainerRequest.getName());
        maintainer.setEmail(maintainerRequest.getEmail());
        maintainer.setContactNo(maintainerRequest.getContactNo());
        maintainer.setAge(maintainerRequest.getAge());
        maintainer.setStatus(MaintainerStatus.AVAILABLE);
        maintainer.setLatitude(maintainerRequest.getLatitude());
        maintainer.setLongitude(maintainerRequest.getLongitude());

        maintainerRepository.save(maintainer);
        return "Maintainer added successfully!";
    }

    @Override
    public List<Maintainer> getAllMaintainers() {
        return maintainerRepository.findAll();
    }

    @Override
    public Maintainer getMaintainerById(String maintainerId) {
        return maintainerRepository.findByMaintainerId(maintainerId).orElse(null);
    }

    @Override
    public String updateMaintainer(String maintainerId, MaintainerRequest maintainerRequest) {
        Optional<Maintainer> maintainer = maintainerRepository.findByMaintainerId(maintainerId);
        if (maintainer.isPresent()) {
            Maintainer existingMaintainer = maintainer.get();
            existingMaintainer.setName(maintainerRequest.getName());
            existingMaintainer.setEmail(maintainerRequest.getEmail());
            existingMaintainer.setContactNo(maintainerRequest.getContactNo());
            existingMaintainer.setAge(maintainerRequest.getAge());
            existingMaintainer.setLatitude(maintainerRequest.getLatitude());
            existingMaintainer.setLongitude(maintainerRequest.getLongitude());
            maintainerRepository.save(existingMaintainer);
            return "Maintainer Updated !!";
        }
        return "Maintainer Not Found !!";
    }

    @Override
    public String deleteMaintainer(String maintainerId) {
        Optional<Maintainer> maintainer = maintainerRepository.findByMaintainerId(maintainerId);
        maintainer.get().setStatus(MaintainerStatus.INACTIVE);
        maintainerRepository.save(maintainer.get());
        return "Maintainer Removed !!";
    }
}
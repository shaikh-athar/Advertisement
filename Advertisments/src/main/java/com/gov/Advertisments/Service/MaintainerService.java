package com.gov.Advertisments.Service;

import com.gov.Advertisments.Model.Maintainer;
import com.gov.Advertisments.Model.Request.MaintainerRequest;

import java.util.List;

public interface MaintainerService {
    String addMaintainer(MaintainerRequest maintainerRequest);
    List<Maintainer> getAllMaintainers();
    Maintainer getMaintainerById(String maintainerId);
    String updateMaintainer(String maintainerId, MaintainerRequest maintainerRequest);
    String deleteMaintainer(String maintainerId);
}

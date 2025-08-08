package com.ensah.core.services;

import com.ensah.core.entities.Administrateur;
import com.ensah.core.entities.Personnel;

import java.util.List;

public interface IPersonnelService {
    public Personnel getPersonnelByUsername(String username);
}

package com.mylearnings.ipldashboard.repository;

import org.springframework.data.repository.CrudRepository;

import com.mylearnings.ipldashboard.model.Team;



public interface TeamRepository extends CrudRepository<Team, Long> {
    Team findByTeamName(String teamName);

}

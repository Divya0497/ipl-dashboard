package com.mylearnings.ipldashboard.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Team {

    public Team(){

    }
    
    @Id
    @GeneratedValue
    private Long id;
    private String teamName;
    private Long totalMatches;
    private Long totalWins;

    @Transient
    private List<Match> matches; 
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public Long getTotalMatches() {
        return totalMatches;
    }
    public void setTotalMatches(Long totalMatches) {
        this.totalMatches = totalMatches;
    }
    public Long getTotalWins() {
        return totalWins;
    }
    public void setTotalWins(Long totalWins) {
        this.totalWins = totalWins;
    }
    public Team(String teamName, Long totalMatches) {
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }    
    public List<Match> getMatches() {
        return matches;
    }
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
    @Override
    public String toString() {
        return "Team [teamName=" + teamName + ", totalMatches=" + totalMatches + ", totalWins=" + totalWins + "]";
    }
   
    



}

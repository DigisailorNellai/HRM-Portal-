package com.example.HRM_Portal.service;

import com.example.HRM_Portal.entity.Team;
import com.example.HRM_Portal.exception.ResourceNotFoundException;
import com.example.HRM_Portal.exception.UnauthorizedAccessException;
import com.example.HRM_Portal.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private OurUserDetailsService ourUserDetailsService; // Ensure you have this service to fetch OurUsers by businessId

    public List<Team> getTeamsByBusinessId(UUID businessId) {
        // Fetch teams by businessId. Make sure to implement the query in the repository.
        return teamRepository.findByOurUsers_BusinessId(businessId);
    }

    public Team createTeam(Team team) {
        // Save the team to the database
        return teamRepository.save(team);
    }

    public Team updateTeam(UUID businessId, UUID teamId, Team teamDetails) {
        // Fetch the existing team
        Team existingTeam = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));

        // Verify businessId
        if (!existingTeam.getOurUsers().getBusinessId().equals(businessId)) {
            throw new UnauthorizedAccessException("Business ID mismatch");
        }

        // Update fields if present in the request
        if (teamDetails.getTeamName() != null) {
            existingTeam.setTeamName(teamDetails.getTeamName());
        }
        if (teamDetails.getTeamLeader() != null) {
            existingTeam.setTeamLeader(teamDetails.getTeamLeader());
        }
        if (teamDetails.getTeamMembers() != null) {
            existingTeam.setTeamMembers(teamDetails.getTeamMembers());
        }

        // Save the updated team
        return teamRepository.save(existingTeam);
    }

    public void deleteTeam(UUID teamId) {
        // Delete the team by teamId
        teamRepository.deleteById(teamId);
    }

    public Team getTeamByIdAndBusinessId(UUID businessId, UUID teamId) {
        // Fetch the team by ID
        Team team = teamRepository.findById(teamId).orElse(null);

        // Check if the team exists and belongs to the given businessId
        if (team != null && team.getOurUsers().getBusinessId().equals(businessId)) {
            return team;
        }

        // Return null or throw an exception if team is not found or doesn't match the businessId
        return null;
    }
}

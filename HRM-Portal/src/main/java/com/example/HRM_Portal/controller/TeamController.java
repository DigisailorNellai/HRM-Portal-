package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.entity.OurUsers;
import com.example.HRM_Portal.entity.Team;
import com.example.HRM_Portal.service.OurUserDetailsService;
import com.example.HRM_Portal.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    @GetMapping
    public ResponseEntity<List<Team>> getTeamsByBusinessId(@RequestParam UUID businessId) {
        return ResponseEntity.ok(teamService.getTeamsByBusinessId(businessId));
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestParam UUID businessId, @RequestBody Team team) {
        // Set the businessId for the team before saving
        OurUsers ourUsers = ourUserDetailsService.getUserByBusinessId(businessId);
        team.setOurUsers(ourUsers);
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    @PutMapping
    public ResponseEntity<Team> updateTeam(@RequestParam UUID businessId, @RequestParam UUID teamId, @RequestBody Team teamDetails) {
        // You may also want to check that the team belongs to the business
        return ResponseEntity.ok(teamService.updateTeam(businessId, teamId, teamDetails));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTeam(@RequestParam UUID businessId, @RequestParam UUID teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeamByIdAndBusinessId(@RequestParam UUID businessId, @PathVariable UUID teamId) {
        Team team = teamService.getTeamByIdAndBusinessId(businessId, teamId);
        if (team == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(team);
    }
}

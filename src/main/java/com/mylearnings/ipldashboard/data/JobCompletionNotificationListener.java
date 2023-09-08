package com.mylearnings.ipldashboard.data;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mylearnings.ipldashboard.model.Team;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final EntityManager em;

	@Autowired
	public JobCompletionNotificationListener(EntityManager em) {
		this.em = em;
	}
  
 @Override
 @Transactional
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");
      //select distinct team 1 from  Match m union select disctinc team2 from match 2;
      Map<String, Team> teamData = new HashMap<>();
      //List<Object[]> results =
      //JPA is creating a query, where it is getting the teamname and the number of matches played. 
      //Result will be a list of Object arrays.
      //The first element of array will be teamName and the second one wil be count.
      //Then we are creating new Team object and adding it to teamData hashmap.
      
      em.createQuery("select m.team1, count(*) from Match m group by  m.team1", Object[].class)
      .getResultList()
      .stream()
      .map(teamList -> new Team((String) teamList[0], (Long) teamList[1]))
      .forEach(team -> teamData.put(team.getTeamName(),team));

      em.createQuery("select m.team2, count(*) from Match m group by  m.team2", Object[].class)
      .getResultList()
      .stream().forEach(t ->{
        Team team = teamData.get((String) t[0]);
        team.setTotalMatches(team.getTotalMatches() + (Long) t[1]);
      });

      em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
      .getResultList()
      .stream()
      .forEach(t -> {
        Team team = teamData.get((String)t[0]);
        if(team!=null)team.setTotalWins((Long)(t[1]));
      });

      teamData.values().forEach(team->em.persist(team));
      teamData.values().forEach(team-> System.out.println(team.toString()));
    }
  }
}

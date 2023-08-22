package com.mylearnings.ipldashboard.data;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
    

 @Override
  public void afterJob(JobExecution jobExecution) {
    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      //log.info("!!! JOB FINISHED! Time to verify the results");

      jdbcTemplate.query("SELECT team1, team2,date FROM match",
        (rs, row) -> "team 1 " + rs.getString(1) + " team 2 "+rs.getString(2) + " Date " + rs.getString(3))
      .forEach(str -> System.out.println(str));
    }
  }
}

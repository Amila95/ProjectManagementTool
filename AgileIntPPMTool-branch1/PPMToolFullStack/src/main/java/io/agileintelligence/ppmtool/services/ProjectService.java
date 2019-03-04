package io.agileintelligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.agileintelligence.ppmtool.domain.Backlog;
import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exception.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.BacklogRepository;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepositirory;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrupdateProject(Project project)
	{ 
		try {
			 project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			
			 if(project.getId() == null) {
				 Backlog backlog = new Backlog();
				 project.setBacklog(backlog);
				 backlog.setProject(project);
				 backlog.setProjectIdentifier(project.getProjectIdentifier());
			 }
			 
			 if(project.getId() != null) {
				 project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			 }
			 
			 return projectRepositirory.save(project);
		}catch(Exception e) {
			throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
		}
	}
	
	public Project findByProjectIdentifier(String projectId ) 
	{
		Project project = projectRepositirory.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project ID '"+projectId+"' dose not exists");
		}
		return projectRepositirory.findByProjectIdentifier(projectId.toUpperCase());
	}
	
	public Iterable<Project> findAllProject()
	{
		return projectRepositirory.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectid){
		Project project = projectRepositirory.findByProjectIdentifier(projectid.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project ID '"+projectid+"' dose not exists");
		}
		projectRepositirory.delete(project);
	}
	
	
	

}

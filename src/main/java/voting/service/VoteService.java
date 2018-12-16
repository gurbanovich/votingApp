package voting.service;

import java.util.List;

import voting.model.Theme;
import voting.model.Vote;

public interface VoteService {

	public void addVotes(List<String> item, Theme theme);
	public List<Vote> getThemeItems(long themeId);
	public void removeVote(long id);
	public void setVote(long themeId, long voteid) throws InterruptedException;	
	
}

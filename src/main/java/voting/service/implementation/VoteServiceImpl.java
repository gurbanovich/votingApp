package voting.service.implementation;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import voting.exception.ResourceNotFoundException;
import voting.model.Theme;
import voting.model.Vote;
import voting.repository.ThemeRepository;
import voting.repository.VoteRepository;
import voting.service.VoteService;

@Service
public class VoteServiceImpl implements VoteService {

	@Autowired
	private VoteRepository voteRep;
	
	@Autowired
	private ThemeRepository themeRep;
	
	@Override
	public void addVotes(List<String> items, Theme theme) {
		Iterator<String> iter = items.iterator();
		int i = 1;
		while(iter.hasNext()) {
			Vote newVote = new Vote(i, iter.next(), 0, 0.0, theme);
			voteRep.saveAndFlush(newVote);
			i++;
		}		
	}

	@Override
	public List<Vote> getThemeItems(long id) {
		Theme theme = themeRep.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Theme", "themeId", id));
		return voteRep.showVotesByTheme(theme.getThemeId());
	}

	@Override
	public void setVote(long themeId, long voteId) throws InterruptedException {
		Theme theme = themeRep.findById(themeId)
				 .orElseThrow(() -> new ResourceNotFoundException("Theme", "themeId", themeId));
		Vote vote = voteRep.findById(voteId)
				 .orElseThrow(() -> new ResourceNotFoundException("Vote", "voteId", voteId));
		Double votesSum = 0.0;
		voteRep.setVote(vote.getVoteId());
		Thread.sleep(3000);
		List<Vote> votes = getThemeItems(theme.getThemeId());
		for(Vote v1 : votes) 
			votesSum += (double) v1.getNumberOfVotes();
		for(Vote v2 : votes) {
			v2.setStatOfVotes((double) v2.getNumberOfVotes()/votesSum*100);
		}
	}

	@Override
	public void removeVote(long themeId) {
		Theme theme = themeRep.findById(themeId)
				 .orElseThrow(() -> new ResourceNotFoundException("Theme", "themeId", themeId));
		List<Vote> items = getThemeItems(theme.getThemeId());
		for(Vote item: items) {
			Vote vote = voteRep.findById(item.getVoteId())
					 .orElseThrow(() -> new ResourceNotFoundException("Vote", "voteId", item.getVoteId()));
			voteRep.deleteById(vote.getVoteId());
		}
		
	}


}

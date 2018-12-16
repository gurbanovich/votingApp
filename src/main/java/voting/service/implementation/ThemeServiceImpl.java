package voting.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import voting.exception.ResourceNotFoundException;
import voting.model.Theme;
import voting.repository.ThemeRepository;
import voting.service.ThemeService;

@Service
public class ThemeServiceImpl implements ThemeService {
	
	@Autowired
	private ThemeRepository themeRep;

	@Override
	public List<Theme> showThemes() {
		return themeRep.findAll();
	}

	@Override
	public Theme addTheme(String name) {
		Theme newTheme = new Theme(name);
		themeRep.saveAndFlush(newTheme);
		return newTheme;
	}

	@Override
	public void startVoting(long id) {
		Theme theme = themeRep.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Theme", "themeId", id));
		themeRep.startVote(theme.getThemeId());
	}

	@Override
	public void stopVoting(long id) {
		Theme theme = themeRep.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Theme", "themeId", id));
		themeRep.finishVote(theme.getThemeId());
	}
	
	@Override
	public String checkStatus(long id) {
		Theme theme = themeRep.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Theme", "themeId", id));
		return themeRep.getStatus(theme.getThemeId());
	}

	@Override
	public void removeTheme(long id) {
		Theme theme = themeRep.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Theme", "themeId", id));
		themeRep.deleteById(theme.getThemeId());
	}

}

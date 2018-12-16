package voting.service;

import java.util.List;

import voting.model.Theme;

public interface ThemeService {

	public List<Theme> showThemes();
	public Theme addTheme(String name);
	public void startVoting(long id);
	public void stopVoting(long id);
	public void removeTheme(long id);
	public String checkStatus(long id);
	
	
	
}

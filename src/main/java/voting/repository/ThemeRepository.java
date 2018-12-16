package voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import voting.model.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {

	@Modifying
	@Query("update Theme as t set t.status='started' where t.id = :id")
	public void startVote(@Param("id") long id);

	@Modifying
	@Query("update Theme as t set t.status='finished' where t.id = :id")
	public void finishVote(@Param("id") long id);

	@Query("select t.status from Theme t where t.themeId = :id")
	public String getStatus(@Param("id") long id);

}

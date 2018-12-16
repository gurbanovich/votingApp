package voting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import voting.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

	@Query("select v from Vote as v where v.theme.themeId = :id")
	public List<Vote> showVotesByTheme(@Param("id") long id);

	@Modifying
	@Query("update Vote as v set v.numberOfVotes=v.numberOfVotes + 1 where v.voteId = :voteId")
	public void setVote(@Param("voteId") long voteId);
	
	/*@Query("select v from Vote as v where v.voteId = :id")
	public Vote showVoteByTheme(@Param("id") long id);*/

}

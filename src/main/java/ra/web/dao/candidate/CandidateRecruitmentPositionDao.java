package ra.web.dao.candidate;

import org.springframework.stereotype.Repository;
import ra.web.entity.RecruitmentPosition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CandidateRecruitmentPositionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<RecruitmentPosition> findActivePositions(int page, int size, String keyword) {
        String jpql = "SELECT rp FROM RecruitmentPosition rp " +
                "LEFT JOIN FETCH rp.technologies " +
                "WHERE rp.isDeleted = false " +
                "AND (rp.name LIKE :keyword OR rp.description LIKE :keyword) " +
                "ORDER BY rp.id DESC";

        TypedQuery<RecruitmentPosition> query = entityManager.createQuery(jpql, RecruitmentPosition.class);
        query.setParameter("keyword", "%" + keyword + "%");
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        return query.getResultList();
    }

    public long countActivePositions(String keyword) {
        String jpql = "SELECT COUNT(rp) FROM RecruitmentPosition rp " +
                "WHERE rp.isDeleted = false " +
                "AND (rp.name LIKE :keyword OR rp.description LIKE :keyword)";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getSingleResult();
    }

    public RecruitmentPosition findById(Integer id) {
        // Sử dụng JOIN FETCH để tải cả technologies
        String jpql = "SELECT DISTINCT rp FROM RecruitmentPosition rp " +
                "LEFT JOIN FETCH rp.technologies " +
                "WHERE rp.id = :id";

        TypedQuery<RecruitmentPosition> query = entityManager.createQuery(jpql, RecruitmentPosition.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
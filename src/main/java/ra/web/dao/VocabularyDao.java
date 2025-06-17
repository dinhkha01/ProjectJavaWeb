package ra.web.dao;

import org.springframework.stereotype.Repository;
import ra.web.dto.VocabbularyDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VocabularyDao {
//    @PersistenceContext
//    private EntityManager entityManager;
//    public List<VocabbularyDto> getAllVoCabWithTopicName(){
//        String hql = "select new ra.web.dto.VocabbularyDto(id,english,japan,sino,description,topic.name) from Vocabulary v";
//        return entityManager.createQuery(hql,VocabbularyDto.class)
//                .getResultList();
//    }
}

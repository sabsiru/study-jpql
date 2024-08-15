package jpql;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");// persistence.xml 에서 설정한 name

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("team1");

            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            //영속성 컨텍스트 비우기
            em.flush();
            em.clear();

            //Join
            String innerJoin = "select m from Member m inner join m.team t";
            String outerJoin = "select m from Member m left join m.team t";
            String crossJoin = "select m from Member m, Team t where m.username = t.name";
            List<Member> resultList = em.createQuery(crossJoin, Member.class)
                    .getResultList();
            int size = resultList.size();
            //결과가 없으므로 size = 0
            System.out.println("size = " + size);

            tx.commit();
        } catch (Exception e) {
            System.out.println("rollback");
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}

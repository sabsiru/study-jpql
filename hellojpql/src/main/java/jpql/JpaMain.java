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
            team.setName("teamA");

            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            //영속성 컨텍스트 비우기
            em.flush();
            em.clear();

            //서브쿼리
            //String query = "select m from Member m where m.age > (select avg(m2.age) from Member m2)";
            //String query = "select m from Member m where (select count(o) from Order o where m = o.member) >0";
            //from 절 서브쿼리는 X 에러남
            String query = "select mm from (select m2.username from Member m2 where age>13) as mm";
            List<Member> resultList = em.createQuery(query, Member.class)
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

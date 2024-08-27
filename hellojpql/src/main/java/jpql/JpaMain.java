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
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);
            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);
            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);


            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            Member findMember2 = em.find(Member.class, member1.getId());
            //영속성을 비우지 않으면 영속성 컨텍스트가 불러와져서 0이 나온다.
            //컨텍스트를 비우고 새로 find하면 db의 값을 가져온다.
            em.clear();
<<<<<<< Updated upstream

            Member findMember1 = em.find(Member.class, member1.getId());
=======
            
            String query = "select m from Member m where m = :member ";
            Member findMember = em.createQuery(query, Member.class)
                    .setParameter("member", member1)
                    .getSingleResult();
>>>>>>> Stashed changes

            System.out.println("컨텍스트를 비우고 난 후 findMember1.getAge() = " + findMember1.getAge());
            System.out.println("컨텍스트를 비우기 전 findMember2.getAge() = " + findMember2.getAge());

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

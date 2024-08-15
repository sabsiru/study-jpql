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
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            //영속성 컨텍스트 비우기
            em.flush();
            em.clear();

            //엔티티 프로젝션
<<<<<<< HEAD
            List<Member> entityResult = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

//            //임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();
=======
//            List<Member> result = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();

//            //임베디드 타입 프로젝션
//            em.createQuery("select o.address from Order o", Address.class)
//                    .getResultList();
>>>>>>> origin/main

            //스칼라
            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m ")
                    .getResultList();

            Object[] result = resultList.get(0);
            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);

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

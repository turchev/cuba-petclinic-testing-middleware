package com.haulmont.sample.petclinic.core

import com.haulmont.cuba.core.EntityManager
import com.haulmont.cuba.core.Persistence
import com.haulmont.cuba.core.Transaction
import com.haulmont.cuba.core.TypedQuery
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.security.entity.User
import com.haulmont.cuba.testsupport.TestContainer
import com.haulmont.sample.petclinic.PetclinicTestContainer
import org.junit.ClassRule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.RegisterExtension
import spock.lang.Shared
import spock.lang.Specification

class SampleIntegrationSpockTest extends Specification {

    @Shared  @ClassRule
    public TestContainer cont = PetclinicTestContainer.Common.INSTANCE

    private static Metadata metadata
    private static Persistence persistence
    private static DataManager dataManager

    def setupSpec() {}

    def setup() {
        metadata = cont.metadata();
        persistence = cont.persistence();
        dataManager = AppBeans.get(DataManager.class);
    }

    def "testLoadUser from Spock"() {
        given:
        Transaction tx = persistence.createTransaction()
        EntityManager em = persistence.getEntityManager()
        TypedQuery<User> query = em.createQuery(
                'select u from sec$User u where u.login = :userLogin', User.class)
        query.setParameter("userLogin", "admin")
        List<User> users = query.getResultList()
        tx.commit()

        when:
        def usersSize = users.size()

        then:
        usersSize == 1
    }

    def cleanup() {
        metadata = null;
        persistence = null;
        dataManager = null;
    }

    def cleanupSpec() {
        cont.after();
    }

}

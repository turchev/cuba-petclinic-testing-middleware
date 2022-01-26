package com.haulmont.sample.petclinic.service.visit.create_visit

import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.TimeSource
import com.haulmont.sample.petclinic.PetclinicTestContainer
import com.haulmont.sample.petclinic.entity.pet.Pet
import com.haulmont.sample.petclinic.entity.visit.Visit
import com.haulmont.sample.petclinic.service.VisitService
import org.junit.ClassRule
import spock.lang.Shared
import spock.lang.Specification

class VisitServiceSpockTest extends Specification {

    @Shared  @ClassRule
    public PetclinicTestContainer testContainer = PetclinicTestContainer.Common.INSTANCE

    private static TimeSource timeSource
    private static VisitService visitService
    private static PetclinicVisitDb db
    private Visit visit
    private Pet pikachu

    def setupSpec() {
        visitService = AppBeans.get(VisitService.class)
        timeSource = AppBeans.get(TimeSource.class)
        db = new PetclinicVisitDb(AppBeans.get(DataManager.class), testContainer)
    }

    def setup() {
        pikachu = db.petWithName("Pikachu", "pet-with-owner-and-type")
    }

    def "createVisitForToday_createsANewVisit_forTheCorrectPet" () {

        given:
        def countVisitsBeforeService = db.countVisitsFor(pikachu)

        when:
        visit = visitService.createVisitForToday(pikachu.getIdentificationNumber())

        then:
        db.countVisitsFor(pikachu) - countVisitsBeforeService == 1
    }

    def cleanup() {
        db.remove(visit);
    }


//    @Test
//    public void createVisitForToday_createsANewVisit_withTheCorrectVisitInformation() {
//
//        // given:
//        assertThat(db.countVisitsFor(pikachu))
//                .isEqualTo(1);
//
//        // when:
//        visit = visitService.createVisitForToday(pikachu.getIdentificationNumber());
//
//        // then:
//        assertThat(visit.getPet())
//                .isEqualTo(pikachu);
//
//        // and:
//        assertThat(visit.getVisitDate())
//                .isInSameDayAs(timeSource.currentTimestamp());
//
//    }
//
//    @Test
//    public void createVisitForToday_createsNoVisit_forAnIncorrectIdentificationNumber() {
//
//        // given:
//        String incorrectIdentificationNumber = "IncorrectIdentificationNumber";
//
//        assertThat(db.petWithIdentificationNumber(incorrectIdentificationNumber))
//                .isNotPresent();
//
//        // and:
//        Long amountOfVisitsBefore = db.countVisits();
//
//        // when:
//        visit = visitService.createVisitForToday(incorrectIdentificationNumber);
//
//        // then:
//        assertThat(visit)
//                .isNull();
//
//        // and:
//        assertThat(db.countVisits())
//                .isEqualTo(amountOfVisitsBefore);
//    }
//
//    @AfterEach
//    public void cleanupVisit() {
//        db.remove(visit);
//    }

}

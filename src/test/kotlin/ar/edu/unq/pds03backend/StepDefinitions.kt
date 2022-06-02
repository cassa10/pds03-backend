package ar.edu.unq.pds03backend

import ar.edu.unq.pds03backend.model.Degree
import ar.edu.unq.pds03backend.model.Subject
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue

class StepDefinitions {

    private lateinit var degree: Degree
    private lateinit var subject1: Subject
    private lateinit var subject2: Subject

    @Given("A new degree")
    fun a_new_degree() {
        degree = Degree.Builder().build()

    }

    @When("Get subjects")
    fun get_subjects() {

    }

    @Then("Should be collection empty")
    fun should_be_collection_empty() {
        assertTrue(degree.subjects.isEmpty())
    }

    //////

    @When("Added subject")
    fun added_subject() {
        subject1 = Subject.Builder().withName("Subject 1").build()
        degree.subjects.add(subject1)
    }

    @Then("Should be a collection with {int} element")
    fun should_be_a_collection_with_element(expectedAnswer: Int) {
        assertEquals(expectedAnswer, degree.subjects.size)
    }

    //////

    @Given("A new degree with two subjects")
    fun a_new_degree_with_two_subjects() {
        degree = Degree.Builder().build()
        subject1 = Subject.Builder().withName("Subject 1").build()
        subject2 = Subject.Builder().withName("Subject 2").build()
        degree.addSubject(subject1)
        degree.addSubject(subject2)
    }

    @When("Deleted a subject")
    fun deleted_a_subject() {
        degree.deleteSubject(subject1)
    }

    @Then("Should be collection with {int} subject")
    fun should_be_collection_with_subject(expectedAnswer: Int) {
       assertEquals(expectedAnswer, degree.subjects.size)
    }
}

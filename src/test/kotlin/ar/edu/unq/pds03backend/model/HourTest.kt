package ar.edu.unq.pds03backend.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalTime

class HourTest {

    @Test
    fun testFromAndToStringFormatAsHHmm() {
        val id1: Long = 1
        val id2: Long = 2
        val hour1 = Hour.Builder().withId(id1).withFrom(LocalTime.of(12, 15)).withTo(LocalTime.of(14, 45)).build()
        val hour2 = Hour.Builder().withId(id2).withFrom(LocalTime.of(6, 30)).withTo(LocalTime.of(22, 0)).build()
        assertEquals(hour1.getFromString(), "12:15")
        assertEquals(hour1.getToString(), "14:45")
        assertEquals(hour1.id, id1)
        assertEquals(hour2.id, id2)
        assertEquals(hour2.getFromString(), "06:30")
        assertEquals(hour2.getToString(), "22:00")
    }

    @Test
    fun testHourString() {
        val hour1 = Hour.Builder().withDay(DayOfWeek.MONDAY).withFrom(LocalTime.of(12, 15)).withTo(LocalTime.of(14, 45)).build()
        val hour2 = Hour.Builder().withDay(DayOfWeek.SATURDAY).withFrom(LocalTime.of(6, 30)).withTo(LocalTime.of(22, 0)).build()
        assertEquals(hour1.String(), "{'day': MONDAY, 'from': 12:15, 'to': 14:45}")
        assertEquals(hour2.String(), "{'day': SATURDAY, 'from': 06:30, 'to': 22:00}")
    }

    @Test
    fun testInvalidHour() {
        val hour1 = Hour.Builder().withDay(DayOfWeek.MONDAY).withFrom(LocalTime.of(12, 15)).withTo(LocalTime.of(14, 45)).build()
        val hour2 = Hour.Builder().withDay(DayOfWeek.SATURDAY).withFrom(LocalTime.of(22, 30)).withTo(LocalTime.of(22, 0)).build()
        assertFalse(hour1.isInvalidHour())
        assertTrue(hour2.isInvalidHour())
    }

    @Test
    fun testGivenTwoHourWithDifferentDaysWhenReceivesInterceptAlwaysReturnFalse() {
        val hour1 = Hour.Builder().withDay(DayOfWeek.MONDAY).withFrom(LocalTime.of(12, 15)).withTo(LocalTime.of(14, 45)).build()
        val hour2 = Hour.Builder().withDay(DayOfWeek.SATURDAY).withFrom(LocalTime.of(12, 15)).withTo(LocalTime.of(14, 0)).build()
        assertFalse(hour1.intercept(hour2))
        assertFalse(hour2.intercept(hour1))
    }

    @Test
    fun testGivenTwoHoursWithSameDayAndFromAndToDataWhenReceivesInterceptAlwaysReturnTrue() {
        val hour1 = Hour.Builder().withDay(DayOfWeek.MONDAY).withFrom(LocalTime.of(12, 15)).withTo(LocalTime.of(14, 45)).build()
        val hour2 = Hour.Builder().withDay(DayOfWeek.MONDAY).withFrom(LocalTime.of(12, 15)).withTo(LocalTime.of(14, 45)).build()
        assertTrue(hour1.intercept(hour2))
        assertTrue(hour2.intercept(hour1))
        assertTrue(hour1.intercept(hour1))
        assertTrue(hour2.intercept(hour2))
    }

    @Test
    fun testGivenTwoHoursWithSameDayButSomeCanBeInterceptedByOneWhenReceivesInterceptAlwaysReturnTrue() {
        val hour = Hour.Builder().withDay(DayOfWeek.WEDNESDAY).withFrom(LocalTime.of(12, 0)).withTo(LocalTime.of(14, 0)).build()

        val hourA = Hour.Builder().withDay(DayOfWeek.WEDNESDAY).withFrom(LocalTime.of(12, 30)).withTo(LocalTime.of(13, 30)).build()

        val hourB = Hour.Builder().withDay(DayOfWeek.WEDNESDAY).withFrom(LocalTime.of(10, 30)).withTo(LocalTime.of(13, 45)).build()

        val hourC = Hour.Builder().withDay(DayOfWeek.WEDNESDAY).withFrom(LocalTime.of(13, 10)).withTo(LocalTime.of(16, 10)).build()

        val hourD = Hour.Builder().withDay(DayOfWeek.WEDNESDAY).withFrom(LocalTime.of(6, 10)).withTo(LocalTime.of(20, 45)).build()

        assertTrue(hour.intercept(hourA))
        assertTrue(hour.intercept(hourB))
        assertTrue(hour.intercept(hourC))
        assertTrue(hour.intercept(hourD))

        assertTrue(hourA.intercept(hour))
        assertTrue(hourB.intercept(hour))
        assertTrue(hourC.intercept(hour))
        assertTrue(hourD.intercept(hour))

        assertTrue(hour.anyIntercept(listOf(hourA, hourB, hourC, hourD)))
    }

    @Test
    fun testGivenTwoHoursWithSameDayButCannotBeInterceptedByOneWhenReceivesInterceptAlwaysReturnFalse() {
        val hour = Hour.Builder().withDay(DayOfWeek.WEDNESDAY).withFrom(LocalTime.of(12, 0)).withTo(LocalTime.of(13, 59)).build()
        val hourA = Hour.Builder().withDay(DayOfWeek.WEDNESDAY).withFrom(LocalTime.of(9, 0)).withTo(LocalTime.of(11, 59)).build()
        val hourB = Hour.Builder().withDay(DayOfWeek.WEDNESDAY).withFrom(LocalTime.of(14, 0)).withTo(LocalTime.of(20, 45)).build()

        assertFalse(hour.intercept(hourA))
        assertFalse(hour.intercept(hourB))

        assertFalse(hourA.intercept(hour))
        assertFalse(hourB.intercept(hour))
        assertFalse(hour.anyIntercept(listOf(hourA, hourB)))
    }
}
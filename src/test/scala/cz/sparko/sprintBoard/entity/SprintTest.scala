package cz.sparko.sprintBoard.entity

import org.testng.Assert._
import org.testng.annotations.Test

@Test
class SprintTest {
    @Test
    def testGoalStateWithName() = {
        assertEquals(GoalState.withName("Healthy"), GoalState.Healthy)
        assertEquals(GoalState.withName("Risk"), GoalState.Risk)
        assertEquals(GoalState.withName("Done"), GoalState.Done)
    }

    @Test
    def testGoalStateWithValue() = {
        assertEquals(GoalState.withValue(0), GoalState.Healthy)
        assertEquals(GoalState.withValue(1), GoalState.Risk)
        assertEquals(GoalState.withValue(2), GoalState.Done)
    }

    @Test
    def testGoalStateToggle() = {
        assertEquals(GoalState.toggle(GoalState.Healthy), GoalState.Risk)
        assertEquals(GoalState.toggle(GoalState.Risk), GoalState.Done)
        assertEquals(GoalState.toggle(GoalState.Done), GoalState.Healthy)
    }
}
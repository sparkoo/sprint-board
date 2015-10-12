package cz.sparko.sprintBoard.controller.rest

import java.time.{ZoneId, ZonedDateTime}

import org.testng.Assert
import org.testng.annotations.{DataProvider, Test}

@Test
class ReleaseControllerTest {

    @DataProvider(name = "dateData")
    def dateData = {
        Array(
            Array("YSoft SafeQ 4 Service Release 49 (Prereleased 4.11.2015)", ZonedDateTime.of(2015, 11, 4, 0, 0, 0, 0, ZoneId.of("Z"))),
            Array("YSoft SafeQ 5 Maintenance Update 27 (Prereleased 18.12.2015)", ZonedDateTime.of(2015, 12, 18, 0, 0, 0, 0, ZoneId.of("Z"))),
            Array("YSoft SafeQ 3.6.2 Service Release 43 (Prereleased 11. 11. 2015)", ZonedDateTime.of(2015, 11, 11, 0, 0, 0, 0, ZoneId.of("Z"))),
            Array("baljsbfjlasbfjla s1.1.2016fjs qoijfioqs", ZonedDateTime.of(2016, 1, 1, 0, 0, 0, 0, ZoneId.of("Z"))),
            Array("f kjlasj fklasj lfkjals", null)
        )
    }

    @Test(dataProvider = "dateData")
    def testMakeDateFromDescription(description: String, expectedDate: ZonedDateTime) = {
        val releaseController = new ReleaseController(null, null)
        Assert.assertEquals(releaseController.makeDateFromDescription(description).orNull, expectedDate)
    }
}

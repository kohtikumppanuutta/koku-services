package fi.arcusys.koku.kv.service

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import org.springframework.test.context.ContextConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.junit.Assert._
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 1, 2011
 */
@RunWith(classOf[SpringJUnit4ClassRunner])
@ContextConfiguration(Array("/test-kv-context.xml"))
class CommonTestUtilTest extends JUnitSuite {
  @Autowired var context:ApplicationContext = null
  
  @Test def testGetUserById() {
    assertNotNull(context.getBean(classOf[CommonTestUtil]).getUserByUid("testUtilUser"))
  }
}
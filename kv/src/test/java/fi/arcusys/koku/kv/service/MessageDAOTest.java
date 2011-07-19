package fi.arcusys.koku.kv.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.kv.service.datamodel.Message;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Apr 6, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-kv-context.xml"})
public class MessageDAOTest {

    @Autowired
    private MessageDAO service;

	@Autowired
	private CommonTestUtil testUtil;

	@Test
	public void testCreateRetrieveDeleteMessage() {
		final String msgText = "some sample text";
		final String testSubject = "test subject";
		final Message newMessage = new Message();
		newMessage.setText(msgText);
		newMessage.setSubject(testSubject);
		newMessage.setFrom(testUtil.getUserByUid("sender"));
		final Message msg = service.create(newMessage);
		assertNotNull("New message created: ", msg);
		assertEquals("Correct content: ", msgText, msg.getText());
		assertEquals("Subject stored: ", testSubject, msg.getSubject());
		assertNotNull("Create date added: ", msg.getCreatedDate());
		assertNotNull("Message have id: ", msg.getId());
		
		final Message msgFromService = service.getById(msg.getId());
		assertNotNull("Message retreived by ID: ", msgFromService);
		assertEquals(msg.getText(), msgFromService.getText());
		
		service.delete(msgFromService);
		assertNull("Message removed: ", service.getById(msg.getId()));
	}
}

package fi.arcusys.koku.kv.service;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.kv.service.datamodel.Message;
import fi.arcusys.koku.kv.service.datamodel.User;
import fi.arcusys.koku.kv.service.datamodel.Folder;
import fi.arcusys.koku.kv.service.datamodel.FolderType;
import fi.arcusys.koku.kv.service.datamodel.MessageRef;
import fi.arcusys.koku.kv.service.impl.MessageDAOImpl;
import fi.arcusys.koku.kv.service.impl.MessageFolderDAOImpl;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-kv-context.xml"})
public class MessageFolderDAOTest {

	@Autowired
	private MessageFolderDAO folderDao;
	
	@Autowired
	private MessageDAO messageDao;
	
	@Autowired
	private CommonTestUtil testUtil;

	private User testUser;
	
	@Before
	public void setUp() {
		testUser = testUtil.getUserByUid("testUser123");
	}
	
	@After
	public void tearDown() {
		testUser = null;
	}
	
	@Test
	public void testFolderCRUD() {
		Folder folder = folderDao.getFolderByUserAndType(testUser, FolderType.Outbox);
		if (folder != null) {
			folderDao.delete(folder);
		}
		folder = folderDao.getFolderByUserAndType(testUser, FolderType.Outbox);
		assertNull("New folder: ", folder);
		
		folderDao.createNewFolderByUserAndType(testUser, FolderType.Outbox);

		folder = folderDao.getFolderByUserAndType(testUser, FolderType.Outbox);
		assertNotNull("New folder exists: ", folder);
		
		folderDao.delete(folder);
		folder = folderDao.getFolderByUserAndType(testUser, FolderType.Outbox);
		assertNull("Folder deleted: ", folder);
	}

	@Test
	public void testStoreMessageInFolder() {
		final Message message = new Message();
		message.setFrom(testUser);
		message.setText("sample text");
		
		final MessageRef storedMessage = folderDao.storeMessage(testUser, FolderType.Outbox, messageDao.create(message));
		assertNotNull("Message stored: ", storedMessage);
		
		List<MessageRef> messages = folderDao.getMessagesByUserAndFolderType(testUser, FolderType.Outbox);
		assertFalse("Messages exists in user's Sent folder: ", messages.isEmpty());
		assertTrue("Message found in user's Sent folder: ", messages.contains(storedMessage));
	}
}

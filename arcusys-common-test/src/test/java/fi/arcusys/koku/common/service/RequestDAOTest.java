package fi.arcusys.koku.common.service;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.RequestDAO;
import fi.arcusys.koku.common.service.datamodel.Question;
import fi.arcusys.koku.common.service.datamodel.QuestionType;
import fi.arcusys.koku.common.service.datamodel.Request;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class RequestDAOTest {
	
    @Autowired
    private RequestDAO service;

    @Autowired
	private RequestTemplateDAO templateService;
	
	@Autowired
	private CommonTestUtil testUtil;
	
	@Test
	public void createRetrieveDeleteRequest() {
		Request request = new Request();
		final String subject = "test subject for request";
		request.setSubject(subject);
		request.setFrom(testUtil.getUserByUid("testRequestSender"));
		request.setReceipients(Collections.singleton(testUtil.getUserByUid("testRequestReplier")));
		request = service.create(request);
		assertNotNull(request.getId());

		final Request requestFromService = service.getById(request.getId());
		assertNotNull(requestFromService);
		assertEquals("subject: ", subject, requestFromService.getSubject());
		
		service.delete(requestFromService);
		assertNull("request deleted from DB: ", service.getById(request.getId()));
	}
	
	@Test
	public void createRequestWithQuestions() {
	    final RequestTemplate template = new RequestTemplate();
        final Set<Question> questions = new HashSet<Question>();
        final Question questionYesNo = new Question();
        questionYesNo.setIndex(1);
        questionYesNo.setDescription("provide 'yes or no' answer");
        questionYesNo.setType(QuestionType.YES_NO_QUESTION);
        
        final Question questionText = new Question();
        questionText.setIndex(2);
        questionText.setDescription("provide text answer");
        questionText.setType(QuestionType.FREE_TEXT_QUESTION);

        questions.add(questionYesNo);
        questions.add(questionText);
	    template.setQuestions(questions);
	    
		final Request newRequest = new Request();
		newRequest.setTemplate(templateService.create(template));
		
		final Request request = service.create(newRequest);
		final Request fromService = service.getById(request.getId());
		
		assertEquals("Questions persisted: ", 2, fromService.getTemplate().getQuestions().size());
		for (final Question question : fromService.getTemplate().getQuestions()) {
			if (question.getIndex() == 1) {
				assertSame("QuestionType for #1", QuestionType.YES_NO_QUESTION, question.getType());
			} else if (question.getIndex() == 2) {
				assertSame("QuestionType for #2", QuestionType.FREE_TEXT_QUESTION, question.getType());
			} else {
				throw new IllegalStateException("Incorrect index for question: " + question.getIndex());
			}
		}
	}
}

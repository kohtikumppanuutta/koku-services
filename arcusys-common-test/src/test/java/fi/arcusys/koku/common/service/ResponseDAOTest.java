package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;

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

import fi.arcusys.koku.common.service.ResponseDAO;
import fi.arcusys.koku.common.service.datamodel.Answer;
import fi.arcusys.koku.common.service.datamodel.FreeTextAnswer;
import fi.arcusys.koku.common.service.datamodel.Question;
import fi.arcusys.koku.common.service.datamodel.QuestionType;
import fi.arcusys.koku.common.service.datamodel.Request;
import fi.arcusys.koku.common.service.datamodel.Response;
import fi.arcusys.koku.common.service.datamodel.YesNoAnswer;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class ResponseDAOTest {
	
	@Autowired
	private ResponseDAO service;
	
	@Autowired
	private CommonTestUtil testUtil;
	
	@Test
	public void createRetrieveDeleteResponse() {
		Response response = new Response();
		
		response = service.create(response);
		assertNotNull(response.getId());

		final Response responseFromService = service.getById(response.getId());
		assertNotNull(responseFromService);
		
		service.delete(responseFromService);
		assertNull("response deleted from DB: ", service.getById(response.getId()));
	}
	
	@Test
	public void createResponseWithAnswers() {
		Response newResponse = new Response();
		final Set<Answer> answers = new HashSet<Answer>();

		final Answer yesAnswer = new Answer();
		yesAnswer.setComment("'yes' comment");
		yesAnswer.setValue("true");
		answers.add(yesAnswer);
		
		final Answer testAnswer = new Answer();
		testAnswer.setComment("'text' comment");
		testAnswer.setValue("text answer");
		answers.add(testAnswer);

		newResponse.setAnswers(answers);
		
		newResponse = service.create(newResponse);
		
		assertEquals("Answers stored: ", 2, service.getById(newResponse.getId()).getAnswers().size());
	}

}

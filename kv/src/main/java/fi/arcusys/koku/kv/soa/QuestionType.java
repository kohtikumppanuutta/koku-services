package fi.arcusys.koku.kv.soa;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
public enum QuestionType {

	YES_NO(fi.arcusys.koku.kv.service.datamodel.QuestionType.YES_NO_QUESTION),
	FREE_TEXT(fi.arcusys.koku.kv.service.datamodel.QuestionType.FREE_TEXT_QUESTION), 
	MULTIPLE_CHOICE(fi.arcusys.koku.kv.service.datamodel.QuestionType.MULTIPLE_CHOICE)
	;

	private final fi.arcusys.koku.kv.service.datamodel.QuestionType dmQuestionType;
	
	private static final Map<fi.arcusys.koku.kv.service.datamodel.QuestionType, QuestionType> dmToSoa = 
		new ConcurrentHashMap<fi.arcusys.koku.kv.service.datamodel.QuestionType, QuestionType>();
	
	static {
		for (final QuestionType soaType : values()) {
			dmToSoa.put(soaType.dmQuestionType, soaType);
		}
	}
	
	private QuestionType(fi.arcusys.koku.kv.service.datamodel.QuestionType dmQuestionType) {
		this.dmQuestionType = dmQuestionType;
	}

	/**
	 * @return
	 */
	public fi.arcusys.koku.kv.service.datamodel.QuestionType getDMQuestionType() {
		return this.dmQuestionType;
	}
	
	public static QuestionType valueOf(fi.arcusys.koku.kv.service.datamodel.QuestionType dmQuestionType) {
		return dmToSoa.get(dmQuestionType);
	}
}

package fi.arcusys.koku.kv.soa;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
public enum QuestionType {
	YES_NO(fi.arcusys.koku.kv.service.datamodel.QuestionType.YES_NO_QUESTION),
	FREE_TEXT(fi.arcusys.koku.kv.service.datamodel.QuestionType.FREE_TEXT_QUESTION);

	private final fi.arcusys.koku.kv.service.datamodel.QuestionType dmQuestionType;
	
	private QuestionType(fi.arcusys.koku.kv.service.datamodel.QuestionType dmQuestionType) {
		this.dmQuestionType = dmQuestionType;
	}

	/**
	 * @return
	 */
	public fi.arcusys.koku.kv.service.datamodel.QuestionType getDMQuestionType() {
		return this.dmQuestionType;
	}
}

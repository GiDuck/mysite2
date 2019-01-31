package com.douzone.mysite.action.reply;

import com.douzone.mvc.action.AbstractActionFactory;
import com.douzone.mvc.action.Action;

public class BoardReplyAbstractFactoy extends AbstractActionFactory{

	@Override
	public Action getAction(String actionName) {
		
		if("insert".equals(actionName)) {

			return new BoardReplyInsertAction();
			
		}else if("modify".equals(actionName)) {

			return new BoardReplyModifyAction();

			
		}else if("delete".equals(actionName)) {
			
			
			return new BoardReplyDeleteAction();
			
		}else {
		
			return null;
			
		}
		
	}

	
	
	
}

package com.douzone.mysite.action.board;

import com.douzone.mvc.action.AbstractActionFactory;
import com.douzone.mvc.action.Action;

public class BoardActionFactory extends AbstractActionFactory {

	@Override
	public Action getAction(String actionName) {

		System.out.println(actionName);

		if ("write".equals(actionName)) {

			return new InsertAction();

		} else if ("insertForm".equals(actionName)) {

			return new InsertFormAction();

		} else if ("modifyForm".equals(actionName)) {

			return new ModifyFormAction();

		}

		else if ("modify".equals(actionName)) {

			return new ModifyAction();

		} else if ("delete".equals(actionName)) {

			return new DeleteAction();

		} else if ("selectOneForm".equals(actionName)) {

			return new SelectOneFormAction();

		} else {

			return new ListBoardAction();

		}
	}

}

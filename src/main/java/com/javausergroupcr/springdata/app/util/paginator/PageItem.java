package com.javausergroupcr.springdata.app.util.paginator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageItem {
	private int number;
	private boolean current;
}

package com.crewmeister.cmcodingchallenge.commons.utility;

import java.util.Arrays;
import java.util.Optional;

public enum Sort {

	RATEDATE("rateDate");

	private String sortBy;

	Sort(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSort() {
		return sortBy;
	}

	public static Optional<Sort> getSort(String sortBy) {
		return Arrays.stream(Sort.values()).filter(env -> env.sortBy.equals(sortBy)).findFirst();
	}
}

package com.chinalife.datacheck.service;

import java.util.List;

import com.chinalife.datacheck.models.Resource;

public interface ResourceService {
	List<Resource> get(String username);
}

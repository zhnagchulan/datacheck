package com.chinalife.datacheck.dao;

import java.util.List;

import com.chinalife.datacheck.models.Resource;

public interface ResourceDao {
	List<Resource> get(String username);
}

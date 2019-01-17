package com.chinalife.datacheck.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinalife.datacheck.models.OrbpsLandQueue;

public interface DataCheckDao {
	List<OrbpsLandQueue> get(@Param("appl_no")String appl_no);
	List<OrbpsLandQueue> get();
}


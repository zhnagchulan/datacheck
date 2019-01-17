package com.chinalife.datacheck.service;

import java.util.List;


import com.chinalife.datacheck.models.OrbpsLandQueue;


public interface DataCheckService {
	List<OrbpsLandQueue> get(String appl_no);
	List<OrbpsLandQueue> get();
}

package com.leo.reportes.domain.gateway;

import com.leo.reportes.domain.models.Grade;
import com.leo.reportes.domain.models.User;

public interface StudentGateway {
    byte[] generateReport(long id);
    User generateResult(long id);
}

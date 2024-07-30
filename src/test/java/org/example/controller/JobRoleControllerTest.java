package org.example.controller;

import org.example.controllers.JobRoleController;
import org.example.exception.DatabaseConnectionException;
import org.example.exception.JobRoleDaoException;
import org.example.models.JobRoleResponse;
import org.example.models.SingleJobRoleResponse;
import org.example.services.JobRoleService;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JobRoleControllerTest {
    JobRoleService jobRoleService = mock(JobRoleService.class);

    private final JobRoleController jobRoleController = new JobRoleController(jobRoleService);

    @Test
    void getListOpenJobRoles_shouldReturnListOfJobRoleResponseInResponse_whenServiceReturnsListOfJobRoleResponse()
            throws JobRoleDaoException {
        List<JobRoleResponse> jobRoleResponses = new ArrayList<>();

        when(jobRoleService.getAllOpenJobRoles()).thenReturn(jobRoleResponses);

        Response response = jobRoleController.getListOpenJobRoles();

        assertEquals(200, response.getStatus());
        assertEquals(jobRoleResponses, response.getEntity());
    }

    @Test
    void getListOpenJobRoles_shouldReturn500StatusCode_whenServiceThrowsJobRoleDaoException()
            throws JobRoleDaoException {
        when(jobRoleService.getAllOpenJobRoles()).thenThrow(JobRoleDaoException.class);

        Response response = jobRoleController.getListOpenJobRoles();

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRoleById_shouldReturnJobRoleResponse_whenServiceReturnsSingleJobRoleResponse()
            throws JobRoleDaoException {
        LocalDate localDate = LocalDate.of(2024, 8, 15);
        Date closingDate = Date.valueOf(localDate);

        SingleJobRoleResponse singleJobRoleResponse = new SingleJobRoleResponse(
                "Software Engineer",
                "Toronto",
                "very capable",
                "band 1",
                closingDate,
                1,
                "coding a lot",
                "very responsible",
                "http://example.com/job-link/SoftwareEngineer"

        );
        when(jobRoleService.getJobRoleById(1)).thenReturn(singleJobRoleResponse);

        Response response = jobRoleController.getJobRoleById(1);
        assertEquals(200, response.getStatus());
        assertEquals(singleJobRoleResponse, response.getEntity());
    }

    @Test
    void getJobRoleById_shouldReturn500StatusCode_whenServiceReturnsJobRoleDaoException()
            throws JobRoleDaoException {
        when(jobRoleService.getJobRoleById(10)).thenThrow(JobRoleDaoException.class);

        Response response = jobRoleController.getJobRoleById(10);

        assertEquals(500, response.getStatus());
    }
}

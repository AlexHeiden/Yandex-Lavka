package ru.yandex.yandexlavka.pojo.request;

import ru.yandex.yandexlavka.pojo.dto.CreateAssignmentDto;

import java.util.List;

public class CreateAssignmentRequest {

    private List<CreateAssignmentDto> assignments;

    public CreateAssignmentRequest() {

    }

    public CreateAssignmentRequest(List<CreateAssignmentDto> assignments) {
        this.assignments = assignments;
    }

    public List<CreateAssignmentDto> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<CreateAssignmentDto> assignments) {
        this.assignments = assignments;
    }
}

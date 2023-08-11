package ru.yandex.yandexlavka.pojo.request;

import ru.yandex.yandexlavka.pojo.dto.CompleteOrderDto;

import java.util.List;

public class CompleteOrderRequest {

    private List<CompleteOrderDto> completeInfo;

    public CompleteOrderRequest() {

    }

    public CompleteOrderRequest(List<CompleteOrderDto> completeInfo) {
        this.completeInfo = completeInfo;
    }

    public List<CompleteOrderDto> getCompleteInfo() {
        return completeInfo;
    }

    public void setCompleteInfo(List<CompleteOrderDto> completeInfo) {
        this.completeInfo = completeInfo;
    }
}

package controller;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * class         : ListCommand
 * date          : 25-01-07
 * description   : from-to 날짜 정보를 담는 클래스
 */
public class ListCommand {


    @DateTimeFormat(pattern = "yyyyMMddHH")   // "2025010723" -> LocalDateTime으로 매핑
    private LocalDateTime from ;

    @DateTimeFormat(pattern = "yyyyMMddHH")    // "2025010723" -> LocalDateTime으로 매핑
    private LocalDateTime to ;

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }
}

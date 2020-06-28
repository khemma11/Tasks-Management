package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    private int id;
    private int userId;
    private int taskId;
    private String comment;
    private Date date;
    private User user;
    private Task task;
}

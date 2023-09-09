package kr.co.scheduler.scheduler.entity;

import jakarta.persistence.*;
import kr.co.scheduler.global.entity.BaseTimeEntity;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_task")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column
    private String idx;

    @Column
    private String task;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public Task(String idx, String task, Project project) {

        this.idx = idx;
        this.task = task;
        this.project = project;
    }

    public void updateTask(String idx, String task) {

        this.idx = idx;
        this.task = task;
    }
}
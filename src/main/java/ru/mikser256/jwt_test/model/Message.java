package ru.mikser256.jwt_test.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * класс сообщений
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "massages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "name")
    private User author;
    private String text;
    private LocalDateTime created;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", author=" + author +
                ", text='" + text + '\'' +
                ", created=" + created.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}

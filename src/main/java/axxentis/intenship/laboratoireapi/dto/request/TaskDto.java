package axxentis.intenship.laboratoireapi.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private Long id;
    private String libelle;
    private String description;
}
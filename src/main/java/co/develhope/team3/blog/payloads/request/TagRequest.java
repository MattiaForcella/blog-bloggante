package co.develhope.team3.blog.payloads.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class TagRequest {

    @Size(min = 15, message = "il tag deve contenere massimo 15 caratteri")
    @NotBlank(message = "ogni articolo deve essere associato almeno ad un tag")
    private String name;
}

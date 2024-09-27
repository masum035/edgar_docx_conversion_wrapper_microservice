package com.wsd.edgardocswrapper.dto.requestsDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.f4b6a3.ulid.UlidCreator;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRequestDTO {
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("password")
    private String password;
    
    @JsonProperty("client")
    private String client;
    
    @JsonProperty("subclientid")
    private String subClientID;
    
    @JsonProperty("formtype")
    private String formType;

    @Builder.Default
    @JsonProperty("zipfilename")
    private String zipFileName = UlidCreator.getMonotonicUlid().toString() + "-EdgarDefault.zip";
    
    @JsonProperty("templatename")
    private String templateName;
}

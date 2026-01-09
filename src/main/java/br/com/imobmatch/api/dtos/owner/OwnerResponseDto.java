package br.com.imobmatch.api.dtos.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OwnerResponseDto {

    public UUID id;
    public String name;
}

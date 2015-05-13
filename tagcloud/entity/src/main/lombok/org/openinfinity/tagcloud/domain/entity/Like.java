package org.openinfinity.tagcloud.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openinfinity.tagcloud.domain.entity.Entity;

import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = { "id" }, callSuper=false)
public class Like implements Entity {
	@Id
	private String id;
	
	@NonNull
	private Profile profile;
}

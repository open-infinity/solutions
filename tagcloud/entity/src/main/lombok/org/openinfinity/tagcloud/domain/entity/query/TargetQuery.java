package org.openinfinity.tagcloud.domain.entity.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TargetQuery {
	
	public enum Category{
		Location, Target;
	}
	
	@NonNull
	private String label;
	
	@NonNull
	private Category category;
    
	@NonNull
	private String id;
}

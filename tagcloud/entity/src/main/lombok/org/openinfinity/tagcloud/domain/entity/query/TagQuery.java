package org.openinfinity.tagcloud.domain.entity.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.openinfinity.tagcloud.domain.entity.Tag;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TagQuery {
    @NonNull
    private List<Tag> required;

    @NonNull
    private List<Tag> preferred;

    @NonNull
    private List<Tag> nearby;

    @NonNull
    private double longitude;

    @NonNull
    private double latitude;

    @NonNull
    private double radius;

}

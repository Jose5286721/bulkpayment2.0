package py.com.global.spm.GUISERVICE.dto;

import com.fasterxml.jackson.annotation.JsonView;
import py.com.global.spm.domain.utils.ScopeViews;

/**
 * Created by global on 3/14/18.
 */
@JsonView({ScopeViews.Basics.class, ScopeViews.Details.class})
public class MetaDTO {
    private Integer pageSize;
    private Integer totalPages;
    private Long totalCount;

    public MetaDTO() {
        // Do nothing
    }

    public MetaDTO(Integer pageSize, Integer totalPages, Long totalCount) {
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}

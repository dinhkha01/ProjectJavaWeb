package ra.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class PageDto<T> {
    private List<T> content; // Các phần tử trong trang
    private int currentPage; // Trang hiện tại
    private long totalPages; // Tổng số trang
    private int size; // Số phần tử trên mỗi trang
    private String keyword;
    private String sortBy; // Cột sắp xếp
    private String direction; // Hướng sắp xếp (asc, desc)
}
package ra.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Setter
@Getter
public class PageDto {
//    private List<User> content; //Cacs phan tu trong trang
    private int currentPage; // Trang hien tai
    private long totalPages; // Tong so trang
    private int size; // So phan tu tren moi trang
    private String keyword;
    private String sortBy; // Cot sap xep
    private String direction; // Huong sap xep (asc, desc)
}

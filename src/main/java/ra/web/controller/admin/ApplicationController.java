package ra.web.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.web.dto.PageDto;
import ra.web.dto.admin.ApplicationDto;
import ra.web.entity.Application;
import ra.web.entity.Application.ProgressStatus;
import ra.web.service.admin.ApplicationService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public String listApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) ProgressStatus progress,
            @RequestParam(required = false) String result,
            Model model) {

        PageDto<ApplicationDto> pageDto = applicationService.findAllWithPagination(
                page, size, sortBy, direction, progress, result);

        model.addAttribute("pageDto", pageDto);
        model.addAttribute("progressOptions", ProgressStatus.values());
        model.addAttribute("selectedProgress", progress);
        model.addAttribute("selectedResult", result);

        return "admin/application/list";
    }

    @GetMapping("/view/{id}")
    @Transactional(readOnly = true) // Thêm readOnly = true để tối ưu
    public String viewApplication(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Application application = applicationService.findById(id);

            // Kiểm tra null và validation
            if (application == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Đơn ứng tuyển không tồn tại");
                return "redirect:/admin/applications";
            }

            if (application.getDestroyAt() != null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Đơn ứng tuyển đã bị huỷ");
                return "redirect:/admin/applications";
            }

            // Debug logging
            System.out.println("Application ID: " + application.getId());
            System.out.println("Application Progress: " + application.getProgress());

            // Kiểm tra candidate và position có null không
            if (application.getCandidate() != null) {
                System.out.println("Candidate Name: " + application.getCandidate().getName());
                System.out.println("Candidate Email: " + application.getCandidate().getEmail());
            } else {
                System.out.println("Candidate is NULL!");
            }

            if (application.getRecruitmentPosition() != null) {
                System.out.println("Position Name: " + application.getRecruitmentPosition().getName());
            } else {
                System.out.println("RecruitmentPosition is NULL!");
            }

            // Cập nhật trạng thái nếu cần
            if (application.getProgress() == ProgressStatus.pending) {
                applicationService.viewApplication(id);
                // Refresh lại object sau khi update
                application = applicationService.findById(id);
            }

            model.addAttribute("application", application);
            return "admin/application/view";

        } catch (Exception e) {
            System.err.println("Error in viewApplication: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi tải dữ liệu đơn ứng tuyển");
            return "redirect:/admin/applications";
        }
    }

    @GetMapping("/cancel/{id}")
    public String cancelApplication(
            @PathVariable Integer id,
            @RequestParam String reason,
            RedirectAttributes redirectAttributes) {
        boolean success = applicationService.cancelApplication(id, reason);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Huỷ đơn ứng tuyển thành công");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Huỷ đơn ứng tuyển thất bại");
        }
        return "redirect:/admin/applications";
    }

    @PostMapping("/interview/{id}")
    public String moveToInterviewing(
            @PathVariable Integer id,
            @RequestParam LocalDateTime interviewRequestDate,
            @RequestParam String interviewLink,
            @RequestParam LocalDateTime interviewTime,
            RedirectAttributes redirectAttributes) {
        boolean success = applicationService.moveToInterviewing(id, interviewRequestDate, interviewLink, interviewTime);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Chuyển sang phỏng vấn thành công");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Chuyển sang phỏng vấn thất bại");
        }
        return "redirect:/admin/applications/view/" + id;
    }

    @PostMapping("/result/{id}")
    public String updateInterviewResult(
            @PathVariable Integer id,
            @RequestParam String result,
            @RequestParam String note,
            RedirectAttributes redirectAttributes) {
        boolean success = applicationService.updateInterviewResult(id, result, note);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật kết quả phỏng vấn thành công");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật kết quả phỏng vấn thất bại");
        }
        return "redirect:/admin/applications/view/" + id;
    }
}
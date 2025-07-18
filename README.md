# Thesis Management System

Hệ thống quản lý khóa luận tốt nghiệp được xây dựng nhằm hỗ trợ các nghiệp vụ của giáo vụ, giảng viên và sinh viên trong việc đăng ký, phân công, chấm điểm và thống kê liên quan đến khóa luận tốt nghiệp.

## 🧰 Công nghệ sử dụng

### Backend
- **Spring MVC**: Phát triển Restful API và xử lý logic nghiệp vụ.
- **Spring Security**: Xác thực và phân quyền người dùng.
- **MySQL**: Quản lý dữ liệu.
- **Hibernate/JPA**: ORM tương tác với cơ sở dữ liệu.

### Frontend
- **ReactJS**: Xây dựng giao diện người dùng phía client.
- **Axios**: Giao tiếp với backend qua REST API.

---

## 📌 Chức năng chính

### 🛡️ Phân hệ người dùng và phân quyền
- Người quản trị:
  - Quản lý tài khoản người dùng.
  - Gán vai trò: Giáo vụ, Giảng viên, Sinh viên.
- Người dùng:
  - Được thay đổi mật khẩu sau khi cấp tài khoản.
  - Tài khoản yêu cầu có avatar.
  
### 📚 Quản lý khóa luận (Giáo vụ)
- Tạo khóa luận mới: Nhập tên đề tài, chọn sinh viên thực hiện, tối đa **2 giảng viên hướng dẫn**.
- Phân công giảng viên phản biện: Hệ thống gửi **email/SMS** tự động đến người phản biện.
  
### 🏛️ Thành lập và quản lý hội đồng bảo vệ
- Tạo hội đồng gồm từ **3 đến 5 giảng viên**:
  - Vai trò: Chủ tịch, Thư ký, Phản biện, Thành viên.
- Mỗi hội đồng được phân công tối đa **5 khóa luận**.
- Giảng viên chấm điểm từng tiêu chí do giáo vụ thiết lập sẵn.
- Hệ thống:
  - Tự động tính điểm trung bình của từng khóa luận.
  - Ghi nhận mọi thông tin chấm điểm.
- Sau khi kết thúc, giáo vụ được quyền **khóa hội đồng**, giảng viên không thể chỉnh sửa điểm.

### 📊 Thống kê & báo cáo
- Quản trị viên và giáo vụ:
  - Thống kê điểm khóa luận theo năm.
  - Thống kê tần suất tham gia khóa luận theo ngành.
- Sinh viên:
  - Sau khi hội đồng khóa, nhận **email thông báo điểm trung bình** chính thức.
- Hệ thống hỗ trợ:
  - **Xuất file PDF** tổng hợp điểm trung bình để in và trình lãnh đạo ký duyệt.

---

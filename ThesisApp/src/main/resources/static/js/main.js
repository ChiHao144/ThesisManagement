function deleteAll(endpoint, id) {
    if (confirm("Bạn chắc chắn xóa không?") === true) { 
        fetch(endpoint + id, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                alert("Xóa thành công!");
                location.reload();
            } else 
                alert("Có lỗi xảy ra!");
        });
    }
}

function deleteThanhVien(endpoint, id) {
    if (confirm("Bạn chắc chắn xóa không? Tất cả điểm của thành viên cũng sẽ bị xóa!") === true) { 
        fetch(endpoint + id, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                alert("Xóa thành công!");
                location.reload();
            } else 
                alert("Lỗi xóa không thành công!");
        });
    }
}

function deleteTieuChi(endpoint, id) {
    if (confirm("Bạn chắc chắn xóa không? Tất cả điểm liên quan đến tiêu chí này cũng sẽ bị xóa") === true) { 
        fetch(endpoint + id, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                alert("Xóa thành công!");
                location.reload();
            } else 
                alert("Có lỗi xảy ra!");
        });
    }
}

function deleteKhoaLuan(endpoint, id) {
    if (confirm("Bạn chắc chắn xóa không? Tất cả điểm liên quan đến khóa luận này cũng sẽ bị xóa") === true) { 
        fetch(endpoint + id, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                alert("Xóa thành công!");
                location.reload();
            } else 
                alert("Có lỗi xảy ra!");
        });
    }
}

function deleteHoiDong(endpoint, id) {
    if (confirm("Bạn chắc chắn xóa không? Tất cả thành viên này cũng sẽ bị xóa") === true) { 
        fetch(endpoint + id, {
            method: "delete"
        }).then(res => {
            if (res.status === 204) {
                alert("Xóa thành công!");
                location.reload();
            } else 
                alert("Có lỗi xảy ra!");
        });
    }
}
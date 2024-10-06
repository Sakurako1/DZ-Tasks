$(document).ready(function () {
    loadStudentList();

    $('#addStudentForm').on('submit', function (e) {
        e.preventDefault();

        const studentData = {
            name: $('#name').val(),
            lastName: $('#lastName').val(),
            fatherName: $('#fatherName').val(),
            birthDate: $('#birthDate').val(),
            groupNumber: $('#groupNumber').val(),
            id: $('#id').val()
        };

        $.ajax({
            url: 'http://localhost:8080/students?action=add',
            type: 'POST',
	     contentType: 'application/json',
            data: JSON.stringify(studentData),
             success: function(response) {
        	console.log('Успех:', response);
       		 alert('Студент добавлен успешно!');
  		  },
            error: function () {
                alert('Failed to add student');
            }
        });
    });

    $('#deleteStudentForm').on('submit', function (e) {
        e.preventDefault();
  	

        const uniqueNumber = $('#idDeleted').val();
        $.ajax({
            url: 'http://localhost:8080/students?action=delete',
            type: 'POST',
	    contentType: 'application/json',
            data: uniqueNumber,
            success: function () {
                alert('Student deleted successfully');
                loadStudentList();
            },
            error: function () {
                alert('Failed to delete student');
            }
        });
    });

    function loadStudentList() {
        $.ajax({
            url: 'http://localhost:8080/students',
            type: 'GET',
            success: function (data) {
                const studentList = $('#studentList tbody');
                studentList.empty();
                data.forEach(student => {
                    const row = `<tr>
                        <td>${student.id}</td>
                        <td>${student.name}</td>
                        <td>${student.lastName}</td>
                        <td>${student.fatherName}</td>
                        <td>${student.birthDate}</td>
                        <td>${student.groupNumber}</td>
                    </tr>`;
                    studentList.append(row);
                });
            },
            error: function () {
                alert('Failed to load student list');
            }
        });
    }
});
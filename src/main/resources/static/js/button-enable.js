$(document).ready(function () {
    $("form").submit(function () { $(":submit", this).attr("disabled", "disabled"); });
    $('#submitButton').prop('disabled', true);
    $('.form-control').keyup(function () {
        checkButtonRegister();
    });
});

function checkButtonRegister() {
    var formControl = $('.form-control');
    var submitButton = $('#submitButton');
    // if (loginName !== '' && passwordHash !== '' && passwordHashRepeat !== '' && passwordLevel2 !== '' &&
    //     passwordLevel2Repeat !== '' && cRealName !== '' && cEmail !== ''){
    if (formControl.eq(0).val().trim() !== '' && formControl.eq(1).val().trim() !== '' && formControl.eq(2).val().trim() !== '' && formControl.eq(3).val().trim() !== '' && formControl.eq(4).val().trim() !== '' && formControl.eq(5).val().trim() !== '' && formControl.eq(6).val().trim() !== '') {
        submitButton.prop('disabled', false);
    } else {
        submitButton.prop('disabled', true);
    }
}
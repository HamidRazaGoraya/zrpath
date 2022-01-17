package com.hamid.template.ui.dashboard.models


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class ResponseEmployDetails(
    @SerializedName("Code")
    @Expose
    val code: String, // 200
    @SerializedName("Data")
    @Expose
    val `data`: Data,
    @SerializedName("Errors")
    @Expose
    val errors: List<Error>,
    @SerializedName("IsSuccess")
    @Expose
    val isSuccess: Boolean, // true
    @SerializedName("Message")
    @Expose
    val message: String // String
) {
    data class Data(
        @SerializedName("ConfirmPassword")
        @Expose
        val confirmPassword: Any?, // null
        @SerializedName("CredentialID")
        @Expose
        val credentialID: String, // BHP
        @SerializedName("Degree")
        @Expose
        val degree: Any?, // null
        @SerializedName("DepartmentID")
        @Expose
        val departmentID: Int, // 5
        @SerializedName("Email")
        @Expose
        val email: String, // sfazal@myeazycare.com
        @SerializedName("EmpSignature")
        @Expose
        val empSignature: Any?, // null
        @SerializedName("EmployeeID")
        @Expose
        val employeeID: Int, // 10493
        @SerializedName("EmployeeSignatureID")
        @Expose
        val employeeSignatureID: Int, // 20683
        @SerializedName("EncryptedValue")
        @Expose
        val encryptedValue: Any?, // null
        @SerializedName("FirstName")
        @Expose
        val firstName: String, // Sharib
        @SerializedName("IsActive")
        @Expose
        val isActive: Boolean, // true
        @SerializedName("IsDeleted")
        @Expose
        val isDeleted: Boolean, // false
        @SerializedName("IsDepartmentSupervisor")
        @Expose
        val isDepartmentSupervisor: Boolean, // false
        @SerializedName("IsSecurityQuestionSubmitted")
        @Expose
        val isSecurityQuestionSubmitted: Boolean, // true
        @SerializedName("IsTempPassword")
        @Expose
        val isTempPassword: Boolean, // false
        @SerializedName("IsVerify")
        @Expose
        val isVerify: Boolean, // true
        @SerializedName("LastName")
        @Expose
        val lastName: String, // Employee
        @SerializedName("MiddleName")
        @Expose
        val middleName: String, // fazal
        @SerializedName("NewPassword")
        @Expose
        val newPassword: Any?, // null
        @SerializedName("Password")
        @Expose
        val password: String, // $2a$12$w3yRO.AmxOANjUB6O8GUiOuf1DesRVP0IwhhhAUUCP0uIAIK1i8le
        @SerializedName("PasswordSalt")
        @Expose
        val passwordSalt: String, // $2a$12$w3yRO.AmxOANjUB6O8GUiO
        @SerializedName("PhoneHome")
        @Expose
        val phoneHome: String, // String
        @SerializedName("PhoneWork")
        @Expose
        val phoneWork: String, // 3425436346
        @SerializedName("Pin")
        @Expose
        val pin: String, // 1234
        @SerializedName("PinSalt")
        @Expose
        val pinSalt: Any?, // null
        @SerializedName("ProfileImagePath")
        @Expose
        val profileImagePath: Any?, // null
        @SerializedName("ResetPasswordCode")
        @Expose
        val resetPasswordCode: String, // 360193
        @SerializedName("ResetPasswordExpiryTime")
        @Expose
        val resetPasswordExpiryTime: String, // 2021-12-23T06:56:54 -08:00
        @SerializedName("RoleID")
        @Expose
        val roleID: Int, // 1
        @SerializedName("SecurityAnswer")
        @Expose
        val securityAnswer: String, // yes
        @SerializedName("SecurityQuestionID")
        @Expose
        val securityQuestionID: Int, // 1
        @SerializedName("SignaturePath")
        @Expose
        val signaturePath: Any?, // null
        @SerializedName("TempConfirmPassword")
        @Expose
        val tempConfirmPassword: Any?, // null
        @SerializedName("TempSignaturePath")
        @Expose
        val tempSignaturePath: Any?, // null
        @SerializedName("UserName")
        @Expose
        val userName: String // sfazal
    )

    data class Error(
        @SerializedName("Field")
        @Expose
        val `field`: String, // string
        @SerializedName("Message")
        @Expose
        val message: String // string
    )
}
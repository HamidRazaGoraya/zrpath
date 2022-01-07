package com.hamid.template.ui.loginAndRegister.logResponseModel


import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("ConfirmPassword")
    val confirmPassword: String,
    @SerializedName("CredentialID")
    val credentialID: String,
    @SerializedName("Degree")
    val degree: String,
    @SerializedName("DepartmentID")
    val departmentID: Int,
    @SerializedName("Email")
    val email: String,
    @SerializedName("EmpSignature")
    val empSignature: String,
    @SerializedName("EmployeeID")
    val employeeID: Int,
    @SerializedName("EmployeeSignatureID")
    val employeeSignatureID: Int,
    @SerializedName("EncryptedValue")
    val encryptedValue: String,
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("IsActive")
    val isActive: Boolean,
    @SerializedName("IsDeleted")
    val isDeleted: Boolean,
    @SerializedName("IsDepartmentSupervisor")
    val isDepartmentSupervisor: Boolean,
    @SerializedName("IsSecurityQuestionSubmitted")
    val isSecurityQuestionSubmitted: Boolean,
    @SerializedName("IsTempPassword")
    val isTempPassword: Boolean,
    @SerializedName("IsVerify")
    val isVerify: Boolean,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("MiddleName")
    val middleName: String,
    @SerializedName("NewPassword")
    val newPassword: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("PasswordSalt")
    val passwordSalt: String,
    @SerializedName("PhoneHome")
    val phoneHome: String,
    @SerializedName("PhoneWork")
    val phoneWork: String,
    @SerializedName("Pin")
    val pin: String,
    @SerializedName("PinSalt")
    val pinSalt: String,
    @SerializedName("ProfileImagePath")
    val profileImagePath: String,
    @SerializedName("ResetPasswordCode")
    val resetPasswordCode: String,
    @SerializedName("ResetPasswordExpiryTime")
    val resetPasswordExpiryTime: String,
    @SerializedName("RoleID")
    val roleID: Int,
    @SerializedName("SecurityAnswer")
    val securityAnswer: String,
    @SerializedName("SecurityQuestionID")
    val securityQuestionID: Int,
    @SerializedName("SignaturePath")
    val signaturePath: String,
    @SerializedName("TempConfirmPassword")
    val tempConfirmPassword: String,
    @SerializedName("TempSignaturePath")
    val tempSignaturePath: String,
    @SerializedName("UserName")
    val userName: String
)
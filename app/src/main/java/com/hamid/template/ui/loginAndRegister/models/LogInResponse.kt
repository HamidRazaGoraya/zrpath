package com.hamid.template.ui.loginAndRegister.models

import com.google.gson.annotations.SerializedName

data class LogInResponse(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

	@field:SerializedName("Message")
	val message: String?,

	@field:SerializedName("Data")
	val data: Data,

	@field:SerializedName("ErrorCode")
	val errorCode: Any? = null
)

data class PermissionsItem(

	@field:SerializedName("PermissionID")
	val permissionID: Int
)

data class Data(

	@field:SerializedName("SessionValueData")
	val sessionValueData: SessionValueData,

	@field:SerializedName("Email")
	val email: String,

	@field:SerializedName("IsNotVerifiedEmail")
	val isNotVerifiedEmail: Boolean
)

data class SessionValueData(

	@field:SerializedName("IsTempPassword")
	val isTempPassword: Boolean,

	@field:SerializedName("EmployeeSignatureID")
	val employeeSignatureID: Int,

	@field:SerializedName("UserName")
	val userName: String,

	@field:SerializedName("Email")
	val email: String,

	@field:SerializedName("ProfileImagePath")
	val profileImagePath: String,

	@field:SerializedName("FirstName")
	val firstName: String,

	@field:SerializedName("EmpCredential")
	val empCredential: String,

	@field:SerializedName("IsSecurityQuestionSubmitted")
	val isSecurityQuestionSubmitted: Boolean,

	@field:SerializedName("RoleID")
	val roleID: Int,

	@field:SerializedName("MiddelName")
	val middelName: String,

	@field:SerializedName("Permissions")
	val permissions: List<PermissionsItem>,

	@field:SerializedName("LastName")
	val lastName: String,

	@field:SerializedName("EmployeeID")
	val employeeID: Int
)

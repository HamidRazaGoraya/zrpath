package com.hamid.template.ui.dashboard.models

import com.google.gson.annotations.SerializedName

data class ResponseDashBoard(

	@field:SerializedName("IsSuccess")
	val isSuccess: Boolean,

	@field:SerializedName("Errors")
	val errors: Any? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Data")
	val data: Data? = null,

	@field:SerializedName("Code")
	val code: String? = null
){
	data class Data(

		@field:SerializedName("Employee")
		val employee: Employee? = null,

		@field:SerializedName("TodayVisits")
		val todayVisits: List<VisitItem>? = null,

		@field:SerializedName("NextDayVisits")
		val nextDayVisits: List<VisitItem>? = null
	){
		data class Employee(

			@field:SerializedName("IsTempPassword")
			val isTempPassword: Boolean? = null,

			@field:SerializedName("ResetPasswordCode")
			val resetPasswordCode: Any? = null,

			@field:SerializedName("Email")
			val email: String? = null,

			@field:SerializedName("ProfileImagePath")
			val profileImagePath: Any? = null,

			@field:SerializedName("IsVerify")
			val isVerify: Boolean? = null,

			@field:SerializedName("IsActive")
			val isActive: Boolean? = null,

			@field:SerializedName("SignaturePath")
			val signaturePath: Any? = null,

			@field:SerializedName("RoleID")
			val roleID: Int? = null,

			@field:SerializedName("SecurityQuestionID")
			val securityQuestionID: Any? = null,

			@field:SerializedName("ResetPasswordExpiryTime")
			val resetPasswordExpiryTime: Any? = null,

			@field:SerializedName("IsDepartmentSupervisor")
			val isDepartmentSupervisor: Boolean? = null,

			@field:SerializedName("PhoneHome")
			val phoneHome: Any? = null,

			@field:SerializedName("DepartmentID")
			val departmentID: Any? = null,

			@field:SerializedName("EncryptedValue")
			val encryptedValue: Any? = null,

			@field:SerializedName("Password")
			val password: Any? = null,

			@field:SerializedName("SecurityAnswer")
			val securityAnswer: Any? = null,

			@field:SerializedName("NewPassword")
			val newPassword: Any? = null,

			@field:SerializedName("PhoneWork")
			val phoneWork: Any? = null,

			@field:SerializedName("EmpSignature")
			val empSignature: Any? = null,

			@field:SerializedName("EmployeeSignatureID")
			val employeeSignatureID: Int? = null,

			@field:SerializedName("IsDeleted")
			val isDeleted: Boolean? = null,

			@field:SerializedName("UserName")
			val userName: Any? = null,

			@field:SerializedName("CredentialID")
			val credentialID: Any? = null,

			@field:SerializedName("Degree")
			val degree: Any? = null,

			@field:SerializedName("FirstName")
			val firstName: String? = null,

			@field:SerializedName("PasswordSalt")
			val passwordSalt: Any? = null,

			@field:SerializedName("TempConfirmPassword")
			val tempConfirmPassword: Any? = null,

			@field:SerializedName("IsSecurityQuestionSubmitted")
			val isSecurityQuestionSubmitted: Boolean? = null,

			@field:SerializedName("MiddleName")
			val middleName: Any? = null,

			@field:SerializedName("ConfirmPassword")
			val confirmPassword: Any? = null,

			@field:SerializedName("Pin")
			val pin: Any? = null,

			@field:SerializedName("LastName")
			val lastName: String? = null,

			@field:SerializedName("EmployeeID")
			val employeeID: Int? = null,

			@field:SerializedName("TempSignaturePath")
			val tempSignaturePath: Any? = null,

			@field:SerializedName("PinSalt")
			val pinSalt: Any? = null
		)
		data class VisitItem(
			@field:SerializedName("FacilityID")
			val FacilityID:Int,
			@field:SerializedName("GroupTripStatus")
			val GroupTripStatus:String?=null,

			@field:SerializedName("IsTripStarted")
			val IsTripStarted:String? =null,

			@field:SerializedName("GroupName")
			val groupName: String,

			@field:SerializedName("IsTripComplete")
			val isTripComplete: Boolean,

			@field:SerializedName("ReferralName")
			val referralName: String,

			@field:SerializedName("TransportationGroupID")
			val transportationGroupID: Int,

			@field:SerializedName("ReferralID")
			val referralID: Int,

			@field:SerializedName("PickUpTime")
			val pickUpTime: String? = null,

			@field:SerializedName("Latitude")
			val latitude: Double,

			@field:SerializedName("TransportationDate")
			val transportationDate: String? = null,

			@field:SerializedName("ScheduleID")
			val scheduleID: Int,

			@field:SerializedName("Longitude")
			val longitude: Double,

			@field:SerializedName("DropOffTime")
			val dropOffTime: String? = null
		)

	}
}



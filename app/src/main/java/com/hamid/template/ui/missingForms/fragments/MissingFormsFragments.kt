package com.hamid.template.ui.missingForms.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.model.Image
import com.hamid.template.base.BaseFragment
import com.hamid.template.databinding.UploadedFormsBinding
import com.hamid.template.ui.missingForms.MissingFormsVM
import com.hamid.template.ui.missingForms.adopter.MissingFormsAdopter
import com.hamid.template.ui.missingForms.eventListners.MissingFormClicked
import com.hamid.template.ui.missingForms.model.RequestUserMissingDocument
import com.hamid.template.ui.missingForms.model.ResponseUserMissingDocument
import com.hamid.template.utils.*
import com.hamid.template.utils.dialogs.DialogImagePicker
import com.hamid.template.utils.dialogs.SelectUploadedDocument
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.scanlibrary.ScanActivity
import com.scanlibrary.ScanConstants
import dagger.hilt.android.AndroidEntryPoint
import id.zelory.compressor.Compressor
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class MissingFormsFragments : BaseFragment<UploadedFormsBinding, MissingFormsVM>() {

    override val viewModel: MissingFormsVM by activityViewModels()

    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager

    lateinit var uploadedFormsAdopter: MissingFormsAdopter

    var REQUEST_GALLERY_PRODUCT = 1002
    override fun setBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): UploadedFormsBinding {
        return UploadedFormsBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       setOnClickEvents()
    }

    private fun setOnClickEvents() {
        binding.addNewForm.setOnClickListener {
            if (viewModel.responseDocumentList!=null){
                viewModel.showSelectFormDialog(viewModel.responseDocumentList!!,viewModel.client)
            }else{
                viewModel.ShowLoading()
                viewModel.getFormsList(true,viewModel.client)
            }
        }
        binding.addNewForm.makeGone()
        uploadedFormsAdopter= MissingFormsAdopter(object : MissingFormClicked {
            override fun openClicked(item: ResponseUserMissingDocument.DataItem) {

                SelectUploadedDocument(object :SelectUploadedDocument.Buttons{
                    override fun Upload() {
                       ShowImageChooser(item)
                    }

                    override fun AddNew() {
                        if (item.eBFormID!=null){
                            viewModel.apiCallForUrl(item.eBFormID,viewModel.client)
                        }else{
                            showSnackBar("Form ID not found")
                        }

                    }
                },!item.eBFormID.isNullOrEmpty()).show(childFragmentManager,"SelectUploadedDocument")
            }

            override fun editClicked(item: ResponseUserMissingDocument.DataItem) {

            }

            override fun deleteClicked(item: ResponseUserMissingDocument.DataItem) {

            }
        })
        binding.missingFormsRecycle.adapter=uploadedFormsAdopter

        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing=true
            loadFilledForms()
        }
        loadFilledForms()
    }

    private fun ShowImageChooser(item: ResponseUserMissingDocument.DataItem) {
        viewModel.documentType=item.documentType
        viewModel.fileName=item.name.checkNull()
       DialogImagePicker(object :DialogImagePicker.Buttons{
           override fun CameraImage() {

               Dexter.withContext(requireContext())
                   .withPermissions(
                       Manifest.permission.READ_EXTERNAL_STORAGE,
                       Manifest.permission.WRITE_EXTERNAL_STORAGE,
                       Manifest.permission.CAMERA
                   ).withListener(object : MultiplePermissionsListener {
                       override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                           if (report.areAllPermissionsGranted()) {
                               val intent = Intent(requireContext(), ScanActivity::class.java)
                               intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, ScanConstants.OPEN_CAMERA)
                               activityForCameraImage.launch(intent)
                           }else
                               if (report.isAnyPermissionPermanentlyDenied){
                                   showSnackBar("Camera and Storage permission required")
                               }
                       }

                       override fun onPermissionRationaleShouldBeShown(
                           permissions: List<PermissionRequest?>?,
                           token: PermissionToken?
                       ) {

                           token!!.continuePermissionRequest()
                       }
                   }).check()

           }

           override fun GalleryImage() {

               Dexter.withContext(requireContext())
                   .withPermissions(
                       Manifest.permission.READ_EXTERNAL_STORAGE,
                       Manifest.permission.WRITE_EXTERNAL_STORAGE
                   ).withListener(object : MultiplePermissionsListener {
                       override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                           if (report.areAllPermissionsGranted()) {
                               getContent.launch("image/*")
                           }else
                               if (report.isAnyPermissionPermanentlyDenied){
                                   showSnackBar("Storage permission required")
                               }
                       }

                       override fun onPermissionRationaleShouldBeShown(
                           permissions: List<PermissionRequest?>?,
                           token: PermissionToken?
                       ) {

                           token!!.continuePermissionRequest()
                       }
                   }).check()


           }
       },"Choose Image").show(childFragmentManager,"ImagePicker")
    }


    val activityForCameraImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            requireActivity().runOnUiThread {
               val mCapturedImageURI = result.data!!.getExtras()!!.getParcelable<Uri>(ScanConstants.SCANNED_RESULT)
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                if (mCapturedImageURI != null) {
                    val cursor: Cursor = requireActivity().managedQuery(mCapturedImageURI, projection, null, null, null)
                    val column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()
                    val capturedImageFilePath = cursor.getString(column_index_data)
                    uploadFiles(File(capturedImageFilePath))
                 }

            }
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                ReduceImagesSize.compressImage(it,requireContext())?.let {
                    uploadFiles(File(FileManager.getPath(requireContext(), it)))
                }
            }
        }



    fun loadFilledForms() {
        viewModel.SetMissingDocument(RequestUserMissingDocument.Data(viewModel.client.ReferralID)).observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.data?.let { data->
                        data.data?.let { formsList->

                            uploadedFormsAdopter.updateItems(formsList)
                        }
                    }

                }
                Resource.Status.ERROR -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Resource.Status.LOADING -> {
                    viewModel.ShowLoading()
                }
            }
        }
    }



    fun uploadFiles(file: File){
        viewModel.UploadDocument(viewModel.fileName,viewModel.client.ReferralID,viewModel.documentType,file).observe(viewLifecycleOwner){
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.data?.let { data->

                    }

                }
                Resource.Status.ERROR -> {
                    binding.swipe.isRefreshing=false
                    viewModel.HideLoading()
                    it.message?.let { it1 -> showSnackBar(it1) }
                }
                Resource.Status.LOADING -> {
                    viewModel.ShowLoading()
                }
            }
        }
    }

}
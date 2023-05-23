package com.ju.simplequiz2204

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ju.simplequiz2204.class29.ChatFragment

import com.ju.simplequiz2204.class29.User
import com.ju.simplequiz2204.databinding.FragmentProfileBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    lateinit var firebaseStorage: StorageReference

    var firebaseUser: FirebaseUser? = null

    lateinit var firebaseDatabaseReference: DatabaseReference


    lateinit var user: User
    private lateinit var fileUri: Uri
    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                fileUri = data?.data!!

                binding.profileImage.setImageURI(fileUri)

                binding.pickAnImage.text = UPLOAD


            } else if (resultCode == ImagePicker.RESULT_ERROR) {

            } else {

            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        user = requireArguments().getParcelable<User>(USER)!!
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        firebaseStorage = FirebaseStorage.getInstance().getReference("Upload")

        firebaseUser = FirebaseAuth.getInstance().currentUser

        val database = Firebase.database
        firebaseDatabaseReference = database.reference.child("User").child(user.userId)

        firebaseDatabaseReference.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val fUser: User = snapshot.getValue(User::class.java)!!
                    binding.profileImage.load(fUser.profileImgUrl)

                    if (firebaseUser!!.uid != fUser.userId) {
                        binding.pickAnImage.text = MESSAGE
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })





        binding.mobileTv.text = user.phone
        binding.nameTv.text = user.email
        binding.pickAnImage.setOnClickListener {

            if (binding.pickAnImage.text == UPLOAD) {
                startImageUpload()
            } else if (binding.pickAnImage.text == MESSAGE) {

                val bundle = Bundle()

                bundle.putParcelable(ChatFragment.USER, user)

                findNavController().navigate(R.id.action_profileFragment_to_chatFragment, bundle)


                Toast.makeText(requireContext(), "Message Clicked", Toast.LENGTH_SHORT).show()

            } else {
                requestPermissions()
            }

        }

        return binding.root
    }

    private fun startImageUpload() {
        var storageReference: StorageReference =
            firebaseStorage.child("Profile").child("profile-img-${user.userId}")

        storageReference.putFile(fileUri).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                storageReference.downloadUrl.addOnSuccessListener {

                    var url: String = it.toString()

                    val map = mapOf(

                        "profileImgUrl" to url
                    )


                    firebaseDatabaseReference.updateChildren(map)
                        .addOnSuccessListener { profileUrl ->

                            Toast.makeText(
                                requireContext(),
                                "Profile image Uploaded",
                                Toast.LENGTH_LONG
                            ).show()

                            binding.profileImage.load(profileUrl)


                        }


                }


            }


        }


    }

    companion object {

        const val USER = "user"
        const val UPLOAD = "Upload"
        const val MESSAGE = "message"


    }


    private fun requestPermissions() {

        Dexter.withContext(requireActivity())
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    // this method is called when all permissions are granted
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        // do you work now
                        pickAnImage()

                    }
                    // check for permanent denial of any permission
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {


                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest?>?,
                    permissionToken: PermissionToken
                ) {
                    // this method is called when user grants some permission and denies some of them.
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener { error: DexterError? ->
                // we are displaying a toast message for error message.

            } // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()
    }


    fun pickAnImage() {

        ImagePicker.with(requireActivity())
            .crop()
            .compress(500)         //Final image size will be less than 2 MB(Optional)
            .maxResultSize(
                200,
                200
            )  //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }

    }


}
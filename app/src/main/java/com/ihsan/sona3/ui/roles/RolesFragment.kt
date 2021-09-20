package com.ihsan.sona3.ui.roles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ihsan.sona3.BaseFragment
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.db.entities.User
import com.ihsan.sona3.databinding.RolesFragmentBinding
import com.ihsan.sona3.utils.UserRoleEnum
import com.ihsan.sona3.utils.show
import com.ihsan.sona3.utils.toast
import net.igenius.customcheckbox.CustomCheckBox
import timber.log.Timber

class RolesFragment : BaseFragment<RolesFragmentBinding>(), View.OnClickListener,
    RolesContract.View {

    private var checkboxGroup = ArrayList<CustomCheckBox>()
    private var checkedRole: Int? = null
    private val args: RolesFragmentArgs by navArgs()
    private var user: User? = null
    private lateinit var db: AppDatabase
    private lateinit var navController: NavController

    private lateinit var rolesPresenter: RolesPresenter


    override fun onClick(v: View?) {
        when (v!!.id) {

            //Selected role
            R.id.ckEditor -> validateCheckBox(0)
            R.id.ckResearcher -> validateCheckBox(1)
            R.id.ckReviewer -> validateCheckBox(2)
            R.id.ckVerifier -> validateCheckBox(3)

            //Show role
            R.id.ivEditor -> setBottomSheetDialog(R.id.ivEditor)
            R.id.ivResearcher -> setBottomSheetDialog(R.id.ivResearcher)
            R.id.ivVerifier -> setBottomSheetDialog(R.id.ivVerifier)
            R.id.ivReviewer -> setBottomSheetDialog(R.id.ivReviewer)

            //Next Button
            R.id.btnNext -> nextButtonPressed()
        }
    }

    private fun nextButtonPressed() {
        Timber.i("NextButton Pressed")

        if (checkedRole != null) {
            //Get user Role
            val userRole = getSelectedRole(checkedRole!!)
            showProgressDialog(requireContext())
            rolesPresenter.updateUserRole(user, userRole)
        } else
            requireContext().toast("يجب اختيار احد الوظائف")

    }

    private fun validateCheckBox(position: Int) {

        //val checkboxGroup = ArrayList<CustomCheckBox>()
        checkboxGroup.add(0, binding.ckEditor)
        checkboxGroup.add(1, binding.ckResearcher)
        checkboxGroup.add(2, binding.ckReviewer)
        checkboxGroup.add(3, binding.ckVerifier)

        for (checkbox in checkboxGroup)
            if (checkboxGroup[position] == checkbox) {
                checkbox.setChecked(true, true)
                checkedRole = position //Save the last position user checked
            } else checkbox.setChecked(false, false)

    }

    private fun setBottomSheetDialog(id: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheet = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_roles_bottom_sheet, null, false)
        dialog.setContentView(bottomSheet)

        val ivShowReviewForms = bottomSheet.findViewById<ImageView>(R.id.ivShowReviewForms)
        val ivShowSearchForms = bottomSheet.findViewById<ImageView>(R.id.ivShowSearchForms)
        val ivShowVerifyForms = bottomSheet.findViewById<ImageView>(R.id.ivShowVerifyForms)
        when (id) {
            R.id.ivResearcher -> ivShowSearchForms.show()

            R.id.ivReviewer -> {
                ivShowReviewForms.show()
                ivShowSearchForms.show()
            }
            R.id.ivVerifier -> {
                ivShowVerifyForms.show()
                ivShowSearchForms.show()
                ivShowReviewForms.show()
            }
        }
        dialog.show()
    }

    /**
     * Get Selected User role From the selected CheckBox
     * [ 0 -> محرر ]
     * [ 1 -> باحث ]
     * [ 2 -> مراجع ]
     * [ 3 -> معتمد ]
     */
    private fun getSelectedRole(role: Int): String? {
        when (role) {
            0 -> return UserRoleEnum.Editor.toString().toLowerCase()
            1 -> return UserRoleEnum.Researcher.toString().toLowerCase()
            2 -> return UserRoleEnum.Reviewer.toString().toLowerCase()
            3 -> return UserRoleEnum.Supervisor.toString().toLowerCase()
        }
        return null
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> RolesFragmentBinding
        get() = RolesFragmentBinding::inflate

    override fun setupOnViewCreated(view: View) {
        binding.ckEditor.setOnClickListener(this)
        binding.ckReviewer.setOnClickListener(this)
        binding.ckResearcher.setOnClickListener(this)
        binding.ckVerifier.setOnClickListener(this)
        binding.ivEditor.setOnClickListener(this)
        binding.ivResearcher.setOnClickListener(this)
        binding.ivVerifier.setOnClickListener(this)
        binding.ivReviewer.setOnClickListener(this)
        binding.btnNext.setOnClickListener(this)

        navController = Navigation.findNavController(view)

        //Get the userData from loginFragment..
        user = args.userData

        //Set the last selected role.
        when (user!!.user_role) {
            UserRoleEnum.Editor.toString().toLowerCase() -> validateCheckBox(0)
            UserRoleEnum.Researcher.toString().toLowerCase() -> validateCheckBox(1)
            UserRoleEnum.Reviewer.toString().toLowerCase() -> validateCheckBox(2)
            UserRoleEnum.Supervisor.toString().toLowerCase() -> validateCheckBox(3)
        }

        db = AppDatabase.invoke(requireContext())
        rolesPresenter = RolesPresenter(db, this)
    }


    override fun onSuccess() {
        hideProgressDialog()

        if (checkedRole != 0)
            showHintDialog()
        else navController.navigate(R.id.action_rolesFragment_to_nav_home)
    }

    override fun onError(message: String) {
        hideProgressDialog()
        Timber.e("Error MSG: $message")
    }

    private fun showHintDialog() {

        val customDialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

        val dialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setView(customDialogView)
            .create()

        val button = customDialogView.findViewById<Button>(R.id.okDialogButton)

        button.setOnClickListener {
            Timber.i("Button")
            dialog.cancel()
            navController.navigate(R.id.action_rolesFragment_to_nav_home)
        }

        dialog.show()
    }
}
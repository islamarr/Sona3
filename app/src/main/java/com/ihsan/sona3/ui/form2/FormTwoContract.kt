
package com.ihsan.sona3.ui.form2

import com.ihsan.sona3.data.model.form2.Form2Data

interface FormTwoContract {
    interface View {
        fun onMembersReady(memberList : ArrayList<Form2Data>)
        fun onUpdateMembers(memberList : ArrayList<Form2Data>)
    }

    interface Presenter {
        fun getMembers()
        fun addMembers()
        fun onDestroy()
    }

}
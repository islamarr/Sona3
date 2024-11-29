
package com.ihsan.sona3.ui.form2

import com.ihsan.sona3.data.model.form2.Form2Data

class FormTwoPresenter(view: FormTwoContract.View) : FormTwoContract.Presenter {

    private val itemList = ArrayList<Form2Data>()
    private var view: FormTwoContract.View? = view

    override fun getMembers() {
        itemList.add(Form2Data("osama",1234568951,"0111101215","01255555541","","","","kygiygiy",1,15,"ubub"))
        view?.onMembersReady(itemList)
    }

    override fun addMembers() {
        itemList.add(Form2Data("osama",1234568951,"0111101215","01255555541","","","","kygiygiy",1,15,"ubub"))
        view?.onUpdateMembers(itemList)
    }

    override fun onDestroy() {
        view = null
    }

}
package tw.north27.coachingapp.adapter.education

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.response.EducationLevel

class EducationLevelAdapter : BaseAdapter() {
    private var educationLevelList: List<EducationLevel>? = null

    fun submitData(educationLevelList: List<EducationLevel>) {
        this.educationLevelList = educationLevelList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return educationLevelList?.size ?: 0
    }

    override fun getItem(position: Int): EducationLevel? {
        return educationLevelList?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return educationLevelList?.get(position)?.id ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: VH
        var view: View? = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(parent?.context)
            view = inflater.inflate(R.layout.item_spinner_text, parent, false)
            holder = VH(view)
            view?.tag = holder
        } else {
            holder = view.tag as VH
        }
        holder.tvText.text = getItem(position)?.name
        return view!!
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvText: TextView = view.findViewById(R.id.tv_text)
    }
}
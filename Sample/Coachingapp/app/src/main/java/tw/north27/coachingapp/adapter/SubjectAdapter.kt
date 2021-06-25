package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.response.Subject

class SubjectAdapter : BaseAdapter() {
    private var subjectList: List<Subject>? = null

    fun submitData(subjectList: List<Subject>) {
        this.subjectList = subjectList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return subjectList?.size ?: 0
    }

    override fun getItem(position: Int): Subject? {
        return subjectList?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return subjectList?.get(position)?.id ?: 0
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
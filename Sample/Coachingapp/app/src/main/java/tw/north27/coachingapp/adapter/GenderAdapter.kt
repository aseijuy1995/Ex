package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.Gender

class GenderAdapter : BaseAdapter() {
    private var genderList: List<Pair<Gender, String>>? = null

    fun submitData(genderList: List<Pair<Gender, String>>) {
        this.genderList = genderList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return genderList?.size ?: 0
    }

    override fun getItem(position: Int): Pair<Gender, String>? {
        return genderList?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return genderList?.get(position).hashCode().toLong()
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
        holder.tvText.text = getItem(position)?.second
        return view!!
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvText: TextView = view.findViewById(R.id.tv_text)
    }
}
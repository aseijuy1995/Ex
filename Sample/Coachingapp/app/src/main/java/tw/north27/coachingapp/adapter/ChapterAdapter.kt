package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.Chapter

class ChapterAdapter : BaseAdapter() {
    private var chapterList: List<Chapter>? = null

    fun submitData(chapterList: List<Chapter>? = null) {
        this.chapterList = chapterList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return chapterList?.size ?: 0
    }

    override fun getItem(position: Int): Chapter? {
        return chapterList?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return chapterList?.get(position)?.id ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: VH
        var view: View? = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(parent?.context)
            view = inflater.inflate(R.layout.item_spinner_list, parent, false)
            holder = VH(view)
            view?.tag = holder
        } else {
            holder = view.tag as VH
        }
        val chapter = getItem(position)
        holder.tvText.text = chapter?.text
        return view!!
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvText: TextView = view.findViewById(R.id.tv_text)
    }
}
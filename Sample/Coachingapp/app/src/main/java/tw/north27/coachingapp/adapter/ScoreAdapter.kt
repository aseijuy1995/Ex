package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.R

class ScoreAdapter : BaseAdapter() {
    private var scoreList: List<Double>? = null

    fun submitData(scoreList: List<Double>?) {
        this.scoreList = scoreList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return scoreList?.size ?: 0
    }

    override fun getItem(position: Int): Double? {
        return scoreList?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return scoreList?.get(position)?.toLong() ?: 0
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
        holder.tvText.text = if (getItem(position) == -1.0) view?.context?.getString(R.string.df) else getItem(position).toString()
        return view!!
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvText: TextView = view.findViewById(R.id.tv_text)
    }
}
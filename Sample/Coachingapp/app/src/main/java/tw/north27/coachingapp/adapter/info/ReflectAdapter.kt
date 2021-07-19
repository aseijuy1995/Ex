package tw.north27.coachingapp.adapter.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.response.Reflect

class ReflectAdapter : BaseAdapter() {
    private var reflectList: List<Reflect>? = null

    fun submitData(reflectList: List<Reflect>) {
        this.reflectList = reflectList
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return reflectList?.size ?: 0
    }

    override fun getItem(position: Int): Reflect? {
        return reflectList?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return reflectList?.get(position)?.id ?: -1
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
        holder.tvText.text = reflectList?.get(position)?.name
        return view!!
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvText: TextView = view.findViewById(R.id.tv_text)
    }
}
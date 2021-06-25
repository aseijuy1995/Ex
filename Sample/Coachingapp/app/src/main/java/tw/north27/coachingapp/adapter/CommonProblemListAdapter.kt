package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yujie.utilmodule.adapter.bindImg
import com.yujie.utilmodule.view.AnimatedExpandableListView
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.response.CommonProblem

class CommonProblemListAdapter(
    private val commonProblemList: List<CommonProblem>
//) : BaseExpandableListAdapter() {
) : AnimatedExpandableListView.AnimatedExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return commonProblemList.size
    }

//    override fun getChildrenCount(groupPosition: Int): Int {
//        return 1
//    }

    override fun getGroup(groupPosition: Int): Any {
        return commonProblemList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return commonProblemList[groupPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return commonProblemList[groupPosition].id
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return commonProblemList[groupPosition].id
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val holder: TitleVH
        var view: View? = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(parent?.context)
            view = inflater.inflate(R.layout.item_common_title, parent, false)
            holder = TitleVH(view)
            view?.tag = holder
        } else {
            holder = view.tag as TitleVH
        }
        holder.tvTitle.text = commonProblemList[groupPosition].title
        holder.ivExpanded.bindImg(
            resId =
            if (isExpanded)
                R.drawable.ic_baseline_expand_less_24_gray
            else
                R.drawable.ic_baseline_expand_more_24_gray
        )
        return view!!
    }

//    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
//        val holder: ContentVH
//        var view: View? = convertView
//        if (view == null) {
//            val inflater = LayoutInflater.from(parent?.context)
//            view = inflater.inflate(R.layout.item_common_content, parent, false)
//            holder = ContentVH(view)
//            view?.tag = holder
//        } else {
//            holder = view.tag as ContentVH
//        }
//        holder.tvContent.text = commonProblemList[groupPosition].content
//        return view!!
//    }


    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun getRealChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val holder: ContentVH
        var view: View? = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(parent?.context)
            view = inflater.inflate(R.layout.item_common_content, parent, false)
            holder = ContentVH(view)
            view?.tag = holder
        } else {
            holder = view.tag as ContentVH
        }
        holder.tvContent.text = commonProblemList[groupPosition].content
        return view!!
    }

    override fun getRealChildrenCount(groupPosition: Int): Int {
        return 1
    }

    inner class TitleVH(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val ivExpanded: ImageView = view.findViewById(R.id.iv_expanded)
    }

    inner class ContentVH(view: View) : RecyclerView.ViewHolder(view) {
        val tvContent: TextView = view.findViewById(R.id.tv_content)
    }

}
package tw.north27.coachingapp.model.treeNodeHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.unnamed.b.atv.model.TreeNode
import tw.north27.coachingapp.R
import tw.north27.coachingapp.model.response.EducationLevel

class EducationLevelHolder(cxt: Context) : TreeNode.BaseNodeViewHolder<EducationLevel>(cxt) {

    override fun createNodeView(node: TreeNode?, value: EducationLevel?): View? {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_education_level_treenode, null, false)
        val tvText = view.findViewById<TextView>(R.id.tv_text)
        tvText.text = value?.name
        return view
    }
}